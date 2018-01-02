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
 

package org.opt4j.operators.mutate;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Constant mutation rate. The {@link MutationRate} is set once.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class ConstantMutationRate implements MutationRate {

	protected final double rate;

	/**
	 * Constructs a {@link ConstantMutationRate} with a given value.
	 * 
	 * @param rate
	 *            the mutation rate value
	 */
	@Inject
	public ConstantMutationRate(@Constant(value = "rate", namespace = ConstantMutationRate.class) double rate) {
		this.rate = rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#get()
	 */
	@Override
	public double get() {
		return rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#set(double)
	 */
	@Override
	public void set(double value) {
		// do nothing
	}

}
