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

package org.opt4j.benchmark.queens;

import static org.opt4j.core.Objective.INFEASIBLE;
import static org.opt4j.core.Objective.Sign.MIN;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

/**
 * The evaluator for the {@link QueensProblem}.
 * 
 * @author lukasiewycz
 * 
 */
public class QueensEvaluator implements Evaluator<QueensBoard> {

	protected final QueensProblem problem;

	protected final List<Objective> objectives = new ArrayList<Objective>();

	/**
	 * Constructs the evaluator for the {@link QueensProblem}.
	 * 
	 * @param problem
	 *            the problem to set
	 * 
	 */
	@Inject
	public QueensEvaluator(QueensProblem problem) {
		this.problem = problem;
	}

	/**
	 * Initializes the {@link QueensEvaluator}.
	 */
	@Inject
	public void init() {
		for (int d = 0; d < problem.dim(); d++) {
			objectives.add(new Objective("sum" + d, MIN));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(QueensBoard queensBoard) {
		Objectives obj = new Objectives();
		for (int d = 0; d < problem.dim(); d++) {
			Objective objective = objectives.get(d);

			if (queensBoard.isFeasible()) {
				int sum = sum(queensBoard, d);
				obj.add(objective, sum);
			} else {
				obj.add(objective, INFEASIBLE);
			}
		}
		return obj;
	}

	/**
	 * Sums the costs of a {@link QueensBoard} in a specific dimension {@code d}
	 * .
	 * 
	 * @param board
	 *            the board
	 * @param d
	 *            the dimension
	 * @return the sum of the costs
	 */
	private int sum(QueensBoard board, int d) {
		int sum = 0;

		for (int i = 0; i < problem.size(); i++) {
			for (int j = 0; j < problem.size(); j++) {
				if (board.isQueen(i, j)) {
					sum += problem.costs(d, i, j);
				}
			}
		}

		return sum;
	}
}
