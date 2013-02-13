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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.PermutationGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * Crossover for the {@link PermutationGenotype}.
 * </p>
 * 
 * <p>
 * The bucket operator randomly runs through to permutation from the beginning
 * to the end, and fill the current element into the new permutation if not
 * existent.
 * </p>
 * 
 * <p>
 * Given two permutations
 * </p>
 * <p>
 * {@code 1 2 3 4 5 6 7 8} and {@code 8 7 6 5 4 3 2 1}
 * </p>
 * <p>
 * this results, for instance, in
 * </p>
 * <p>
 * {@code 1 8 2 7 3 6 4 5} or {@code 8 7 1 2 3 6 5 4}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class CrossoverPermutationBucket implements CrossoverPermutation {

	protected final Random random;

	/**
	 * Constructs a new {@link CrossoverPermutationBucket}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverPermutationBucket(Rand random) {
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
	public Pair<PermutationGenotype<?>> crossover(PermutationGenotype<?> p1, PermutationGenotype<?> p2) {

		PermutationGenotype<Object> o1 = p1.newInstance();
		PermutationGenotype<Object> o2 = p1.newInstance();

		int size = p1.size();

		assert (size == p2.size()) : "Permutation is undefined for genotypes with different lengths.";
		assert p1.containsAll(p2) : "Permutation is undefined for different domains.";

		Set<Object> elements = new HashSet<Object>();

		int i = 0;
		int j = 0;

		while (o1.size() != size || o2.size() != size) {
			if (j == size || (random.nextBoolean() && i < size)) {
				Object e = p1.get(i);
				i++;
				if (elements.add(e)) {
					o1.add(e);
				} else {
					o2.add(e);
				}
			} else {
				Object e = p2.get(j);
				j++;

				if (elements.add(e)) {
					o1.add(e);
				} else {
					o2.add(e);
				}
			}
		}

		Pair<PermutationGenotype<?>> offspring = new Pair<PermutationGenotype<?>>(o1, o2);
		return offspring;
	}
}