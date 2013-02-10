/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.operator.algebra;

import org.opt4j.core.Genotype;
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.operator.normalize.NormalizeDouble;

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
