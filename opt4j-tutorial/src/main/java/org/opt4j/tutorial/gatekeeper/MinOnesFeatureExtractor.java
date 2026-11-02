package org.opt4j.tutorial.gatekeeper;

import org.opt4j.core.Individual;
import org.opt4j.gatekeeper.FeatureExtractor;
import org.opt4j.tutorial.minones.MinOnesResult;

public class MinOnesFeatureExtractor implements FeatureExtractor {

    @Override
    public float[] extractFeatures(Individual individual) {
        MinOnesResult phenotype = (MinOnesResult) individual.getPhenotype();

        // MinOnes uses 1000 variables
        float[] features = new float[1000];

        for (int i = 0; i < 1000; i++) {
            Boolean val = phenotype.get(i);
            features[i] = (val != null && val) ? 1.0f : 0.0f;
        }

        return features;
    }

    @Override
    public int getFeatureVectorSize() {
        return 1000;
    }
}
