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

package org.opt4j.optimizers.mopso;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.mutate.MutateDoubleElementwise;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link MutateDoubleUniform} uniformly mutates a {@link DoubleGenotype}
 * elementwise.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoubleUniform extends MutateDoubleElementwise {

	protected final double perturbation;

	/**
	 * Constructs a {@link MutateDoubleUniform}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalize operator
	 * @param perturbation
	 *            the perturbation index
	 */
	@Inject
	public MutateDoubleUniform(Rand random, NormalizeDouble normalize,
			@Constant(value = "perturbation", namespace = MutateDoubleNonUniform.class) double perturbation) {
		super(random, normalize);
		this.perturbation = perturbation;
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
			double v = (random.nextDouble() - 0.5) * perturbation;
			x += v;
		}
		return x;
	}

}
