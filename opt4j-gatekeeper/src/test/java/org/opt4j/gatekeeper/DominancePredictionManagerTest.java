package org.opt4j.gatekeeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;

@ExtendWith(MockitoExtension.class)
class DominancePredictionManagerTest {

    @Mock private GatekeeperModel model;
    @Mock private FeatureExtractor featureExtractor;
    @Mock private Archive archive;
    @Mock private Individual individual;

    private DominancePredictionManager manager;

    @BeforeEach
    void setUp() {
        // Prediction starts at iteration 25
        manager = new DominancePredictionManager(model, featureExtractor, 25);
    }

    @Test
    void testStatisticsTracking() {
        manager.incrementEvaluated();
        manager.incrementEvaluated();
        manager.incrementSkipped();

        assertEquals(2, manager.getEvaluatedCount());
        assertEquals(1, manager.getSkippedCount());

        manager.resetStats();

        assertEquals(0, manager.getEvaluatedCount());
        assertEquals(0, manager.getSkippedCount());
        assertEquals(0, manager.getCurrentIteration());
    }

    @Test
    void testShouldSkipFalseDuringWarmupPhase() {
        manager.setCurrentIteration(10); // 10 < 25

        boolean skip = manager.shouldSkipEvaluation(individual, archive);

        assertFalse(skip, "Should not skip during warm-up phase");
        verifyNoInteractions(model, featureExtractor);
    }

    @Test
    void testShouldSkipDelegatesToModelAfterWarmup() {
        manager.setCurrentIteration(30); // 30 > 25

        float[] newFeatures = { 1.0f };
        float[] archiveFeatures = { 0.5f };

        when(featureExtractor.extractFeatures(individual)).thenReturn(newFeatures);

        Individual archiveInd = mock(Individual.class);
        when(archive.iterator()).thenAnswer(inv -> List.of(archiveInd).iterator());
        when(featureExtractor.extractFeatures(archiveInd)).thenReturn(archiveFeatures);

        when(model.predictIsDominated(any(), any())).thenReturn(true);

        boolean skip = manager.shouldSkipEvaluation(individual, archive);

        assertTrue(skip);
        verify(model).predictIsDominated(eq(newFeatures), anyList());
    }

    @Test
    void testShouldSkipFalseIfDependenciesAreNull() {
        DominancePredictionManager nullManager = new DominancePredictionManager(null, null, 0);
        nullManager.setCurrentIteration(10);

        assertFalse(nullManager.shouldSkipEvaluation(individual, archive));
    }
}
