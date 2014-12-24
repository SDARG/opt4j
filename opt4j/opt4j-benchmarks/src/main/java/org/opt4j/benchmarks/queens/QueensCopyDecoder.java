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
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

/**
 * A simple decoder strategy. Each field has a binary value that indicates
 * whether a queen is located on it or not.
 * 
 * The genotype is a {@link BooleanGenotype} of the length {@code size*size}.
 * 
 * @author lukasiewycz
 * 
 */
public class QueensCopyDecoder implements Decoder<BooleanGenotype, QueensBoard>, Creator<BooleanGenotype> {

	protected final QueensProblem problem;
	protected final Random random;

	/**
	 * Constructs a {@link QueensCopyDecoder}.
	 * 
	 * @param problem
	 *            the queens problem
	 * @param random
	 *            the random value generator
	 */
	@Inject
	public QueensCopyDecoder(QueensProblem problem, Rand random) {
		this.problem = problem;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Creator#create()
	 */
	@Override
	public BooleanGenotype create() {
		int size = problem.size();

		BooleanGenotype vector = new BooleanGenotype();
		vector.init(random, size * size);

		return vector;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Decoder#decode(org.opt4j.core.Genotype)
	 */
	@Override
	public QueensBoard decode(BooleanGenotype vector) {
		int size = problem.size();

		QueensBoard queensBoard = new QueensBoard(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				queensBoard.setQueen(i, j, vector.get(i + j * size));
			}
		}

		return queensBoard;
	}

}
