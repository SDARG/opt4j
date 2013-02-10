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

package org.opt4j.operator.mutate;

import org.opt4j.core.Genotype;
import org.opt4j.genotype.CompositeGenotype;
import org.opt4j.operator.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the {@link Mutate} interface.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class MutateGenericImplementation extends AbstractGenericOperator<Mutate<Genotype>, Mutate<?>> implements
		Mutate<Genotype> {

	/**
	 * Constructs the {@link MutateGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected MutateGenericImplementation() {
		super(MutateBoolean.class, MutateDouble.class, MutateInteger.class, MutatePermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(Genotype genotype, double p) {
		Mutate<Genotype> mutate = getOperator(genotype);

		if (mutate == null) {
			mutateComposite((CompositeGenotype<?, ?>) genotype, p);
		} else {
			mutate.mutate(genotype, p);
		}
	}

	protected void mutateComposite(CompositeGenotype<?, ?> genotype, double p) {
		for (Genotype entry : genotype.values()) {
			mutate(entry, p);
		}
	}
}
