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

package org.opt4j.operators.crossover;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the {@link Crossover} interface.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class CrossoverGenericImplementation extends AbstractGenericOperator<Crossover<Genotype>, Crossover<?>>
		implements Crossover<Genotype> {

	/**
	 * Constructs the {@link CrossoverGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected CrossoverGenericImplementation() {
		super(CrossoverBoolean.class, CrossoverDouble.class, CrossoverInteger.class, CrossoverPermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.Crossover#crossover(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public Pair<Genotype> crossover(Genotype parent1, Genotype parent2) {
		Crossover<Genotype> crossover = getOperator(parent1);

		if (crossover == null) {
			return crossoverComposite((CompositeGenotype<?, ?>) parent1, (CompositeGenotype<?, ?>) parent2);
		} else {
			return crossover.crossover(parent1, parent2);
		}
	}

	protected Pair<Genotype> crossoverComposite(CompositeGenotype<?, ?> p1, CompositeGenotype<?, ?> p2) {
		CompositeGenotype<Object, Genotype> o1 = p1.newInstance();
		CompositeGenotype<Object, Genotype> o2 = p2.newInstance();

		for (Object key : p1.keySet()) {
			final Genotype g1 = p1.get(key);
			final Genotype g2 = p2.get(key);
			Pair<Genotype> genotype = crossover(g1, g2);
			o1.put(key, genotype.getFirst());
			o2.put(key, genotype.getSecond());
		}

		Pair<Genotype> offspring = new Pair<Genotype>(o1, o2);
		return offspring;
	}
}
