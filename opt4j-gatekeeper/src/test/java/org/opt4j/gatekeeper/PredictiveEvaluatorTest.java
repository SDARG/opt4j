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

    @Test
    void evaluate_shouldCallDelegate_whenPredictionIsFalse() {
        // Enable skipping, exploration = 20%
        PredictiveEvaluator evaluator = new PredictiveEvaluator(
                delegate, dominanceManager, archive, mainRandom, true, 0.2);

        when(delegate.evaluate(any())).thenReturn(normalObjectives);

        // Populate `cachedObjectiveKeys`
        evaluator.evaluate(dummyPhenotype);

        // Setup for second call: Model says "Do not skip"
        when(dominanceManager.shouldSkipEvaluation(any(), any())).thenReturn(false);

        // Second call
        Objectives result = evaluator.evaluate(dummyPhenotype);

        // It should return the normal objective
        assertEquals(10.0, result.getValues().iterator().next().getDouble());

        // Delegate evaluated twice, skipped zero times
        verify(delegate, times(2)).evaluate(any());
        verify(dominanceManager, times(2)).incrementEvaluated();
        verify(dominanceManager, never()).incrementSkipped();
    }

    @Test
    void evaluate_shouldReturnInfeasible_whenModelPredictsDominated_andNoExploration() {
        // Exploration = 0.0 (Meaning random.nextDouble() > 0.0 is always TRUE -> Guaranteed Skip)
        PredictiveEvaluator evaluator = new PredictiveEvaluator(
                delegate, dominanceManager, archive, mainRandom, true, 0.0);

        when(delegate.evaluate(any())).thenReturn(normalObjectives);

        // Cache initialization
        evaluator.evaluate(dummyPhenotype);

        // Setup for second call: Model predicts this is dominated
        when(dominanceManager.shouldSkipEvaluation(any(), any())).thenReturn(true);

        // Second call
        Objectives result = evaluator.evaluate(dummyPhenotype);

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
        PredictiveEvaluator evaluator = new PredictiveEvaluator(
                delegate, dominanceManager, archive, mainRandom, true, 1.0);

        when(delegate.evaluate(any())).thenReturn(normalObjectives);

        // Cache initialization
        evaluator.evaluate(dummyPhenotype);

        // Setup for second call: Model predicts this is dominated, but Random forces us to explore
        when(dominanceManager.shouldSkipEvaluation(any(), any())).thenReturn(true);

        // Second call
        Objectives result = evaluator.evaluate(dummyPhenotype);

        // Returned the real evaluation results
        assertEquals(10.0, result.getValues().iterator().next().getDouble());

        // Delegate evaluated twice, skipped zero times
        verify(delegate, times(2)).evaluate(any());
        verify(dominanceManager, times(2)).incrementEvaluated();
        verify(dominanceManager, never()).incrementSkipped();
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
