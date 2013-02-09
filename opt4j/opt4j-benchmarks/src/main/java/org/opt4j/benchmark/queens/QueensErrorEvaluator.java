/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.benchmark.queens;

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
	 * Counts the number of errors for a given {@link QueensBoard}. That means the number of attacking queens.
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
