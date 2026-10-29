package org.opt4j.gatekeeper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.problem.MultiEvaluator;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PredictiveEvaluatorTest {

    @Mock
    private MultiEvaluator delegate;
    @Mock
    private DominancePredictionManager dominanceManager;
    @Mock
    private Archive archive;
    @Mock
    private Random mainRandom;

    private Objectives normalObjectives;
    private final Object dummyPhenotype = new Object();

    @BeforeEach
    void setUp() {
        // Mock the normal objective return from Opt4J's real evaluators
        normalObjectives = new Objectives();
        normalObjectives.add(new Objective("cost", Objective.Sign.MIN), 10.0);

        // Prevent NullPointerException when PredictiveEvaluator instantiates its internal random
        lenient().when(mainRandom.nextLong()).thenReturn(12345L);
    }

    /**
     * Evaluates twice with skipping enabled: the first call populates the cached objective keys, for the second call
     * the model returns the given prediction.
     */
    private Objectives evaluateTwice(double explorationProbability, boolean predictedDominated) {
        PredictiveEvaluator evaluator = new PredictiveEvaluator(
                delegate, dominanceManager, archive, mainRandom, true, explorationProbability);

        when(delegate.evaluate(any())).thenReturn(normalObjectives);

        // Populate `cachedObjectiveKeys`
        evaluator.evaluate(dummyPhenotype);

        // Setup for second call
        when(dominanceManager.shouldSkipEvaluation(any(), any())).thenReturn(predictedDominated);

        return evaluator.evaluate(dummyPhenotype);
    }

    private void verifyEvaluatedTwiceAndNeverSkipped() {
        verify(delegate, times(2)).evaluate(any());
        verify(dominanceManager, times(2)).incrementEvaluated();
        verify(dominanceManager, never()).incrementSkipped();
    }

    @Test
    void evaluate_shouldCallDelegate_whenPredictionIsFalse() {
        // Exploration = 20%, model says "Do not skip"
        Objectives result = evaluateTwice(0.2, false);

        // It should return the normal objective
        assertEquals(10.0, result.getValues().iterator().next().getDouble());
        verifyEvaluatedTwiceAndNeverSkipped();
    }

    @Test
    void evaluate_shouldReturnInfeasible_whenModelPredictsDominated_andNoExploration() {
        // Exploration = 0.0 (Meaning random.nextDouble() > 0.0 is always TRUE -> Guaranteed Skip)
        Objectives result = evaluateTwice(0.0, true);

        // Objectives returned must be INFEASIBLE so downstream systems don't break
        assertNull(result.getValues().iterator().next().getValue());

        //  Delegate was only called once (during cache initialization)
        verify(delegate, times(1)).evaluate(any());
        verify(dominanceManager, times(1)).incrementEvaluated(); // From call 1
        verify(dominanceManager, times(1)).incrementSkipped();   // From call 2
    }

    @Test
    void evaluate_shouldForceExploration_whenModelPredictsDominated_butRandomTriggersExploration() {
        // Exploration = 1.0 (Meaning random.nextDouble() > 1.0 is always FALSE -> Guaranteed Explore)
        Objectives result = evaluateTwice(1.0, true);

        // Returned the real evaluation results
        assertEquals(10.0, result.getValues().iterator().next().getDouble());
        verifyEvaluatedTwiceAndNeverSkipped();
    }

    @Test
    void evaluate_shouldCallDelegate_whenSkippingIsGloballyDisabled() {
        // Enable skipping = FALSE
        PredictiveEvaluator evaluator = new PredictiveEvaluator(
                delegate, dominanceManager, archive, mainRandom, false, 0.2);

        when(delegate.evaluate(any())).thenReturn(normalObjectives);

        // Call twice
        evaluator.evaluate(dummyPhenotype);
        evaluator.evaluate(dummyPhenotype);

        // Everything evaluates normally. Dominance Prediction is never consulted.
        verify(delegate, times(2)).evaluate(any());
        verify(dominanceManager, times(2)).incrementEvaluated();
        verify(dominanceManager, never()).shouldSkipEvaluation(any(), any());
        verify(dominanceManager, never()).incrementSkipped();
    }
}
