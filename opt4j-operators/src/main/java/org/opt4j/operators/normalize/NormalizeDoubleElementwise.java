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
 
package org.opt4j.operators.normalize;

import org.opt4j.core.genotype.DoubleGenotype;

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
