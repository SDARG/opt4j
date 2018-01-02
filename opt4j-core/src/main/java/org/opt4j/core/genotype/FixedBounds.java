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

/**
 * The {@link FixedBounds} are {@link Bounds} that return a fixed lower and
 * upper bound for each index.
 * 
 * @author lukasiewycz
 * 
 * @param <E>
 *            the type of the bounds
 */
class FixedBounds<E extends Number> implements Bounds<E> {

	protected final E lowerBound;
	protected final E upperBound;

	/**
	 * Construct a {@link FixedBounds}.
	 * 
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public FixedBounds(E lowerBound, E upperBound) {
		super();
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getLowerBound(int)
	 */
	@Override
	public E getLowerBound(int index) {
		return lowerBound;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getUpperBound(int)
	 */
	@Override
	public E getUpperBound(int index) {
		return upperBound;
	}

}
