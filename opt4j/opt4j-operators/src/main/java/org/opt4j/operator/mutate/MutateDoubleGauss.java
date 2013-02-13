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
import org.opt4j.operator.normalize.NormalizeDouble;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link MutateDoubleGauss}.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoubleGauss extends MutateDoubleElementwise {

	protected final double sigma;

	/**
	 * Constructs a {@link MutateDoubleGauss}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalizer
	 * @param sigma
	 *            the sigma value
	 */
	@Inject
	public MutateDoubleGauss(Rand random, NormalizeDouble normalize,
			@Constant(value = "sigma", namespace = MutateDoubleGauss.class) double sigma) {
		super(random, normalize);
		this.sigma = sigma;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.MutateDoubleElementwise#mutateElement(double,
	 * double, double, double)
	 */
	@Override
	protected double mutateElement(double element, double lb, double ub, double p) {
		if (random.nextDouble() < p) {
			element += sigma * random.nextGaussian();
		}
		return element;
	}

}
