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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link IntegerGenotype} is a {@link Genotype} that consists of
 * {@link Integer} values.
 * </p>
 * <p>
 * Example problem: Select the outcome of throwing five dice<br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * IntegerGenotype genotype = new IntegerGenotype(1, 6);
 * genotype.init(new Random(), 5);
 * </pre>
 * 
 * </blockquote> Example instance: [3, 5, 6, 1, 3]<br/>
 * Example search space size: 6<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class IntegerGenotype extends ArrayList<Integer> implements ListGenotype<Integer> {

	protected final Bounds<Integer> bounds;

	/**
	 * Constructs a {@link IntegerGenotype} with a specified lower and upper
	 * bound for all values.
	 * 
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public IntegerGenotype(int lowerBound, int upperBound) {
		this(new FixedBounds<Integer>(lowerBound, upperBound));
	}

	/**
	 * Constructs a {@link IntegerGenotype} with the given {@link Bounds}.
	 * 
	 * @param bounds
	 *            the bounds
	 */
	public IntegerGenotype(Bounds<Integer> bounds) {
		this.bounds = bounds;
	}

	/**
	 * Returns the lower bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the lower bound of the {@code i}-th element
	 */
	public int getLowerBound(int index) {
		return bounds.getLowerBound(index);
	}

	/**
	 * Returns the upper bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the upper bound of the {@code i}-th element
	 */
	public int getUpperBound(int index) {
		return bounds.getUpperBound(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends IntegerGenotype> cstr = this.getClass().getConstructor(Bounds.class);
			return (G) cstr.newInstance(bounds);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize this genotype with {@code n} random values.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the number of elements in the resulting genotype
	 */
	public void init(Random random, int n) {
		try {
			getLowerBound(n - 1);
			getUpperBound(n - 1);
		} catch (IndexOutOfBoundsException outOfBoundException) {
			throw new IllegalArgumentException(
					"Can not initialize a genotype with " + n + " entries with the specified bounds");
		}

		for (int i = 0; i < n; i++) {
			int lo = getLowerBound(i);
			int hi = getUpperBound(i);
			int value = lo + random.nextInt(hi - lo + 1);
			if (i >= size()) {
				add(value);
			} else {
				set(i, value);
			}
		}
	}

	private static final long serialVersionUID = 1L;
}
