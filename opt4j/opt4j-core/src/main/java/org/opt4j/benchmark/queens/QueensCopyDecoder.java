/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.benchmark.queens;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.genotype.BooleanGenotype;

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
