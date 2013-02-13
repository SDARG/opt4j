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

import java.util.HashSet;
import java.util.Set;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Genotype;
import org.opt4j.sat.AbstractSATDecoder;
import org.opt4j.sat.Constraint;
import org.opt4j.sat.Literal;
import org.opt4j.sat.Model;
import org.opt4j.sat.SATManager;

import com.google.inject.Inject;

/**
 * A SAT decoder strategy. The decoder is based on the
 * {@link AbstractSATDecoder}.
 * 
 * @author lukasiewycz
 * 
 */
public class QueensSATDecoder extends AbstractSATDecoder<Genotype, QueensBoard> {

	protected final QueensProblem problem;

	/**
	 * Constructs a {@link QueensSATDecoder}.
	 * 
	 * @param satManager
	 *            the SATManager
	 * @param problem
	 *            the problem instance
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public QueensSATDecoder(SATManager satManager, QueensProblem problem, Rand random) {
		super(satManager, random);
		this.problem = problem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.AbstractSATDecoder#convertModel(org.opt4j.sat.Model)
	 */
	@Override
	public QueensBoard convertModel(Model model) {
		int size = problem.size();

		QueensBoard queensBoard = new QueensBoard(size);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				queensBoard.setQueen(i, j, model.get(i * size + j));
			}
		}

		return queensBoard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.AbstractSATDecoder#createConstraints()
	 */
	@Override
	public Set<Constraint> createConstraints() {
		Set<Constraint> constraints = new HashSet<Constraint>();
		int size = problem.size();

		for (int i = 0; i < size; i++) {

			Constraint constraint = new Constraint("=", 1);
			for (int j = 0; j < size; j++) {
				constraint.add(new Literal(i * size + j, true));
			}

			constraints.add(constraint);
		}

		for (int j = 0; j < size; j++) {
			Constraint constraint = new Constraint("=", 1);

			for (int i = 0; i < size; i++) {
				constraint.add(new Literal(i * size + j, true));
			}

			constraints.add(constraint);
		}

		for (int k = -size + 1; k < size; k++) {
			// diagonal 1
			Constraint constraint = new Constraint("<=", 1);
			for (int j = 0; j < size; j++) {
				int i = k + j;
				if (0 <= i && i < size) {
					constraint.add(new Literal(i * size + j, true));
				}
			}
			constraints.add(constraint);
		}

		for (int k = 0; k < 2 * size - 1; k++) {
			// diagonal 2
			Constraint constraint = new Constraint("<=", 1);
			for (int j = 0; j < size; j++) {
				int i = k - j;
				if (0 <= i && i < size) {
					constraint.add(new Literal(i * size + j, true));
				}
			}
			constraints.add(constraint);
		}

		return constraints;
	}
}
