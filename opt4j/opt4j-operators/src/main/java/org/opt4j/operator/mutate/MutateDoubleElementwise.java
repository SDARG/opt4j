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

package org.opt4j.operator.mutate;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.operator.normalize.NormalizeDouble;

/**
 * The {@link MutateDoubleElementwise}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class MutateDoubleElementwise extends MutateDouble {

	/**
	 * Constructs a {@link MutateDoubleElementwise}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalizer
	 */
	public MutateDoubleElementwise(Rand random, NormalizeDouble normalize) {
		super(random, normalize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.MutateDouble#mutateList(org.opt4j.genotype.
	 * DoubleGenotype, double)
	 */
	@Override
	protected void mutateList(DoubleGenotype vector, double p) {
		for (int i = 0; i < vector.size(); i++) {
			double x = vector.get(i);
			double y = mutateElement(x, vector.getLowerBound(i), vector.getUpperBound(i), p);

			if (x != y) {
				vector.set(i, y);
			}
		}
	}

	/**
	 * Mutate one element of the list.
	 * 
	 * @param element
	 *            the element
	 * @param lb
	 *            the lower bound
	 * @param ub
	 *            the upper bound
	 * @param p
	 *            the mutation rate
	 * @return the mutated element
	 */
	protected abstract double mutateElement(double element, double lb, double ub, double p);

}
