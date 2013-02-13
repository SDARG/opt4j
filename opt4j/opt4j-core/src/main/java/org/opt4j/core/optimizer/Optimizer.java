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

import com.google.inject.ImplementedBy;

/**
 * <p>
 * This {@link Optimizer} interface has to be implemented by all
 * population-based optimizers.
 * </p>
 * <p>
 * To implement an iteration-based {@link org.opt4j.core.optimizer.Optimizer}s,
 * use the simpler {@link IterativeOptimizer} interface.
 * </p>
 * <p>
 * To bind an {@link Optimizer}, use {@link OptimizerModule#bindOptimizer}.
 * </p>
 * 
 * @author glass, lukasiewycz
 * 
 */
@ImplementedBy(OptimizationMediator.class)
public interface Optimizer {

	/**
	 * Starts the optimization process.
	 * 
	 * @throws StopException
	 *             if the optimization is stopped
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public abstract void optimize() throws StopException, TerminationException;

	/**
	 * Adds an {@link OptimizerIterationListener} to this optimizer.
	 * 
	 * @see #removeOptimizerIterationListener
	 * @param listener
	 *            the OptimizerIterationListener to add
	 */
	public void addOptimizerIterationListener(OptimizerIterationListener listener);

	/**
	 * Removes an {@link OptimizerIterationListener} from this optimizer.
	 * 
	 * @see #addOptimizerIterationListener
	 * @param listener
	 *            the OptimizerIterationListener to remove
	 */
	public void removeOptimizerIterationListener(OptimizerIterationListener listener);

	/**
	 * Adds an {@link OptimizerStateListener} to this optimizer.
	 * 
	 * @see #removeOptimizerStateListener
	 * @param listener
	 *            the OptimizerStateListener to add
	 */
	public void addOptimizerStateListener(OptimizerStateListener listener);

	/**
	 * Removes an {@link OptimizerStateListener} from this optimizer.
	 * 
	 * @see #addOptimizerStateListener
	 * @param listener
	 *            the OptimizerStateListener to remove
	 */
	public void removeOptimizerStateListener(OptimizerStateListener listener);

	/**
	 * Return the current iteration.
	 * 
	 * @return the current iteration
	 */
	public int getIteration();

	/**
	 * Returns {@code true} if the {@link Optimizer} is running.
	 * 
	 * @return {@code true} if the optimizer is running
	 */
	public boolean isRunning();

	/**
	 * This method is called to start the optimization process.
	 */
	public void startOptimization();

	/**
	 * This method is called once the optimization process has stopped.
	 */
	public void stopOptimization();

}
