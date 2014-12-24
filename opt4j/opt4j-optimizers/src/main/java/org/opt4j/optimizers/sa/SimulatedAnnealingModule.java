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
 

package org.opt4j.optimizers.sa;

import org.opt4j.core.config.annotations.Info;
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
