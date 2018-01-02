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
