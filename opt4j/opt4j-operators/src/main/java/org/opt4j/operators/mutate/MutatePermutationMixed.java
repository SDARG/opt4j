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

package org.opt4j.operators.mutate;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.PermutationGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * Mutate for the {@link PermutationGenotype}. Randomly selects between
 * {@link MutatePermutationSwap}, {@link MutatePermutationInsert}, and
 * {@link MutatePermutationRevert}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class MutatePermutationMixed implements MutatePermutation {

	protected final Random random;

	protected final MutatePermutationSwap swap;

	protected final MutatePermutationInsert insert;

	protected final MutatePermutationRevert revert;

	/**
	 * Constructs a new {@link MutatePermutation} with the given mutation rate.
	 * 
	 * 
	 * @param swap
	 *            the swap mutate
	 * @param insert
	 *            the insert mutate
	 * @param revert
	 *            the revert mutate
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public MutatePermutationMixed(final MutatePermutationSwap swap, final MutatePermutationInsert insert,
			final MutatePermutationRevert revert, Rand random) {
		this.swap = swap;
		this.insert = insert;
		this.revert = revert;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.Genotype)
	 */
	@Override
	public void mutate(PermutationGenotype<?> genotype, double p) {
		if (random.nextDouble() < 0.33) {
			swap.mutate(genotype, p);
		} else if (random.nextBoolean()) {
			insert.mutate(genotype, p);
		} else {
			revert.mutate(genotype, p);
		}
	}

}
