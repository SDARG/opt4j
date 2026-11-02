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

import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Value;
import org.opt4j.core.optimizer.*;
import org.opt4j.core.start.Constant;

import java.util.*;
import java.util.logging.Logger;

/**
 * Listens to optimizer iterations and builds pairwise training data (Archive vs. New)
 * to train the GatekeeperModel incrementally.
 */
public class GatekeeperTrainingListener implements OptimizerIterationListener, OptimizerStateListener {

    private static final Logger log = Logger.getLogger(GatekeeperTrainingListener.class.getName());

    private final Population population;
    private final Archive archive;
    private final DominancePredictionManager dominanceManager;
    private final GatekeeperModel model;
    private final FeatureExtractor featureExtractor;
    private final int maxArchiveSamplesForTraining;
    private final Random samplingRandom = new Random();

    private final Set<Integer> seenIndividualHashes = new HashSet<>();

    @Inject
    public GatekeeperTrainingListener(
            Population population,
            Archive archive,
            DominancePredictionManager dominanceManager,
            GatekeeperModel model,
            FeatureExtractor featureExtractor,
            @Constant(value = "maxArchiveSamplesForTraining", namespace = NeuralGatekeeperModule.class) int maxArchiveSamplesForTraining) {

        this.population = population;
        this.archive = archive;
        this.dominanceManager = dominanceManager;
        this.model = model;
        this.featureExtractor = featureExtractor;
        this.maxArchiveSamplesForTraining = maxArchiveSamplesForTraining;
    }

    @Override
    public void iterationComplete(int iteration) {
        dominanceManager.setCurrentIteration(iteration);

        List<Individual> validNewIndividuals = new ArrayList<>();
        for (Individual individual : population) {
            if (isInfeasible(individual)) continue;

            if (seenIndividualHashes.add(individual.hashCode())) {
                validNewIndividuals.add(individual);
            }
        }

        if (validNewIndividuals.isEmpty() || archive.isEmpty()) return;

        trainModelOnIterationData(validNewIndividuals);
    }

    private void trainModelOnIterationData(List<Individual> newIndividuals) {
        List<float[]> combinedFeaturesList = new ArrayList<>();
        List<Float> labelsList = new ArrayList<>();

        List<Individual> validArchive = new ArrayList<>();
        List<float[]> archiveFeaturesList = new ArrayList<>();

        for (Individual nonDomInd : archive) {
            validArchive.add(nonDomInd);
            archiveFeaturesList.add(featureExtractor.extractFeatures(nonDomInd));
        }

        for (Individual newInd : newIndividuals) {
            float[] newFeatures = featureExtractor.extractFeatures(newInd);

            List<Integer> archiveIndices = new ArrayList<>();
            for (int i = 0; i < validArchive.size(); i++) archiveIndices.add(i);

            // Subsample if configured
            if (maxArchiveSamplesForTraining > 0 && archiveIndices.size() > maxArchiveSamplesForTraining) {
                Collections.shuffle(archiveIndices, samplingRandom);
                archiveIndices = archiveIndices.subList(0, maxArchiveSamplesForTraining);
            }

            for (int idx : archiveIndices) {
                Individual archiveInd = validArchive.get(idx);
                float[] archiveFeatures = archiveFeaturesList.get(idx);

                boolean archiveDominatesNew = archiveInd.getObjectives().dominates(newInd.getObjectives());

                // Ordering: [Archive, New]
                float[] combined = new float[archiveFeatures.length + newFeatures.length];
                System.arraycopy(archiveFeatures, 0, combined, 0, archiveFeatures.length);
                System.arraycopy(newFeatures, 0, combined, archiveFeatures.length, newFeatures.length);

                combinedFeaturesList.add(combined);
                labelsList.add(archiveDominatesNew ? 1.0f : 0.0f);
            }
        }

        if (!combinedFeaturesList.isEmpty()) {
            model.train(combinedFeaturesList, labelsList);
        }
    }

    private boolean isInfeasible(Individual individual) {
        if (individual.getObjectives() == null) return true;
        for (Value<?> v : individual.getObjectives().getValues()) {
            if (v == Objective.INFEASIBLE) return true;
        }
        return false;
    }

    @Override
    public void optimizationStarted(Optimizer optimizer) {
        seenIndividualHashes.clear();
        dominanceManager.resetStats();
    }

    @Override
    public void optimizationStopped(Optimizer optimizer) {
        log.info("Predictive Evaluation Stats:");
        int total = dominanceManager.getSkippedCount() + dominanceManager.getEvaluatedCount();
        double skipRate = (total > 0) ? (100.0 * dominanceManager.getSkippedCount() / total) : 0.0;
        log.info(String.format("Evaluated: %d | Skipped: %d | Skip Rate: %.2f%%",
                dominanceManager.getEvaluatedCount(), dominanceManager.getSkippedCount(), skipRate));

        try {
            model.close();
        } catch (Exception e) {
            log.warning("Failed to close GatekeeperModel cleanly: " + e.getMessage());
        }
        seenIndividualHashes.clear();
    }
}
