package org.opt4j.gatekeeper.extractors;

import org.junit.jupiter.api.Test;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.core.genotype.IntegerGenotype;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompositeFeatureExtractorTest {

    // Helper class that satisfies Java's type requirements
    static class MockListGenotype extends ArrayList<Object> implements Genotype {
        @Override
        @SuppressWarnings("unchecked")
        public <G extends Genotype> G newInstance() {
            return (G) new MockListGenotype(); // Fulfill Opt4J's method requirement
        }
    }

    @Test
    void testCompositeFlattening_WithIntegerAndList() {
        CompositeFeatureExtractor extractor = new CompositeFeatureExtractor();

        IntegerGenotype ig = new IntegerGenotype(0, 10);
        ig.add(5); // Normalized to 0.5f

        // Using our safe mock list that implements Genotype
        MockListGenotype listGenotype = new MockListGenotype();
        listGenotype.add(1.0f); // Float Number -> 1.0f
        listGenotype.add(true); // Boolean -> 1.0f

        CompositeGenotype<String, Genotype> composite = new CompositeGenotype<>();
        composite.put("A_Int", ig);
        composite.put("B_List", listGenotype);

        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(composite);

        float[] features = extractor.extractFeatures(mockIndividual);

        // A_Int (0.5), B_List (1.0, 1.0)
        assertArrayEquals(new float[]{0.5f, 1.0f, 1.0f}, features, 1e-6f);
    }

    @Test
    void testThrowsExceptionForWrongGenotype() {
        CompositeFeatureExtractor extractor = new CompositeFeatureExtractor();
        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(new org.opt4j.core.genotype.BooleanGenotype());

        assertThrows(IllegalArgumentException.class, () -> extractor.extractFeatures(mockIndividual));
    }
}
