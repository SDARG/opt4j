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
 * The {@link DoubleGenotype} consists of double values that can be used as a
 * {@link Genotype}.
 * </p>
 * <p>
 * Example problem: Select filling level of five bottles<br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * DoubleGenotype&lt;Switch&gt; genotype = new DoubleGenotype();
 * genotype.init(new Random(), 5);
 * </pre>
 * 
 * </blockquote> Example instance: [0.5035947840006195, 0.9693492473483428,
 * 0.12786372316728167, 0.5299369900029843, 0.8055193291478467]<br/>
 * Example search space size: [0;1]<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DoubleGenotype extends ArrayList<Double> implements ListGenotype<Double> {

	protected final Bounds<Double> bounds;

	/**
	 * Constructs a {@link DoubleGenotype} with lower bounds {@code 0.0} and
	 * upper bounds {@code 1.0}.
	 */
	public DoubleGenotype() {
		this(0, 1);
	}

	/**
	 * Constructs a {@link DoubleGenotype} with a specified lower and upper
	 * bound for all values.
	 * 
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public DoubleGenotype(double lowerBound, double upperBound) {
		this(new FixedBounds<Double>(lowerBound, upperBound));
	}

	/**
	 * Constructs a {@link DoubleGenotype} with the given {@link Bounds}.
	 * 
	 * @param bounds
	 *            the bounds
	 */
	public DoubleGenotype(Bounds<Double> bounds) {
		this.bounds = bounds;
	}

	/**
	 * Returns the lower bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the lower bound of the {@code i}-th element
	 */
	public double getLowerBound(int index) {
		return bounds.getLowerBound(index);
	}

	/**
	 * Returns the upper bound for the {@code i}-th element.
	 * 
	 * @param index
	 *            the {@code i}-th element
	 * @return the upper bound of the {@code i}-th element
	 */
	public double getUpperBound(int index) {
		return bounds.getUpperBound(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends DoubleGenotype> cstr = this.getClass().getConstructor(Bounds.class);
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
		for (int i = 0; i < n; i++) {
			double lo = getLowerBound(i);
			double hi = getUpperBound(i);
			double value = lo + random.nextDouble() * (hi - lo);
			if (i >= size()) {
				add(value);
			} else {
				set(i, value);
			}
		}
	}
}
