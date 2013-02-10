/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.operator.normalize;

import org.opt4j.genotype.DoubleGenotype;

/**
 * The {@link NormalizeDoubleWrap} normalizes the {@link DoubleGenotype} by
 * wrapping the values at the borders.
 * 
 * @author lukasiewycz
 * 
 */
public class NormalizeDoubleWrap extends NormalizeDoubleElementwise {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.normalize.NormalizeDoubleElementwise#normalize(double,
	 * double, double)
	 */
	@Override
	public double normalize(double value, double lb, double ub) {
		double diff = ub - lb;
		if (value < lb) {
			double distance = lb - value;
			value += Math.ceil(distance / diff) * diff;
		}
		if (value > ub) {
			double distance = value - ub;
			value -= Math.ceil(distance / diff) * diff;
		}
		return value;
	}

}
