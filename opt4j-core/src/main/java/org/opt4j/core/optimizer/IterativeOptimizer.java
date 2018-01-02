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

/**
 * <p>
 * This {@link IterativeOptimizer} interface has to be implemented by all
 * population-based iterative optimizers.
 * </p>
 * <p>
 * To bind an {@link IterativeOptimizer}, use
 * {@link OptimizerModule#bindIterativeOptimizer}.
 * </p>
 * 
 * @author reimann, glass, lukasiewycz
 * 
 */
public interface IterativeOptimizer {

	/**
	 * This method is called a single time before the optimization process is
	 * started. It can be used to initialize used data structures, etc.
	 * 
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public void initialize() throws TerminationException;

	/**
	 * Performs the next iteration in the optimization process.
	 * 
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public void next() throws TerminationException;
}
