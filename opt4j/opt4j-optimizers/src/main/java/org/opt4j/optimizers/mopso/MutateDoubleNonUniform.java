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
import org.opt4j.core.optimizer.Iteration;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link MutateDoubleNonUniform} non-uniformly mutates a
 * {@link DoubleGenotype} elementwise.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoubleNonUniform extends MutateDoubleUniform {

	protected final Iteration iteration;

	/**
	 * Constructs a {@link MutateDoubleNonUniform}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalize operator
	 * @param iteration
	 *            the iteration counter
	 * @param perturbation
	 *            the perturbation index
	 */
	@Inject
	public MutateDoubleNonUniform(Rand random, NormalizeDouble normalize, Iteration iteration,
			@Constant(value = "perturbation", namespace = MutateDoubleNonUniform.class) double perturbation) {
		super(random, normalize, perturbation);
		this.iteration = iteration;
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
			double tau = random.nextBoolean() ? 1 : -1;
			x += tau * delta(perturbation);
		}
		return x;
	}

	/**
	 * Calculates the delta value for the non-uniform mutation.
	 * 
	 * @param perturbation
	 *            the perturbation value
	 * @return the delta value
	 */
	protected double delta(double perturbation) {
		final double diff = 1.0 - 0.0;
		double r = random.nextDouble();

		double actIter = iteration.value();
		double maxIter = iteration.max();
		double ratio = actIter / maxIter;
		ratio = Math.min(ratio, 1.0);

		double y = Math.pow(1.0 - ratio, perturbation);
		return diff * (1.0 - Math.pow(r, y));
	}

}
