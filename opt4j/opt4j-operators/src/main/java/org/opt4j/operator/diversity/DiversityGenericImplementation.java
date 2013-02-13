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

package org.opt4j.operator.diversity;

import org.opt4j.core.Genotype;
import org.opt4j.genotype.CompositeGenotype;
import org.opt4j.operator.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link DiversityGenericImplementation} is a standard implementation of
 * the {@link Diversity} interface.
 * 
 * @author glass, lukasiewycz
 * 
 */
@Singleton
public class DiversityGenericImplementation extends AbstractGenericOperator<Diversity<Genotype>, Diversity<?>>
		implements Diversity<Genotype> {

	/**
	 * Constructs the {@link DiversityGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected DiversityGenericImplementation() {
		super(DiversityBoolean.class, DiversityDouble.class, DiversityInteger.class, DiversityPermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.diversity.Diversity#diversity(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public double diversity(Genotype a, Genotype b) {
		Diversity<Genotype> diversity = getOperator(a);

		if (diversity == null) {
			return diversityComposite((CompositeGenotype<?, ?>) a, (CompositeGenotype<?, ?>) b);
		} else {
			return diversity.diversity(a, b);
		}
	}

	protected double diversityComposite(CompositeGenotype<?, ?> a, CompositeGenotype<?, ?> b) {

		double diversity = 0;
		for (Object key : a.keySet()) {
			Genotype childA = a.get(key);
			Genotype childB = b.get(key);
			int size = childA.size();

			diversity += diversity(childA, childB) * size;
		}
		return diversity / a.size();

	}

}
