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
