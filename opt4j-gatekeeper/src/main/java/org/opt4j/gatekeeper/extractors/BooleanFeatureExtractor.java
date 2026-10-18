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

package org.opt4j.gatekeeper.extractors;

import com.google.inject.Singleton;
import org.opt4j.core.Individual;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.gatekeeper.FeatureExtractor;

@Singleton
public class BooleanFeatureExtractor implements FeatureExtractor {
    private int featureSize = -1;

    @Override
    public synchronized float[] extractFeatures(Individual individual) {
        if (!(individual.getGenotype() instanceof BooleanGenotype genotype)) {
            throw new IllegalArgumentException("Expected BooleanGenotype but got " + individual.getGenotype().getClass().getSimpleName());
        }

        if (featureSize == -1) {
            featureSize = genotype.size();
        }

        float[] features = new float[featureSize];
        for (int i = 0; i < featureSize; i++) {
            features[i] = genotype.get(i) ? 1.0f : 0.0f;
        }
        return features;
    }

    @Override
    public int getFeatureVectorSize() { return featureSize; }
}
