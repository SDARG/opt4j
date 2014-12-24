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
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.operators.normalize.NormalizeDouble;

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
