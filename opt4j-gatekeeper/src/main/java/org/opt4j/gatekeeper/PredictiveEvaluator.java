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

package org.opt4j.gatekeeper;

import com.google.inject.Inject;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.MultiEvaluator;
import org.opt4j.core.start.Constant;

import java.util.Collection;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Evaluator wrapper that skips expensive evaluations based on dominance prediction.
 * When a skip is accepted, it returns INFEASIBLE objectives so downstream components don't crash.
 */
public class PredictiveEvaluator implements Evaluator<Object> {

    private static final Logger log = Logger.getLogger(PredictiveEvaluator.class.getName());

    private final MultiEvaluator delegate;
    private final DominancePredictionManager dominanceManager;
    private final Archive archive;
    private final boolean enableSkipping;

    private final Random explorationRandom;
    private final double explorationProbability;

    private Collection<Objective> cachedObjectiveKeys = null;

    @Inject
    public PredictiveEvaluator(
            MultiEvaluator delegate,
            DominancePredictionManager dominanceManager,
            Archive archive,
            Random mainRandom,
            @Constant(value = "enablePredictiveSkipping", namespace = NeuralGatekeeperModule.class) boolean enableSkipping,
            @Constant(value = "explorationProbability", namespace = NeuralGatekeeperModule.class) double explorationProbability) {

        this.delegate = delegate;
        this.dominanceManager = dominanceManager;
        this.archive = archive;
        this.enableSkipping = enableSkipping;
        this.explorationProbability = explorationProbability;
        this.explorationRandom = new Random(mainRandom.nextLong());

        if (enableSkipping) {
            log.info("PredictiveEvaluator: Neural Skipping enabled.");
        }
    }

    @Override
    public Objectives evaluate(Object phenotype) {
        boolean canSkip = cachedObjectiveKeys != null && enableSkipping;

        if (canSkip) {
            boolean skipRequested = dominanceManager.shouldSkipEvaluation(wrapPhenotype(phenotype), archive);

            // exploration stream: occasionally run the evaluation anyway to avoid getting stuck
            boolean shouldActuallySkip = skipRequested && explorationRandom.nextDouble() > explorationProbability;

            if (shouldActuallySkip) {
                dominanceManager.incrementSkipped();
                return createInfeasibleObjectives();
            }
        }

        Objectives result = delegate.evaluate(phenotype);
        dominanceManager.incrementEvaluated();

        if (cachedObjectiveKeys == null) {
            cachedObjectiveKeys = result.getKeys();
        }
        return result;
    }

    private Individual wrapPhenotype(Object phenotype) {
        Individual wrapper = new Individual() {};
        wrapper.setPhenotype(phenotype);
        return wrapper;
    }

    private Objectives createInfeasibleObjectives() {
        Objectives infeasible = new Objectives();
        for (Objective obj : cachedObjectiveKeys) {
            infeasible.add(obj, Objective.INFEASIBLE);
        }
        return infeasible;
    }
}
