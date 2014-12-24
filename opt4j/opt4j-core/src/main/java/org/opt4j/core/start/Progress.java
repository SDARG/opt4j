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
 
package org.opt4j.core.start;

import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerIterationListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link Progress} offers some methods for the measurement of the progress
 * of an {@link Optimizer}.
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
	 * @see
	 * org.opt4j.core.optimizer.OptimizerIterationListener#iterationComplete
	 * (org.opt4j.core.optimizer.Optimizer, int)
	 */
	@Override
	public void iterationComplete(int iteration) {
		this.iteration = iteration;
	}

}
