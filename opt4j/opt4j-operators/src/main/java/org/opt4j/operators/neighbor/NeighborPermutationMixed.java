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
 

package org.opt4j.operators.neighbor;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.PermutationGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * Randomly selects between {@link NeighborPermutationSwap},
 * {@link NeighborPermutationInsert}, and {@link NeighborPermutationRevert}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborPermutationMixed implements NeighborPermutation {

	protected final Random random;

	protected final NeighborPermutationSwap swap;

	protected final NeighborPermutationInsert insert;

	protected final NeighborPermutationRevert revert;

	/**
	 * Constructs a {@link NeighborPermutationMixed} operator for the
	 * {@link PermutationGenotype}.
	 * 
	 * @param swap
	 *            the swap permutation
	 * @param insert
	 *            the insert permutation
	 * @param revert
	 *            the revert permutation
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborPermutationMixed(NeighborPermutationSwap swap, NeighborPermutationInsert insert,
			NeighborPermutationRevert revert, Rand random) {
		this.swap = swap;
		this.insert = insert;
		this.random = random;
		this.revert = revert;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(PermutationGenotype<?> genotype) {
		if (random.nextDouble() < 0.33) {
			swap.neighbor(genotype);
		} else if (random.nextBoolean()) {
			insert.neighbor(genotype);
		} else {
			revert.neighbor(genotype);
		}
	}
}
