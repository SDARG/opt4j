/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

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
