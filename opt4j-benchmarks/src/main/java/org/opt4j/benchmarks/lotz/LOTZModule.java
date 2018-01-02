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
 

package org.opt4j.benchmarks.lotz;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

/**
 * <p>
 * The {@link LOTZModule} for the "Leading Ones Trailing Zeros" optimization
 * problem. This is a 2-dimensional optimization problem:
 * </p>
 * 
 * <p>
 * <ol>
 * <li>maximize the consecutive ones from the beginning of a binary string</li>
 * <li>maximize the consecutive ones from the end of a binary string</li>
 * </ol>
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@Info("The 2-dimensional \"Leading Ones Trailing Zeros\" optimization problem.")
public class LOTZModule extends ProblemModule {

	@Info("The length of the binary string.")
	@Constant(value = "size", namespace = LOTZCreator.class)
	protected int size = 30;

	/**
	 * Returns the size of the binary string.
	 * 
	 * @return the size of the binary string
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size of the binary string.
	 * 
	 * @param size
	 *            the size of the binary string to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindProblem(LOTZCreator.class, LOTZDecoder.class, LOTZEvaluator.class);
	}

}
