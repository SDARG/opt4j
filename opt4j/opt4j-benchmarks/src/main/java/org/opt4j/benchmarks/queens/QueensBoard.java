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

/**
 * A {@link QueensBoard} represents a chessboard with a given side length on
 * which with queens can be placed.
 * 
 * @author lukasiewycz
 * 
 */
public class QueensBoard {

	protected final boolean[][] board;

	protected final int size;

	private boolean feasible;

	/**
	 * Constructs a board with the side length {@code size}.
	 * 
	 * @param size
	 *            the size of the board
	 */
	public QueensBoard(int size) {
		this.size = size;
		board = new boolean[size][size];

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = false;
			}
		}

	}

	/**
	 * Adds or removes queen from a field.
	 * 
	 * @param i
	 *            the row
	 * @param j
	 *            the column
	 * @param value
	 *            {@code true} if a queen is on the field, else {@code false}
	 */
	public void setQueen(int i, int j, boolean value) {
		board[i][j] = value;
	}

	/**
	 * Returns {@code true} if a queen is on this field.
	 * 
	 * @param i
	 *            the row
	 * @param j
	 *            the column
	 * @return {@code true} if a queen is on the field, else {@code false}
	 */
	public boolean isQueen(int i, int j) {
		return board[i][j];
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
	 * The {@link QueensBoard} is feasible if no queen can attack another.
	 * 
	 * @return true if the queens board is feasible
	 */
	public boolean isFeasible() {
		return feasible;
	}

	/**
	 * Sets the feasibility of the {@link QueensBoard}.
	 * 
	 * @param feasible
	 *            true if the queens board is feasible, false otherwise
	 */
	public void setFeasible(boolean feasible) {
		this.feasible = feasible;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j]) {
					s += "[" + i + "," + j + "]";
				}
			}
		}
		return s;
	}
}
