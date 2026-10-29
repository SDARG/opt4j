package org.opt4j.gatekeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.Population;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GatekeeperTrainingListenerTest {

    @Mock private Archive archive;
    @Mock private DominancePredictionManager dominanceManager;
    @Mock private GatekeeperModel model;
    @Mock private FeatureExtractor featureExtractor;

    @Captor private ArgumentCaptor<List<float[]>> combinedFeaturesCaptor;
    @Captor private ArgumentCaptor<List<Float>> labelsCaptor;

    private Population population;
    private GatekeeperTrainingListener listener;

    private Individual archiveInd;
    private Individual feasibleInd;
    private Individual infeasibleInd;

    @BeforeEach
    void setUp() {
        population = new Population();
        listener = new GatekeeperTrainingListener(
                population, archive, dominanceManager, model, featureExtractor, -1);

        Objective costObj = new Objective("cost", Objective.Sign.MIN);

        // Set up Archive Individual (High fitness / low cost)
        archiveInd = new Individual() {};
        Objectives archObjectives = new Objectives();
        archObjectives.add(costObj, 5.0);
        archiveInd.setObjectives(archObjectives);

        // Set up Feasible New Individual (Worse fitness -> dominated by archive)
        feasibleInd = new Individual() {};
        Objectives feasibleObjectives = new Objectives();
        feasibleObjectives.add(costObj, 10.0);
        feasibleInd.setObjectives(feasibleObjectives);

        // Set up Infeasible New Individual
        infeasibleInd = new Individual() {};

        // Override getValues() here to guarantee the listener loop in GatekeeperTrainingListener
        // finds the Objective.INFEASIBLE value and triggers the filter correctly,
        // bypassing Opt4J's internal logic that hides infeasible values from the map.
        Objectives infeasibleObjectives = new Objectives() {
            @Override
            public Collection<Value<?>> getValues() {
                return Collections.singletonList(Objective.INFEASIBLE);
            }
        };
        infeasibleInd.setObjectives(infeasibleObjectives);

        lenient().when(archive.isEmpty()).thenReturn(true);
        lenient().when(archive.iterator()).thenAnswer(invocation -> Collections.emptyIterator());
        lenient().when(featureExtractor.extractFeatures(infeasibleInd)).thenReturn(new float[]{0.0f});
    }

    @Test
    @SuppressWarnings("unchecked")
    void testIterationComplete_FiltersInfeasibleAndCombinesFeaturesCorrectly() {
        population.add(feasibleInd);
        population.add(infeasibleInd); // This one should be filtered out

        when(archive.isEmpty()).thenReturn(false);
        when(archive.iterator()).thenAnswer(invocation -> List.of(archiveInd).iterator());

        float[] archiveFeatures = { 1.0f, 0.5f };
        float[] feasibleFeatures = { 0.0f, 1.0f, 0.2f };

        when(featureExtractor.extractFeatures(archiveInd)).thenReturn(archiveFeatures);
        when(featureExtractor.extractFeatures(feasibleInd)).thenReturn(feasibleFeatures);

        listener.optimizationStarted(null);
        listener.iterationComplete(1);

        verify(dominanceManager).setCurrentIteration(1);
        verify(model, times(1)).train(combinedFeaturesCaptor.capture(), labelsCaptor.capture());

        List<float[]> combinedFeaturesList = combinedFeaturesCaptor.getValue();
        List<Float> labelsList = labelsCaptor.getValue();

        // Assert that only one training pair was created (Infeasible was successfully skipped)
        assertEquals(1, combinedFeaturesList.size(), "Should only have 1 training pair");
        assertEquals(1, labelsList.size());

        // Assert that feature dimensions are correctly concatenated (2 + 3 = 5)
        float[] combined = combinedFeaturesList.get(0);
        assertEquals(5, combined.length, "Combined array length should be Archive(2) + New(3)");

        // Assert that values are concatenated in the correct [Archive, New] order
        assertArrayEquals(new float[]{ 1.0f, 0.5f, 0.0f, 1.0f, 0.2f }, combined, 1e-6f);

        // Assert that label is 1.0f because Archive (cost 5.0) dominates Feasible (cost 10.0)
        assertEquals(1.0f, labelsList.get(0), 1e-6f, "Label should be 1.0f (Archive dominates)");
    }

    @Test
    void testIterationComplete_SkipsTrainingIfPopulationIsEmptyOrAllInfeasible() {
        population.add(infeasibleInd);

        listener.optimizationStarted(null);
        listener.iterationComplete(1);

        verify(model, never()).train(any(), any());
    }

    @Test
    void testIterationComplete_DeduplicatesIndividuals() {
        population.add(feasibleInd);
        population.add(feasibleInd); // Duplicate instance (same hashcode)

        when(archive.isEmpty()).thenReturn(false);
        when(archive.iterator()).thenAnswer(invocation -> List.of(archiveInd).iterator());

        when(featureExtractor.extractFeatures(archiveInd)).thenReturn(new float[]{1f});
        when(featureExtractor.extractFeatures(feasibleInd)).thenReturn(new float[]{0f});

        listener.optimizationStarted(null);
        listener.iterationComplete(1);

        verify(model, times(1)).train(combinedFeaturesCaptor.capture(), any());

        // Ensure that the duplication was caught by the hash set
        assertEquals(1, combinedFeaturesCaptor.getValue().size(), "Should deduplicate and only yield 1 pair");
    }
}
