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

package org.opt4j.operator.crossover;

import java.util.List;

import org.opt4j.common.random.Rand;
import org.opt4j.operator.normalize.NormalizeDouble;

/**
 * The {@link CrossoverDoubleElementwise} can be used to derive
 * {@link CrossoverDouble} classOperators that can work element-wise on the
 * double vectors.
 * 
 * @author glass
 * 
 */
public abstract class CrossoverDoubleElementwise extends CrossoverDouble {

	/**
	 * Constructs a new {@link CrossoverDoubleElementwise}.
	 * 
	 * @param normalize
	 *            the normalize operator
	 * @param random
	 *            the random number generator
	 */
	public CrossoverDoubleElementwise(NormalizeDouble normalize, Rand random) {
		super(normalize, random);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.CrossoverDouble#crossover(java.util.List,
	 * java.util.List, java.util.List, java.util.List)
	 */
	@Override
	protected void crossover(List<Double> p1, List<Double> p2, List<Double> o1, List<Double> o2) {
		int size = p1.size();

		for (int i = 0; i < size; i++) {
			Pair<Double> values = crossover(p1.get(i), p2.get(i));
			o1.add(values.getFirst());
			o2.add(values.getSecond());
		}
	}

	/**
	 * Performs a crossover with two double values.
	 * 
	 * @param x
	 *            the first value
	 * @param y
	 *            the second value
	 * @return the resulting values
	 */
	public abstract Pair<Double> crossover(double x, double y);

}
