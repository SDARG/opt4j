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
