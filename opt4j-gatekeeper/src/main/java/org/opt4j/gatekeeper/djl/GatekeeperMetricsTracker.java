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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.opt4j.core.start.Constant;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Singleton
public class GatekeeperMetricsTracker implements AutoCloseable {

    private static final Logger log = Logger.getLogger(GatekeeperMetricsTracker.class.getName());

    private final List<Float> accuracyHistory = new ArrayList<>();
    private final List<Float> fpHistory = new ArrayList<>();
    private final List<Float> fnHistory = new ArrayList<>();
    private final List<Integer> evaluationHistory = new ArrayList<>();
    private final List<Integer> iterationHistory = new ArrayList<>();

    private final int averageWindow = 50;

    private String accuracyFilename = "accuracy.tsv";

    /**
     * Sets the file the metrics are written to on {@link #close()}.
     *
     * @param accuracyFilename
     *            the output file
     */
    @Inject(optional = true)
    void setAccuracyFilename(
            @Constant(value = "accuracyFilename", namespace = org.opt4j.gatekeeper.NeuralGatekeeperModule.class) String accuracyFilename) {
        this.accuracyFilename = accuracyFilename;
    }

    public synchronized void recordBatchMetrics(long tp, long tn, long fp, long fn, int currentIteration, int evaluatedCount) {
        long totalSamples = tp + tn + fp + fn;
        if (totalSamples == 0) return;

        float accuracy = (float) (tp + tn) / totalSamples;
        long negatives = fp + tn;
        float fpr = (negatives > 0) ? (float) fp / negatives : 0.0f;
        long positives = fn + tp;
        float fnr = (positives > 0) ? (float) fn / positives : 0.0f;

        accuracyHistory.add(accuracy);
        fpHistory.add(fpr);
        fnHistory.add(fnr);
        evaluationHistory.add(evaluatedCount);
        iterationHistory.add(currentIteration);
    }

    @Override
    public synchronized void close() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(accuracyFilename))) {
            writer.write("Iteration\tEvaluations\tPredictor\tAccuracy\tFPR\tFNR\n");

            int size = accuracyHistory.size();
            for (int i = 0; i < size; i++) {
                writer.write(iterationHistory.get(i) + "\t" +
                        evaluationHistory.get(i) + "\tNeuralNet\t" +
                        accuracyHistory.get(i) + "\t" +
                        fpHistory.get(i) + "\t" +
                        fnHistory.get(i) + "\n");
            }
            log.info("Gatekeeper metrics saved to " + accuracyFilename);
        } catch (IOException e) {
            log.warning("Error writing accuracy log: " + e.getMessage());
        }
    }
}
