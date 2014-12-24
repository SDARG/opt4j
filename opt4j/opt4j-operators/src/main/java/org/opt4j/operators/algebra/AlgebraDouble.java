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


package org.opt4j.operators.algebra;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link AlgebraDouble} for {@link DoubleGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class AlgebraDouble implements Algebra<DoubleGenotype> {

	protected final NormalizeDouble normalize;

	/**
	 * Constructs a {@link AlgebraDouble} .
	 * 
	 * @param normalize
	 *            the normalize operator for double values
	 */
	@Inject
	public AlgebraDouble(final NormalizeDouble normalize) {
		super();
		this.normalize = normalize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.algebra.Algebra#algebra(org.opt4j.operator.algebra
	 * .Term, org.opt4j.core.Genotype[])
	 */
	@Override
	public DoubleGenotype algebra(Term term, Genotype... genotypes) {
		int n = genotypes.length;
		assert (n > 0);

		DoubleGenotype[] list = new DoubleGenotype[n];

		for (int i = 0; i < n; i++) {
			list[i] = (DoubleGenotype) genotypes[i];
		}

		DoubleGenotype offspring = list[0].newInstance();
		offspring.clear();

		int size = list[0].size();

		double[] values = new double[n];

		for (int j = 0; j < size; j++) {
			for (int i = 0; i < n; i++) {
				if (list[i] == null) {
					values[i] = 0;
				} else {
					values[i] = list[i].get(j);
				}
			}
			double result = term.calculate(values);
			offspring.add(result);
		}

		normalize.normalize(offspring);

		return offspring;
	}

}
