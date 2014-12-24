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


package org.opt4j.benchmarks.queens;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

/**
 * A permutation decoder strategy. The permutation contains the values
 * {@code 1,...,size}. This value indicates for each row in which column a queen
 * is. This strategy ensures that queens do not attack each other in rows and
 * columns.
 * 
 * The genotype is a {@link PermutationGenotype} of the length {@code size}.
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class QueensPermutationDecoder implements Decoder<PermutationGenotype<Integer>, QueensBoard>,
		Creator<PermutationGenotype<Integer>> {

	protected final QueensProblem problem;
	protected final Random random;

	/**
	 * Constructs a {@link QueensPermutationDecoder}.
	 * 
	 * @param problem
	 *            the queens problem
	 * @param random
	 *            the random number generator
	 * 
	 */
	@Inject
	public QueensPermutationDecoder(QueensProblem problem, Rand random) {
		this.problem = problem;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Creator#create()
	 */
	@Override
	public PermutationGenotype<Integer> create() {
		int size = problem.size();

		PermutationGenotype<Integer> genotype = new PermutationGenotype<Integer>();
		for (int i = 0; i < size; i++) {
			genotype.add(i);
		}
		genotype.init(random);

		return genotype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Decoder#decode(org.opt4j.core.Genotype)
	 */
	@Override
	public QueensBoard decode(PermutationGenotype<Integer> permutation) {
		int size = problem.size();

		QueensBoard queensBoard = new QueensBoard(size);

		for (int i = 0; i < permutation.size(); i++) {
			queensBoard.setQueen(i, permutation.get(i), true);
		}

		return queensBoard;
	}

}
