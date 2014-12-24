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
