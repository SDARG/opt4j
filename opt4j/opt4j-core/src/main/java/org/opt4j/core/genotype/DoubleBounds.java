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

import java.util.List;

/**
 * The {@link DoubleBounds} is an implementation of the {@link Bounds} for the
 * {@link DoubleGenotype} that accepts arrays as well as lists as bounds.
 * 
 * @author lukasiewycz
 * 
 */
public class DoubleBounds implements Bounds<Double> {

	protected final double[] lower;
	protected final double[] upper;

	/**
	 * Constructs a {@link DoubleBounds} with arrays.
	 * 
	 * @param lower
	 *            the lower bounds
	 * @param upper
	 *            the upper bounds
	 */
	public DoubleBounds(double[] lower, double[] upper) {
		this.lower = lower;
		this.upper = upper;
	}

	/**
	 * Constructs a {@link DoubleBounds} with lists.
	 * 
	 * @param lower
	 *            the lower bounds
	 * @param upper
	 *            the upper bounds
	 */
	public DoubleBounds(List<Double> lower, List<Double> upper) {
		this.lower = new double[lower.size()];
		this.upper = new double[upper.size()];
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
	public Double getLowerBound(int index) {
		return lower[index];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.Bounds#getUpperBound(int)
	 */
	@Override
	public Double getUpperBound(int index) {
		return upper[index];
	}

}
