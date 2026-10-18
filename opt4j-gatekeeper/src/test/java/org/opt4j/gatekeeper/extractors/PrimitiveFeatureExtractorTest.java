package org.opt4j.gatekeeper.extractors;

import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.genotype.IntegerGenotype;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrimitiveFeatureExtractorTest {

    @Test
    void testBooleanFeatureExtractor() {
        BooleanFeatureExtractor extractor = new BooleanFeatureExtractor();

        BooleanGenotype bg = new BooleanGenotype();
        bg.add(true);
        bg.add(false);
        bg.add(true);

        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(bg);

        float[] features = extractor.extractFeatures(mockIndividual);

        assertArrayEquals(new float[]{1.0f, 0.0f, 1.0f}, features, 1e-6f);
        assertEquals(3, extractor.getFeatureVectorSize());
    }

    @Test
    void testDoubleFeatureExtractor_NormalBounds() {
        DoubleFeatureExtractor extractor = new DoubleFeatureExtractor();

        // Create genotype with bounds [0.0, 10.0]
        DoubleGenotype dg = new DoubleGenotype(0.0, 10.0);
        dg.add(5.0);  // Expected normalized: 0.5
        dg.add(10.0); // Expected normalized: 1.0
        dg.add(0.0);  // Expected normalized: 0.0

        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(dg);

        float[] features = extractor.extractFeatures(mockIndividual);

        assertArrayEquals(new float[]{0.5f, 1.0f, 0.0f}, features, 1e-6f);
    }

    @Test
    void testDoubleFeatureExtractor_MinEqualsMax_PreventsNaN() {
        DoubleFeatureExtractor extractor = new DoubleFeatureExtractor();

        // Edge case: lower bound == upper bound
        DoubleGenotype dg = new DoubleGenotype(5.0, 5.0);
        dg.add(5.0);

        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(dg);

        float[] features = extractor.extractFeatures(mockIndividual);

        // Code should fallback to 0.0f instead of dividing by zero (NaN)
        assertArrayEquals(new float[]{0.0f}, features, 1e-6f);
    }

    @Test
    void testIntegerFeatureExtractor_MinEqualsMax_PreventsNaN() {
        IntegerFeatureExtractor extractor = new IntegerFeatureExtractor();

        // Edge case: lower bound == upper bound
        IntegerGenotype ig = new IntegerGenotype(5, 5);
        ig.add(5);

        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(ig);

        float[] features = extractor.extractFeatures(mockIndividual);

        // Code should fallback to 0.0f instead of dividing by zero
        assertArrayEquals(new float[]{0.0f}, features, 1e-6f);
    }

    @Test
    void testBooleanExtractor_ThrowsExceptionForWrongGenotype() {
        BooleanFeatureExtractor extractor = new BooleanFeatureExtractor();
        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(new DoubleGenotype()); // Wrong type

        assertThrows(IllegalArgumentException.class, () -> extractor.extractFeatures(mockIndividual));
    }

    @Test
    void testDoubleExtractor_ThrowsExceptionForWrongGenotype() {
        DoubleFeatureExtractor extractor = new DoubleFeatureExtractor();
        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(new BooleanGenotype()); // Wrong type

        assertThrows(IllegalArgumentException.class, () -> extractor.extractFeatures(mockIndividual));
    }

    @Test
    void testIntegerExtractor_ThrowsExceptionForWrongGenotype() {
        IntegerFeatureExtractor extractor = new IntegerFeatureExtractor();
        Individual mockIndividual = mock(Individual.class);
        when(mockIndividual.getGenotype()).thenReturn(new BooleanGenotype()); // Wrong type

        assertThrows(IllegalArgumentException.class, () -> extractor.extractFeatures(mockIndividual));
    }
}
