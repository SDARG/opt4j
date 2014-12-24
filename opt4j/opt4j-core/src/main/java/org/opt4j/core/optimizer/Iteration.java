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
 * The {@link Iteration} object is used as iteration counter for the
 * optimization.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class Iteration {

	protected int value = 0;
	protected final int maxIterations;

	/**
	 * Constructs a {@link Iteration} object.
	 * 
	 * @param maxIterations
	 *            the maximal number of iterations
	 */
	@Inject
	public Iteration(@MaxIterations int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/**
	 * Returns the current iteration value.
	 * 
	 * @return the current iteration value
	 */
	public int value() {
		return value;
	}

	/**
	 * Increases the iteration value by 1.
	 */
	public void next() {
		value++;
	}

	/**
	 * Returns the maximal number of iterations.
	 * 
	 * @return the maximal number of iterations
	 */
	public int max() {
		return maxIterations;
	}

}
