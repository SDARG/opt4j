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
import com.google.inject.Singleton;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Singleton
public class DominancePredictionManager {

    private static final Logger log = Logger.getLogger(DominancePredictionManager.class.getName());

    private final GatekeeperModel model;
    private final FeatureExtractor featureExtractor;

    private volatile int currentIteration = 0;
    private final AtomicInteger skippedCount = new AtomicInteger(0);
    private final AtomicInteger evaluatedCount = new AtomicInteger(0);

    private final int predictionStartIteration;

    @Inject
    public DominancePredictionManager(
            GatekeeperModel model,
            FeatureExtractor featureExtractor,
            @Constant(value = "predictionStartIteration", namespace = NeuralGatekeeperModule.class) int predictionStartIteration,
            Random mainRandom) {

        this.model = model;
        this.featureExtractor = featureExtractor;
        this.predictionStartIteration = predictionStartIteration;
    }

    public void setCurrentIteration(int iteration) {
        this.currentIteration = iteration;
    }

    public int getCurrentIteration() { return currentIteration; }
    public void incrementSkipped() { skippedCount.incrementAndGet(); }
    public void incrementEvaluated() { evaluatedCount.incrementAndGet(); }
    public int getSkippedCount() { return skippedCount.get(); }
    public int getEvaluatedCount() { return evaluatedCount.get(); }

    public void resetStats() {
        skippedCount.set(0);
        evaluatedCount.set(0);
        currentIteration = 0;
    }

    /**
     * Determines whether an evaluation should be skipped.
     */
    public boolean shouldSkipEvaluation(Individual individual, Archive archive) {
        if (currentIteration < predictionStartIteration) {
            return false; // Warm-up phase
        }

        if (model == null || featureExtractor == null) {
            return false; // Missing dependencies, evaluate normally
        }

        float[] newFeatures = featureExtractor.extractFeatures(individual);
        List<float[]> archiveFeatures = new ArrayList<>();

        for (Individual archiveInd : archive) {
            archiveFeatures.add(featureExtractor.extractFeatures(archiveInd));
        }

        return model.predictIsDominated(newFeatures, archiveFeatures);
    }
}
