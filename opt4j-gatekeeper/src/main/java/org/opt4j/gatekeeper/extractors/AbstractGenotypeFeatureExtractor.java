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

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.gatekeeper.FeatureExtractor;

/**
 * Base class for feature extractors that translate a single, list-like {@link Genotype} into a feature vector with
 * one feature per genotype element.
 *
 * @param <G>
 *            the supported genotype type
 */
public abstract class AbstractGenotypeFeatureExtractor<G extends Genotype> implements FeatureExtractor {

    private final Class<G> genotypeClass;
    private int featureSize = -1;

    /**
     * Constructs the extractor.
     *
     * @param genotypeClass
     *            the supported genotype type
     */
    protected AbstractGenotypeFeatureExtractor(Class<G> genotypeClass) {
        this.genotypeClass = genotypeClass;
    }

    @Override
    public synchronized float[] extractFeatures(Individual individual) {
        Genotype raw = individual.getGenotype();
        if (!genotypeClass.isInstance(raw)) {
            throw new IllegalArgumentException("Expected " + genotypeClass.getSimpleName() + " but got " + raw.getClass().getSimpleName());
        }
        G genotype = genotypeClass.cast(raw);

        if (featureSize == -1) {
            featureSize = genotype.size();
        }

        float[] features = new float[featureSize];
        for (int i = 0; i < featureSize; i++) {
            features[i] = extractFeature(genotype, i);
        }
        return features;
    }

    /**
     * Returns the feature of the genotype element at the given index.
     *
     * @param genotype
     *            the genotype
     * @param index
     *            the index of the element
     * @return the feature value
     */
    protected abstract float extractFeature(G genotype, int index);

    /**
     * Normalizes a value to [0.0, 1.0] with respect to its bounds to prevent exploding gradients in the neural
     * network. Returns 0.0 if the bounds have zero width.
     *
     * @param value
     *            the value
     * @param min
     *            the lower bound
     * @param max
     *            the upper bound
     * @return the normalized value
     */
    protected static float normalize(double value, double min, double max) {
        if (max > min) {
            return (float) ((value - min) / (max - min));
        }
        return 0.0f;
    }

    @Override
    public int getFeatureVectorSize() {
        return featureSize;
    }
}
