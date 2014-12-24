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

import static org.opt4j.core.Objective.Sign.MIN;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.Priority;

import com.google.inject.Inject;

/**
 * The evaluator for the {@link QueensProblem}.
 * 
 * @author lukasiewycz
 * 
 */
@Priority(-10)
public class QueensErrorEvaluator implements Evaluator<QueensBoard> {

	protected final QueensProblem problem;

	protected final Objective error = new Objective("error", MIN);

	/**
	 * Constructs the evaluator for the {@link QueensProblem}.
	 * 
	 * @param problem
	 *            the problem to set
	 * 
	 */
	@Inject
	public QueensErrorEvaluator(QueensProblem problem) {
		this.problem = problem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(QueensBoard queensBoard) {
		int errors = countErrors(queensBoard);

		Objectives objectives = new Objectives();
		objectives.add(error, errors);
		queensBoard.setFeasible(errors == 0);
		return objectives;
	}

	/**
	 * Counts the number of errors for a given {@link QueensBoard}. That means
	 * the number of attacking queens.
	 * 
	 * @param queensBoard
	 *            the board
	 * @return the error count
	 */
	private int countErrors(QueensBoard queensBoard) {
		int errorcount = 0;
		int size = problem.size();

		for (int i = 0; i < size; i++) {
			// horizontal
			int sum = 0;
			for (int j = 0; j < size; j++) {
				if (queensBoard.isQueen(i, j)) {
					sum++;
				}
			}
			sum = Math.abs(1 - sum);
			errorcount += sum;

			// vertikal
			sum = 0;
			for (int j = 0; j < size; j++) {
				if (queensBoard.isQueen(j, i)) {
					sum++;
				}
			}
			sum = Math.abs(1 - sum);
			errorcount += sum;
		}

		for (int k = -size + 1; k < size; k++) {
			// diagonal 1
			int sum = 0;
			for (int j = 0; j < size; j++) {
				int i = k + j;
				if (0 <= i && i < size && queensBoard.isQueen(j, i)) {
					sum++;
				}
			}
			sum = Math.max(0, sum - 1);
			errorcount += sum;
		}

		for (int k = 0; k < 2 * size - 1; k++) {
			// diagonal 2
			int sum = 0;
			for (int j = 0; j < size; j++) {
				int i = k - j;
				if (0 <= i && i < size && queensBoard.isQueen(j, i)) {
					sum++;
				}
			}
			sum = Math.max(0, sum - 1);
			errorcount += sum;
		}

		return errorcount;
	}

}
