/*******************************************************************************
 * Copyright (c) 2014 Opt4J
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
 

package org.opt4j.core.common.logger;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.Objective;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Opt4JModule;

/**
 * The {@link AbstractLogger} triggers its refinement on specific events
 * dependent on the iteration and evaluation count. Each {@link AbstractLogger}
 * has to listen to the be registered as listener of the {@link Optimizer} (
 * {@link Opt4JModule#addOptimizerStateListener(Class)} and
 * {@link Opt4JModule#addOptimizerIterationListener(Class)}) and the state of
 * the {@link Individual} ({@link Opt4JModule#addIndividualStateListener(Class)}
 * .
 * 
 * @author lukasiewycz
 * 
 */
public abstract class AbstractLogger implements OptimizerStateListener, OptimizerIterationListener,
		IndividualStateListener {

	protected final int iterationStep;
	protected final int evaluationStep;
	protected final AtomicInteger evaluationCount = new AtomicInteger(0);

	protected boolean isFirst = true;
	protected int evaluationCountLast = 0;

	/**
	 * Constructs an {@link AbstractLogger}.
	 * 
	 * @param iterationStep
	 *            the number of iterations between two logging events
	 * @param evaluationStep
	 *            the number of evaluations between two logging events
	 */
	public AbstractLogger(int iterationStep, int evaluationStep) {
		super();
		this.iterationStep = iterationStep;
		this.evaluationStep = evaluationStep;
	}

	/**
	 * Callback method called if the specific number of iterations or
	 * evaluations is reached.
	 * 
	 * @param iteration
	 *            the current iteration number
	 * @param evaluation
	 *            the current evaluation number
	 */
	public abstract void logEvent(int iteration, int evaluation);

	/**
	 * Writes the header.
	 * 
	 * @param objectives
	 *            a collection of objectives
	 */
	public abstract void logHeader(Collection<Objective> objectives);

	/**
	 * Callback method invoked once the optimization starts.
	 * 
	 */
	public abstract void optimizationStarted();

	/**
	 * Callback method invoked once the optimization stops.
	 * 
	 */
	public abstract void optimizationStopped();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete
	 * (org.opt4j.core.optimizer.Optimizer, int)
	 */
	@Override
	public void iterationComplete(int iteration) {

		boolean logIteration = false;
		if (iterationStep > 0) {
			logIteration = iteration % iterationStep == 0;
		}

		boolean logEvaluation = false;
		if (evaluationStep > 0) {
			logEvaluation = ((evaluationCount.intValue() - evaluationCountLast) >= evaluationStep);
		}

		if (logEvaluation) {
			do {
				evaluationCountLast += evaluationStep;
			} while ((evaluationCount.intValue() - evaluationCountLast) >= evaluationStep);
		}

		if (logIteration || logEvaluation) {
			logEvent(iteration, evaluationCount.intValue());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStarted(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {
		optimizationStarted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStopped(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		optimizationStopped();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j
	 * .core.Individual)
	 */
	@Override
	public void individualStateChanged(Individual individual) {
		if (individual.getState() == State.EVALUATED) {
			if (isFirst) {
				synchronized (this) {
					if (isFirst) {
						logHeader(individual.getObjectives().getKeys());
						isFirst = false;
					}
				}
			}
			evaluationCount.incrementAndGet();
		}
	}

}
