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
 

package org.opt4j.operators.mutate;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.normalize.NormalizeDouble;

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
