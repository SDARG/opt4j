/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.core.optimizer;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link OptimizationMediator} performs the overall optimization process
 * for the {@link IterativeOptimizer}.
 * 
 * @author reimann, glass, lukasiewycz
 * 
 */
@Singleton
public class OptimizationMediator extends AbstractOptimizer {

	protected final IterativeOptimizer iterativeOptimizer;

	/**
	 * Creates a new {@link OptimizationMediator}.
	 * 
	 * @param iterativeOptimizer
	 *            the iterative optimizer to use
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
	@Inject
	public OptimizationMediator(IterativeOptimizer iterativeOptimizer, Population population, Archive archive,
			IndividualCompleter completer, Control control, Iteration iteration) {
		super(population, archive, completer, control, iteration);
		this.iterativeOptimizer = iterativeOptimizer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Optimizer#optimize()
	 */
	@Override
	public void optimize() throws StopException, TerminationException {
		iterativeOptimizer.initialize();
		while (iteration.value() < iteration.max()) {
			iterativeOptimizer.next();
			nextIteration();
		}
	}
}
