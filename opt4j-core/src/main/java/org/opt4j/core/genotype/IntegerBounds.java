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
 

package org.opt4j.core.genotype;

import java.util.List;

/**
 * The {@link IntegerBounds} is an implementation of the {@link Bounds} for the
 * {@link IntegerGenotype} that accepts arrays as well as lists for as bounds.
 * 
 * @author lukasiewycz
 * 
 */
public class IntegerBounds implements Bounds<Integer> {

	protected final int[] lower;
	protected final int[] upper;

	/**
	 * Constructs a {@link IntegerBounds} with arrays.
	 * 
	 * @param lower
	 *            the lower bounds
	 * @param upper
	 *            the upper bounds
	 */
	public IntegerBounds(int[] lower, int[] upper) {
		if (lower.length != upper.length) {
			throw new IllegalArgumentException("Lower and upper bounds arrays should have same length.");
		}
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * Constructs a {@link IntegerBounds} with lists.
	 * 
	 * @param lower
	 *            the lower bounds
	 * @param upper
	 *            the upper bounds
	 */
	public IntegerBounds(List<Integer> lower, List<Integer> upper) {
		if (lower.size() != upper.size()) {
			throw new IllegalArgumentException("Lower and upper bounds lists should have the same size.");
		}
		this.lower = new int[lower.size()];
		this.upper = new int[upper.size()];
		for (int i = 0; i < lower.size(); i++) {
			this.lower[i] = lower.get(i);
		}
		for (int i = 0; i < upper.size(); i++) {
			this.upper[i] = upper.get(i);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getLowerBound(int)
	 */
	@Override
	public Integer getLowerBound(int index) {
		return lower[index];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getUpperBound(int)
	 */
	@Override
	public Integer getUpperBound(int index) {
		return upper[index];
	}

}
