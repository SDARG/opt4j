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

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link QueensProblem} information. Contains the size of the board and the
 * costs of the fields.
 * 
 * @author lukasiewycz
 * 
 */
public class QueensProblem {

	protected final int[][][] board;

	protected final int size;

	protected final int dim;

	protected final Random random;

	/**
	 * Constructs a new {@link QueensProblem}.
	 * 
	 * @param size
	 *            the size of the board
	 * @param dim
	 *            the number of objective functions
	 * @param seed
	 *            the seed for the random number generator
	 */
	@Inject
	public QueensProblem(@Constant(value = "size", namespace = QueensProblem.class) int size,
			@Constant(value = "dim", namespace = QueensProblem.class) int dim,
			@Constant(value = "seed", namespace = QueensProblem.class) int seed) {
		random = new Random(seed);

		this.size = size;
		this.dim = dim;
		board = new int[dim][size][size];

		for (int d = 0; d < dim; d++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					board[d][i][j] = random.nextInt(100);
				}
			}
		}

	}

	/**
	 * Returns the costs for one field of a specified dimension.
	 * 
	 * @param d
	 *            the dimension
	 * @param i
	 *            the row
	 * @param j
	 *            the column
	 * @return the costs of this field in the dimension
	 */
	public int costs(int d, int i, int j) {
		return board[d][i][j];
	}

	/**
	 * Returns the size of the board.
	 * 
	 * @return the size of the board
	 */
	public int size() {
		return size;
	}

	/**
	 * The number of objective functions.
	 * 
	 * @return the number of objective functions
	 */
	public int dim() {
		return dim;
	}

}
