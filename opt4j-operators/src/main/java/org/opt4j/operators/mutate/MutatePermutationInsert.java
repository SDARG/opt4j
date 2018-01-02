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
 * Mutate for the {@link PermutationGenotype}. With a given mutation rate each
 * element is inserted on a different position.
 * </p>
 * 
 * <p>
 * Given a permutation {@code 1 2 3 4 5 6 7 8}, this might result in {@code 1 2
 * 6 3 4 5 7 8}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class MutatePermutationInsert implements MutatePermutation {

	protected final Random random;

	/**
	 * Constructs a new {@link MutatePermutation} with the given mutation rate.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public MutatePermutationInsert(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mutate(PermutationGenotype<?> genotype, double p) {
		PermutationGenotype<Object> permutation = (PermutationGenotype<Object>) genotype;
		int size = permutation.size();

		if (size > 1) {
			for (int i = 0; i < size; i++) {
				if (random.nextDouble() < p) {
					Object object = permutation.remove(i);
					int j = random.nextInt(permutation.size());

					permutation.add(j, object);
				}
			}
		}
	}

}
