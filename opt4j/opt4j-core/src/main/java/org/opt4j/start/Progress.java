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
package org.opt4j.start;

import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link Progress} offers some methods for the measurement of the progress of an {@link Optimizer}.
 * 
 * @see Optimizer
 * @author lukasiewycz
 * 
 */
@Singleton
public class Progress implements OptimizerIterationListener {

	@Inject(optional = true)
	@MaxIterations
	protected Integer maxIterations = null;

	protected int iteration = 0;

	/**
	 * Returns the progress value between {@code 0} and {@code 1}.
	 * 
	 * @return the progress value
	 */
	public Double get() {
		if (maxIterations == null) {
			return null;
		}
		return (double) getCurrentIteration() / (double) getMaxIterations();
	}

	/**
	 * Returns the number of maximal iterations.
	 * 
	 * @return the number of maximal iteration
	 */
	public Integer getMaxIterations() {
		return maxIterations == null ? getCurrentIteration() : maxIterations;
	}

	/**
	 * Returns the current iteration.
	 * 
	 * @return the current iteration
	 */
	public int getCurrentIteration() {
		return Math.min(iteration, maxIterations == null ? iteration : maxIterations);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete (org.opt4j.core.optimizer.Optimizer,
	 * int)
	 */
	@Override
	public void iterationComplete(int iteration) {
		this.iteration = iteration;
	}

}
