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

package org.opt4j.optimizer.sa;

import org.opt4j.config.annotations.Info;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;

/**
 * This module binds the {@link SimulatedAnnealing} optimizer.
 * 
 * @author lukasiewycz
 * 
 */
@Info("A probabilistic optimization heuristic that simulates the physical process of annealing."
		+ "Adds the objective values in case of a multi-objective optimization.")
public class SimulatedAnnealingModule extends OptimizerModule {

	@Info("The number of iterations.")
	@MaxIterations
	protected int iterations = 100000;

	/**
	 * Returns the number of iterations.
	 * 
	 * @see #setIterations
	 * @return the number of iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Sets the number of iterations.
	 * 
	 * @see #getIterations
	 * @param iterations
	 *            the number of iterations
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindIterativeOptimizer(SimulatedAnnealing.class);
	}
}
