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
