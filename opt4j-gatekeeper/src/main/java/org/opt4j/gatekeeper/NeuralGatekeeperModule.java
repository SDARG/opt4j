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

package org.opt4j.gatekeeper;

import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.start.Constant;
import org.opt4j.core.start.Opt4JModule;

@Parent(NeuralGatekeeper.class)
@Icon(Icons.PUZZLE_BLUE)
@Info("<html><b>Configures the Neural Gatekeeper for Predictive Skipping.</b><br><br>"
        + "<font color='red'><b>WARNING: No other logger with the same output file should be active!</b></font><br><br>"
        + "<b>Aggressive Profile (Default):</b><br>"
        + "Start Iteration = 25 | Exploration = 0.2 | Learning Rate = 0.001 | Threshold = 0.8 | Penalty = 1.0<br><br>"
        + "<b>Conservative Profile:</b><br>"
        + "Start Iteration = 50 | Exploration = 0.2 | Learning Rate = 0.0005 | Threshold = 0.95 | Penalty = 10.0</html>")
public class NeuralGatekeeperModule extends Opt4JModule {

    @Info("Enable predictive skipping of evaluations.")
    @Order(1)
    @Constant(value = "enablePredictiveSkipping", namespace = NeuralGatekeeperModule.class)
    protected boolean enablePredictiveSkipping = true;

    @Info("Iteration at which to start predicting and skipping evaluations.")
    @Order(2)
    @Constant(value = "predictionStartIteration", namespace = NeuralGatekeeperModule.class)
    protected int predictionStartIteration = 25;

    @Info("Probability to run a real evaluation even if a skip was predicted (for forced exploration).")
    @Order(3)
    @Constant(value = "explorationProbability", namespace = NeuralGatekeeperModule.class)
    protected double explorationProbability = 0.2;

    @Info("Number of hidden layers in the neural network.")
    @Order(4)
    @Constant(value = "hiddenLayerCount", namespace = NeuralGatekeeperModule.class)
    protected int hiddenLayerCount = 2;

    @Info("Number of units in the hidden layers.")
    @Order(5)
    @Constant(value = "hiddenLayerSize", namespace = NeuralGatekeeperModule.class)
    protected int hiddenLayerSize = 200;

    @Info("Learning rate for the neural network optimizer.")
    @Order(6)
    @Constant(value = "learningRate", namespace = NeuralGatekeeperModule.class)
    protected double learningRate = 0.001;

    @Info("Probability threshold for predicting dominance.")
    @Order(7)
    @Constant(value = "predictionThreshold", namespace = NeuralGatekeeperModule.class)
    protected double predictionThreshold = 0.8;

    @Info("Penalty multiplier for false positives during training.")
    @Order(8)
    @Constant(value = "falsePositivePenalty", namespace = NeuralGatekeeperModule.class)
    protected double falsePositivePenalty = 1.0;

    @Info("Maximum number of training pairs per generation. Use -1 to use the entire archive.")
    @Order(9)
    @Constant(value = "maxArchiveSamplesForTraining", namespace = NeuralGatekeeperModule.class)
    protected int maxArchiveSamplesForTraining = -1;

    @Info("Batch size used for training the neural network.")
    @Order(10)
    @Constant(value = "batchSize", namespace = NeuralGatekeeperModule.class)
    protected int batchSize = 1024;

    @Info("The filename for the main run output TSV (correct evaluation count).")
    @Order(11)
    @Constant(value = "tsvFilename", namespace = NeuralGatekeeperModule.class)
    protected String tsvFilename = "run.tsv";

    @Info("The filename for the accuracy output TSV.")
    @Order(12)
    @Constant(value = "accuracyFilename", namespace = NeuralGatekeeperModule.class)
    protected String accuracyFilename = "accuracy.tsv";

    @Info("Enable GPU acceleration for the neural network (requires CUDA).")
    @Order(13)
    @Constant(value = "useGpu", namespace = NeuralGatekeeperModule.class)
    protected boolean useGpu = false;


    public boolean isEnablePredictiveSkipping() {
        return enablePredictiveSkipping;
    }

    public void setEnablePredictiveSkipping(boolean enablePredictiveSkipping) {
        this.enablePredictiveSkipping = enablePredictiveSkipping;
    }

    public int getPredictionStartIteration() {
        return predictionStartIteration;
    }

    public void setPredictionStartIteration(int predictionStartIteration) {
        this.predictionStartIteration = predictionStartIteration;
    }

    public double getExplorationProbability() {
        return explorationProbability;
    }

    public void setExplorationProbability(double explorationProbability) {
        this.explorationProbability = explorationProbability;
    }

    public int getHiddenLayerCount() {
        return hiddenLayerCount;
    }

    public void setHiddenLayerCount(int hiddenLayerCount) {
        this.hiddenLayerCount = hiddenLayerCount;
    }

    public int getHiddenLayerSize() {
        return hiddenLayerSize;
    }

    public void setHiddenLayerSize(int hiddenLayerSize) {
        this.hiddenLayerSize = hiddenLayerSize;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public double getPredictionThreshold() {
        return predictionThreshold;
    }

    public void setPredictionThreshold(double predictionThreshold) {
        this.predictionThreshold = predictionThreshold;
    }

    public double getFalsePositivePenalty() {
        return falsePositivePenalty;
    }

    public void setFalsePositivePenalty(double falsePositivePenalty) {
        this.falsePositivePenalty = falsePositivePenalty;
    }

    public int getMaxArchiveSamplesForTraining() {
        return maxArchiveSamplesForTraining;
    }

    public void setMaxArchiveSamplesForTraining(int maxArchiveSamplesForTraining) {
        this.maxArchiveSamplesForTraining = maxArchiveSamplesForTraining;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getTsvFilename() {
        return tsvFilename;
    }

    public void setTsvFilename(String tsvFilename) {
        this.tsvFilename = tsvFilename;
    }

    public String getAccuracyFilename() {
        return accuracyFilename;
    }

    public void setAccuracyFilename(String accuracyFilename) {
        this.accuracyFilename = accuracyFilename;
    }

    public boolean isUseGpu() {
        return useGpu;
    }

    public void setUseGpu(boolean useGpu) {
        this.useGpu = useGpu;
    }


    @Override
    protected void config() {
        // Bind Core Manager
        bind(DominancePredictionManager.class).in(Singleton.class);

        // Intercept Evaluator
        bind(new TypeLiteral<Evaluator<Object>>() {}).to(PredictiveEvaluator.class).in(Singleton.class);

        // Add the Training Listener
        bind(GatekeeperTrainingListener.class).in(Singleton.class);
        addOptimizerIterationListener(GatekeeperTrainingListener.class);
        addOptimizerStateListener(GatekeeperTrainingListener.class);

        bind(EvaluationLogger.class).in(Singleton.class);
        addOptimizerIterationListener(EvaluationLogger.class);
        addOptimizerStateListener(EvaluationLogger.class);

        // Bind DJL Implementation to the Interface
        bind(GatekeeperModel.class).to(org.opt4j.gatekeeper.djl.DjlDominancePredictor.class).in(Singleton.class);
    }
}
