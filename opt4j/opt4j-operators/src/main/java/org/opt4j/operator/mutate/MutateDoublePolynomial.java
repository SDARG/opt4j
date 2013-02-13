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
 * The {@link MutateDoublePolynomial}.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoublePolynomial extends MutateDoubleElementwise {

	protected final double eta;

	/**
	 * Constructs a {@link MutateDoubleGauss} with a {@link Rand} random number
	 * generator, a {@link NormalizeDouble}, and an eta value.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalizer
	 * @param eta
	 *            the eta value
	 */
	@Inject
	public MutateDoublePolynomial(Rand random, NormalizeDouble normalize,
			@Constant(value = "eta", namespace = MutateDoublePolynomial.class) double eta) {
		super(random, normalize);
		this.eta = eta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.MutateDoubleElementwise#mutateElement(double,
	 * double, double, double)
	 */
	@Override
	protected double mutateElement(double x, double lb, double ub, double p) {
		if (random.nextDouble() < p) {
			double u = random.nextDouble();
			double delta = 0;
			double diff = ub - lb;

			if (u < 0.5) {
				double m = 1.0 - ((x - lb) / diff);
				double b = 2 * u + (1 - 2 * u) * (Math.pow(m, (eta + 1)));
				delta = Math.pow(b, (1.0 / (eta + 1))) - 1.0;
			} else {
				double m = 1.0 - ((ub - x) / diff);
				double b = 2 * (1 - u) + 2 * (u - 0.5) * (Math.pow(m, (eta + 1)));
				delta = 1.0 - Math.pow(b, (1.0 / (eta + 1)));
			}

			return x + delta * diff;
		}
		return x;
	}
}
