/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

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
		double element = x;
		if (random.nextDouble() < p) {
			double tau = random.nextBoolean() ? 1 : -1;
			element += tau * delta(perturbation);
		}
		return element;
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
