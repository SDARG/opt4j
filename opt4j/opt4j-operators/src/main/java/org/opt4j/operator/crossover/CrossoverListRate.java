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

package org.opt4j.operator.crossover;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.ListGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link CrossoverListRate} performs a crossover on
 * {@link org.opt4j.core.Genotype} objects that are lists of values.
 * </p>
 * <p>
 * A point of crossover of the list is selected with a given {@code rate}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public abstract class CrossoverListRate<G extends ListGenotype<?>> implements Crossover<G> {

	protected final Random random;

	protected final double rate;

	/**
	 * Constructs a new {@link CrossoverListRate}.
	 * 
	 * @param rate
	 *            the rate for a crossover
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverListRate(double rate, Rand random) {
		this.rate = rate;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.Crossover#crossover(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Pair<G> crossover(G p1, G p2) {

		ListGenotype<Object> o1 = p1.newInstance();
		ListGenotype<Object> o2 = p2.newInstance();

		int size = p1.size();

		boolean select = random.nextBoolean();

		for (int i = 0; i < size; i++) {
			if (random.nextDouble() < rate) {
				select = !select;
			}

			if (select) {
				o1.add(p1.get(i));
				o2.add(p2.get(i));
			} else {
				o1.add(p2.get(i));
				o2.add(p1.get(i));
			}
		}

		Pair<G> offspring = new Pair<G>((G) o1, (G) o2);
		return offspring;
	}

}
