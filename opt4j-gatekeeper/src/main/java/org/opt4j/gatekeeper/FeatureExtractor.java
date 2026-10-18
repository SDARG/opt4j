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

import org.opt4j.core.Individual;

/**
 * Interface to translate an Opt4J Individual into a numerical feature vector
 * suitable for neural network input.
 */
public interface FeatureExtractor {

    /**
     * Extracts a numerical feature array from the given individual's phenotype/genotype.
     *
     * @param individual The Opt4J individual.
     * @return A float array representing the features.
     */
    float[] extractFeatures(Individual individual);

    /**
     * @return The exact size of the float array returned by {@link #extractFeatures(Individual)}.
     */
    int getFeatureVectorSize();
}
