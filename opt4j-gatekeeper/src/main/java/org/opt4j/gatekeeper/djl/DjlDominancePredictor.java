/*******************************************************************************
 * Copyright (c) 2026 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.gatekeeper.djl;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Activation;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.GradientCollector;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.training.dataset.Dataset;
import ai.djl.training.listener.TrainingListener;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;
import ai.djl.training.tracker.Tracker;
import ai.djl.translate.NoopTranslator;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.opt4j.core.start.Constant;
import org.opt4j.gatekeeper.DominancePredictionManager;
import org.opt4j.gatekeeper.GatekeeperModel;

import java.util.List;

@Singleton
public class DjlDominancePredictor implements GatekeeperModel {

    private final GatekeeperMetricsTracker metricsTracker;
    private final Provider<DominancePredictionManager> dominanceManagerProvider;    // Used just to get current counters/metrics
                                                                                    // Wrap DominancePredictionManager with a Provider to fix circular dependency error
    private final int hiddenLayerCount;
    private final int hiddenLayerSize;
    private final float learningRate;
    private final float predictionThreshold;
    private final float falsePositivePenalty;
    private final int batchSize;
    private final boolean useGpu;

    private boolean initialized = false;
    private int features;

    private NDManager baseManager;
    private Model model;
    private Trainer trainer;

    // ThreadLocal ensures lock-free concurrent inference for Opt4J Worker Threads
    private ThreadLocal<Predictor<NDList, NDList>> predictorPool;

    @Inject
    public DjlDominancePredictor(
            GatekeeperMetricsTracker metricsTracker,
            Provider<DominancePredictionManager> dominanceManagerProvider,
            DjlInitializerService initService, // Forces eager initialization of native libs
            @Constant(value = "hiddenLayerCount", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) int hiddenLayerCount,
            @Constant(value = "hiddenLayerSize", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) int hiddenLayerSize,
            @Constant(value = "learningRate", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) double learningRate,
            @Constant(value = "predictionThreshold", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) double predictionThreshold,
            @Constant(value = "falsePositivePenalty", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) double falsePositivePenalty,
            @Constant(value = "batchSize", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) int batchSize,
            @Constant(value = "useGpu", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) boolean useGpu){

        this.metricsTracker = metricsTracker;
        this.dominanceManagerProvider = dominanceManagerProvider;
        this.hiddenLayerCount = hiddenLayerCount;
        this.hiddenLayerSize = hiddenLayerSize;
        this.learningRate = (float) learningRate;
        this.predictionThreshold = (float) predictionThreshold;
        this.falsePositivePenalty = (float) falsePositivePenalty;
        this.batchSize = batchSize;
        this.useGpu = useGpu;
    }

    private synchronized void ensureInitialized(int featureSize) {
        if (!initialized) {
            this.features = featureSize;

            // Determine Device based on config
            ai.djl.Device device = this.useGpu ? ai.djl.Device.gpu() : ai.djl.Device.cpu();
            this.baseManager = NDManager.newBaseManager(device);
            this.model = Model.newInstance("gatekeeper-binary-model", device);

            SequentialBlock block = new SequentialBlock();
            for (int i = 0; i < hiddenLayerCount; i++) {
                block.add(Linear.builder().setUnits(hiddenLayerSize).build());
                block.add(Activation::relu);
            }
            block.add(Linear.builder().setUnits(1).build()); // Single logit output
            model.setBlock(block);

            DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.sigmoidBinaryCrossEntropyLoss())
                    .optOptimizer(Optimizer.adam().optLearningRateTracker(Tracker.fixed(learningRate)).build())
                    .optDevices(new ai.djl.Device[]{ device })
                    .addTrainingListeners(TrainingListener.Defaults.logging());

            this.trainer = model.newTrainer(config);
            this.trainer.initialize(new Shape(this.batchSize, features));
            this.predictorPool = ThreadLocal.withInitial(() -> model.newPredictor(new NoopTranslator(), device));
            this.initialized = true;
        }
    }

    @Override
    public boolean predictIsDominated(float[] newFeatures, List<float[]> archiveFeaturesList) {
        if (archiveFeaturesList.isEmpty()) return false;

        int inputFeatureSize = newFeatures.length + archiveFeaturesList.getFirst().length;
        ensureInitialized(inputFeatureSize);

        try (NDManager subManager = this.baseManager.newSubManager()) {
            // Build the batch of [Archive, New] pairs
            float[][] featuresArray = new float[archiveFeaturesList.size()][inputFeatureSize];
            for (int i = 0; i < archiveFeaturesList.size(); i++) {
                float[] archFeat = archiveFeaturesList.get(i);
                System.arraycopy(archFeat, 0, featuresArray[i], 0, archFeat.length);
                System.arraycopy(newFeatures, 0, featuresArray[i], archFeat.length, newFeatures.length);
            }

            NDArray inputFeatures = subManager.create(featuresArray);

            // Thread-safe, lock-free prediction
            Predictor<NDList, NDList> predictor = predictorPool.get();
            NDArray logits = predictor.predict(new NDList(inputFeatures)).singletonOrThrow();

            // Convert logits to probabilities (Sigmoid)
            NDArray probabilities = logits.getManager().ones(logits.getShape(), logits.getDataType())
                    .div(logits.neg().exp().add(1f));

            float[] probs = probabilities.toFloatArray();

            for (float p : probs) {
                if (p > predictionThreshold) return true; // Exit if any archive individual dominates
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error during prediction", e);
        }
    }

    @Override
    public synchronized void train(List<float[]> combinedFeatures, List<Float> labels) {
        if (combinedFeatures.isEmpty()) return;
        ensureInitialized(combinedFeatures.getFirst().length);

        try (NDManager subManager = this.baseManager.newSubManager()) {
            float[][] featuresArray = combinedFeatures.toArray(new float[0][]);
            float[] labelsArray = new float[labels.size()];
            long totalPositives = 0;

            for (int i = 0; i < labels.size(); i++) {
                labelsArray[i] = labels.get(i);
                if (labelsArray[i] > 0.5f) totalPositives++;
            }

            long datasetSize = labels.size();
            long totalNegatives = datasetSize - totalPositives;

            // Global class weights to heavily penalize false positives
            float globalPosWeight = (totalPositives > 0) ? (float) datasetSize / (2.0f * totalPositives) : 0.0f;
            float globalNegWeight = (totalNegatives > 0) ? (float) datasetSize / (2.0f * totalNegatives) : 0.0f;
            globalNegWeight *= falsePositivePenalty;

            NDArray featuresND = subManager.create(featuresArray);
            NDArray labelsND = subManager.create(labelsArray).reshape(-1, 1);

            Dataset trainingData = new ArrayDataset.Builder()
                    .setData(featuresND)
                    .optLabels(labelsND)
                    .setSampling(this.batchSize, false)
                    .build();

            long totalTP = 0, totalTN = 0, totalFP = 0, totalFN = 0;

            for (Batch batch : trainingData.getData(subManager)) {
                try {
                    NDArray featureData = batch.getData().head();
                    NDArray labelData = batch.getLabels().head();
                    try (GradientCollector collector = trainer.newGradientCollector()) {
                        // Calculate Metrics before stepping
                        NDArray logits = trainer.forward(new NDList(featureData)).singletonOrThrow();
                        NDArray predictedLabels = logits.gt(0.0f); // Sigmoid > 0.5

                        totalTP += predictedLabels.eq(1.0f).logicalAnd(labelData.eq(1.0f)).sum().getLong();
                        totalTN += predictedLabels.eq(0.0f).logicalAnd(labelData.eq(0.0f)).sum().getLong();
                        totalFP += predictedLabels.eq(1.0f).logicalAnd(labelData.eq(0.0f)).sum().getLong();
                        totalFN += predictedLabels.eq(0.0f).logicalAnd(labelData.eq(1.0f)).sum().getLong();

                        // Manual Backpropagation with custom weighted loss
                        NDArray probabilities = logits.getManager().ones(logits.getShape(), logits.getDataType()).div(logits.neg().exp().add(1f));
                        float eps = 1e-7f;
                        NDArray clippedProbs = probabilities.clip(eps, 1 - eps);

                        NDArray posTerm = labelData.mul(clippedProbs.log()).mul(-globalPosWeight);
                        NDArray oneMinusLabel = labelData.neg().add(1);
                        NDArray oneMinusProb = clippedProbs.neg().add(1);
                        NDArray negTerm = oneMinusLabel.mul(oneMinusProb.log()).mul(-globalNegWeight);

                        NDArray loss = posTerm.add(negTerm).mean();
                        collector.backward(loss);
                    }
                    trainer.step();
                } finally {
                    batch.close();
                }
            }
            metricsTracker.recordBatchMetrics(totalTP, totalTN, totalFP, totalFN, dominanceManagerProvider.get().getCurrentIteration(), dominanceManagerProvider.get().getEvaluatedCount());
        } catch (Exception e) {
            throw new RuntimeException("Training failed", e);
        }
    }

    @Override
    public synchronized void close() {
        if (metricsTracker != null) metricsTracker.close();

        // Cannot close ThreadLocal variables from the main thread if they were spawned by workers,
        // but Model/Trainer/Manager cleanup will natively release the underlying PyTorch memory.
        if (trainer != null) { trainer.close(); trainer = null; }
        if (model != null) { model.close(); model = null; }
        if (baseManager != null) { baseManager.close(); baseManager = null; }
    }
}
