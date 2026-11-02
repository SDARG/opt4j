package org.opt4j.gatekeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DominancePredictionManagerTest {

    @Mock private GatekeeperModel model;
    @Mock private FeatureExtractor featureExtractor;
    @Mock private Archive archive;
    @Mock private Individual individual;
    @Mock private Random random;

    private DominancePredictionManager manager;

    @BeforeEach
    void setUp() {
        // Prediction starts at iteration 25
        manager = new DominancePredictionManager(model, featureExtractor, 25, random);
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
    void testShouldSkip_FalseDuringWarmupPhase() {
        manager.setCurrentIteration(10); // 10 < 25

        boolean skip = manager.shouldSkipEvaluation(individual, archive);

        assertFalse(skip, "Should not skip during warm-up phase");
        verifyNoInteractions(model, featureExtractor);
    }

    @Test
    void testShouldSkip_DelegatesToModelAfterWarmup() {
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
    void testShouldSkip_FalseIfDependenciesAreNull() {
        DominancePredictionManager nullManager = new DominancePredictionManager(null, null, 0, random);
        nullManager.setCurrentIteration(10);

        assertFalse(nullManager.shouldSkipEvaluation(individual, archive));
    }
}
