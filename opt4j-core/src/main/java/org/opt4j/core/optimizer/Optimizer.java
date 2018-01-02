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
