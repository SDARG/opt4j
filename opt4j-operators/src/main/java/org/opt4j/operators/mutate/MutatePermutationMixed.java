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
