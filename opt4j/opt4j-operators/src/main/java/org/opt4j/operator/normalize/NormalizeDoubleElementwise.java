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
package org.opt4j.operator.normalize;

import org.opt4j.genotype.DoubleGenotype;

/**
 * <p>
 * The {@link NormalizeDoubleElementwise} normalizes {@link DoubleGenotype}s
 * elementwise.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public abstract class NormalizeDoubleElementwise implements NormalizeDouble {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.normalize.Normalize#normalize(org.opt4j.core.problem
	 * .Genotype)
	 */
	@Override
	public void normalize(DoubleGenotype genotype) {
		int size = genotype.size();

		for (int i = 0; i < size; i++) {
			double value = genotype.get(i);
			double lb = genotype.getLowerBound(i);
			double ub = genotype.getUpperBound(i);

			if (value < lb || ub < value) {
				value = normalize(value, lb, ub);
				assert (lb <= value && value <= ub);
				genotype.set(i, value);
			}
		}

	}

	/**
	 * Normalize a double value.
	 * 
	 * @param value
	 *            the value to be normalized
	 * @param lb
	 *            the lower bound
	 * @param ub
	 *            the upper bound
	 * @return a normalize value in the bounds
	 */
	public abstract double normalize(double value, double lb, double ub);

}
