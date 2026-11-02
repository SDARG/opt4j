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

import java.util.List;

/**
 * Interface representing the machine learning surrogate model.
 * Implementations (e.g., DJL) must ensure that {@link #predictIsDominated} is thread-safe
 * so multiple Opt4J evaluation threads can query the model concurrently.
 */
public interface GatekeeperModel extends AutoCloseable {

    /**
     * Predicts whether the new individual is dominated by ANY individual in the archive.
     *
     * @param newFeatures The feature vector of the new individual.
     * @param archiveFeatures A list of feature vectors representing the current archive.
     * @return true if the model predicts the new individual is dominated by the archive.
     */
    boolean predictIsDominated(float[] newFeatures, List<float[]> archiveFeatures);

    /**
     * Trains the model on a batch of comparisons.
     *
     * @param combinedFeatures A list of concatenated feature arrays: [ArchiveFeatures, NewFeatures].
     * @param labels A list of labels: 1.0f if the archive individual dominates the new one, 0.0f otherwise.
     */
    void train(List<float[]> combinedFeatures, List<Float> labels);
}
