/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.core.optimizer;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.IndividualFactory;

import com.google.inject.Inject;

/**
 * The {@link AbstractOptimizer} is an abstract implementation of a population-based {@link Optimizer}. It provides the
 * necessary coupling to the framework by informing all {@link OptimizerStateListener}s and
 * {@link OptimizerIterationListener}s, calling the {@link IndividualCompleter} to decode and evaluate new
 * {@link org.opt4j.core.Individual}s and adding them to the {@link Archive}. The {@link Control} is checked regularly
 * to allow user interaction.
 * 
 * To implement an iteration-based {@link Optimizer}, the {@link IterativeOptimizer} interface is available.
 * 
 * @author glass, lukasiewycz
 * 
 */
public abstract class AbstractOptimizer implements Optimizer {

	protected final Iteration iteration;

	protected final Population population;

	protected final Archive archive;

	protected final IndividualCompleter completer;

	protected final Control control;

	protected final Set<OptimizerStateListener> stateListeners = new CopyOnWriteArraySet<OptimizerStateListener>();

	protected final Set<OptimizerIterationListener> iterationListeners = new CopyOnWriteArraySet<OptimizerIterationListener>();

	protected boolean optimizing = false;

	/**
	 * Constructs an {@link AbstractOptimizer} with a {@link Population}, an {@link Archive}, an
	 * {@link IndividualFactory}, and a {@link IndividualCompleter}.
	 * 
	 * @param population
	 *            the specified population
	 * @param archive
	 *            the specified archive
	 * @param completer
	 *            the specified completer
	 * @param control
	 *            the control
	 * @param iteration
	 *            the iteration counter
	 */
	public AbstractOptimizer(Population population, Archive archive, IndividualCompleter completer, Control control,
			Iteration iteration) {
		this.population = population;
		this.archive = archive;
		this.completer = completer;
		this.control = control;
		this.iteration = iteration;
	}

	@Inject
	protected void injectListeners(Set<OptimizerStateListener> stateListeners,
			Set<OptimizerIterationListener> iterationListeners) {
		this.stateListeners.addAll(stateListeners);
		this.iterationListeners.addAll(iterationListeners);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#getIteration()
	 */
	@Override
	public int getIteration() {
		return iteration.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return optimizing;
	}

	/**
	 * Call this method if a new iteration started.
	 * 
	 * @throws StopException
	 *             if the optimization is stopped
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	protected void nextIteration() throws TerminationException, StopException {
		completer.complete(population);
		archive.update(population);
		iteration.next();
		for (OptimizerIterationListener listener : iterationListeners) {
			listener.iterationComplete(iteration.value());
		}
		control.checkpointStop();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#startOptimization()
	 */
	@Override
	public void startOptimization() {
		optimizing = true;
		for (OptimizerStateListener listener : stateListeners) {
			listener.optimizationStarted(this);
		}
		try {
			optimize();
		} catch (StopException e) {
			System.out.println("Optimization stopped.");
		} catch (TerminationException e) {
			System.err.println("Optimization terminated.");
		} finally {
			stopOptimization();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#stopOptimization()
	 */
	@Override
	public void stopOptimization() {
		optimizing = false;
		for (OptimizerStateListener listener : stateListeners) {
			listener.optimizationStopped(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#addOptimizerIterationListener(org.
	 * opt4j.core.optimizer.OptimizerIterationListener)
	 */
	@Override
	public void addOptimizerIterationListener(OptimizerIterationListener listener) {
		iterationListeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#addOptimizerStateListener(org.opt4j
	 * .core.optimizer.OptimizerStateListener)
	 */
	@Override
	public void addOptimizerStateListener(OptimizerStateListener listener) {
		stateListeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#removeOptimizerIterationListener(org
	 * .opt4j.core.optimizer.OptimizerIterationListener)
	 */
	@Override
	public void removeOptimizerIterationListener(OptimizerIterationListener listener) {
		iterationListeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#removeOptimizerStateListener(org.opt4j
	 * .core.optimizer.OptimizerStateListener)
	 */
	@Override
	public void removeOptimizerStateListener(OptimizerStateListener listener) {
		stateListeners.remove(listener);
	}

}
