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
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.gatekeeper.FeatureExtractor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Singleton
public class CompositeFeatureExtractor implements FeatureExtractor {
    private int featureSize = -1;

    @Override
    public synchronized float[] extractFeatures(Individual individual) {
        if (!(individual.getGenotype() instanceof CompositeGenotype)) {
            throw new IllegalArgumentException("Expected CompositeGenotype but got " + individual.getGenotype().getClass().getSimpleName());
        }

        List<Float> featureList = new ArrayList<>();
        flattenComposite((CompositeGenotype<?, ?>) individual.getGenotype(), featureList);

        if (featureSize == -1) {
            featureSize = featureList.size();
        }

        float[] features = new float[featureSize];
        for (int i = 0; i < featureSize; i++) {
            features[i] = featureList.get(i);
        }
        return features;
    }

    private void flattenComposite(CompositeGenotype<?, ?> composite, List<Float> featureList) {
        // Sort keys to guarantee deterministic ordering
        List<Object> keys = new ArrayList<>(composite.keySet());
        keys.sort(Comparator.comparing(Object::toString));

        for (Object key : keys) {
            Genotype subGenotype = composite.get(key);

            if (subGenotype instanceof CompositeGenotype) {
                flattenComposite((CompositeGenotype<?, ?>) subGenotype, featureList);
            } else if (subGenotype instanceof DoubleGenotype dg) {
                for (int i = 0; i < dg.size(); i++) {                    double val = dg.get(i);
                    double min = dg.getLowerBound(i);
                    double max = dg.getUpperBound(i);
                    featureList.add(max > min ? (float) ((val - min) / (max - min)) : 0.0f);
                }
            } else if (subGenotype instanceof IntegerGenotype ig) {
                for (int i = 0; i < ig.size(); i++) {
                    double val = ig.get(i);
                    double min = ig.getLowerBound(i);
                    double max = ig.getUpperBound(i);
                    featureList.add(max > min ? (float) ((val - min) / (max - min)) : 0.0f);
                }
            } else if (subGenotype instanceof BooleanGenotype bg) {
                for (int i = 0; i < bg.size(); i++) {
                    featureList.add(bg.get(i) ? 1.0f : 0.0f);
                }
            } else if (subGenotype instanceof List) {
                // Fallback for custom bounds-less implementations
                for (Object element : (List<?>) subGenotype) {
                    if (element instanceof Number) {
                        featureList.add(((Number) element).floatValue());
                    } else if (element instanceof Boolean) {
                        featureList.add(((Boolean) element) ? 1.0f : 0.0f);
                    }
                }
            }
        }
    }

    @Override
    public int getFeatureVectorSize() { return featureSize; }
}
