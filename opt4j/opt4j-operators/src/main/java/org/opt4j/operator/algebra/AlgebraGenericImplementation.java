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

package org.opt4j.operator.algebra;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.core.Genotype;
import org.opt4j.genotype.CompositeGenotype;
import org.opt4j.operator.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link AlgebraGenericImplementation} is a standard implementation of the
 * {@link Algebra} interface.
 * 
 * @author glass, lukasiewycz
 * 
 */
@Singleton
public class AlgebraGenericImplementation extends AbstractGenericOperator<Algebra<Genotype>, Algebra<?>> implements
		Algebra<Genotype> {

	/**
	 * Constructs the {@link AlgebraGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected AlgebraGenericImplementation() {
		super(AlgebraDouble.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.algebra.Algebra#algebra(org.opt4j.operator.algebra
	 * .Term, org.opt4j.core.Genotype[])
	 */
	@Override
	public Genotype algebra(Term term, Genotype... genotypes) {
		Algebra<Genotype> algebra = getOperator(genotypes[0]);
		if (algebra == null) {
			return algebraComposite(term, genotypes);
		} else {
			return algebra.algebra(term, genotypes);
		}
	}

	protected CompositeGenotype<?, ?> algebraComposite(Term term, Genotype... genotypes) {
		int n = genotypes.length;
		List<CompositeGenotype<?, ?>> composites = new ArrayList<CompositeGenotype<?, ?>>();
		for (int i = 0; i < n; i++) {
			composites.add((CompositeGenotype<?, ?>) genotypes[i]);
		}

		CompositeGenotype<Object, Genotype> offspring = composites.get(0).newInstance();

		for (Object key : composites.get(0).keySet()) {
			Genotype[] g = new Genotype[n];
			for (int i = 0; i < n; i++) {
				if (composites.get(i) == null) {
					g[i] = null;
				} else {
					Genotype genotype = composites.get(i).get(key);
					g[i] = genotype;
				}
			}
			Genotype value = algebra(term, g);
			offspring.put(key, value);
		}

		return offspring;
	}

}
