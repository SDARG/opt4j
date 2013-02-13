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

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * <p>
 * Crossover for the {@link PermutationGenotype}.
 * </p>
 * 
 * <p>
 * This operator takes sublist of the first permutation from the beginning to a
 * random cut point and fills the remaining elements from the second
 * permutation.
 * </p>
 * 
 * <p>
 * Given two permutations<br />
 * {@code 1 2 3 4 5 6 7 8} and {@code 8 7 6 5 4 3 2 1}.<br />
 * This results, for instance, in<br/>
 * {@code 1 2 3 4 5 8 7 6} or {@code 1 2 8 7 6 5 4 3}.
 * </p>
 * 
 * <p>
 * Additionally, a {@code rotation} value defines if the initial permutation are
 * randomly rotated. With the {@code rotation} possible results would be<br />
 * {@code 4 5 6 8 7 3 2 1} and {@code 7 8 1 2 4 3 6 5}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class CrossoverPermutationOnePoint implements CrossoverPermutation {

	protected final Random random;

	protected final boolean rotation;

	/**
	 * Constructs a new {@link CrossoverPermutationOnePoint}.
	 * 
	 * @param random
	 *            the random number generator
	 * 
	 * @param rotation
	 *            use rotation
	 */
	@Inject
	public CrossoverPermutationOnePoint(Rand random,
			@Constant(value = "rotation", namespace = CrossoverPermutationOnePoint.class) boolean rotation) {
		this.random = random;
		this.rotation = rotation;
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

		if (size > 0) {
			Set<Object> elements1 = new HashSet<Object>();
			Set<Object> elements2 = new HashSet<Object>();

			int offset = rotation ? random.nextInt(size) : 0;

			int cutpoint = random.nextInt(size);

			for (int i = 0; i < cutpoint; i++) {
				final int pos = (offset + i) % size;
				Object e1 = p1.get(pos);
				Object e2 = p2.get(pos);
				o1.add(e1);
				o2.add(e2);
				elements1.add(e1);
				elements2.add(e2);
			}

			offset = rotation ? random.nextInt(size) : 0;

			for (int i = 0; i < size; i++) {
				final int pos = (offset + i) % size;
				Object e1 = p1.get(pos);
				Object e2 = p2.get(pos);
				if (!elements1.contains(e2)) {
					o1.add(e2);
				}
				if (!elements2.contains(e1)) {
					o2.add(e1);
				}
			}
		}

		Pair<PermutationGenotype<?>> offspring = new Pair<PermutationGenotype<?>>(o1, o2);
		return offspring;
	}
}
