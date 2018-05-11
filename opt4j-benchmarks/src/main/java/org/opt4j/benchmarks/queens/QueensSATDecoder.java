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

import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.Genotype;
import org.opt4j.core.common.random.Rand;
import org.opt4j.satdecoding.AbstractSATDecoder;
import org.opt4j.satdecoding.Constraint;
import org.opt4j.satdecoding.Literal;
import org.opt4j.satdecoding.Model;
import org.opt4j.satdecoding.SATManager;

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

		constraints.addAll(createConstraintsRowsColumns());
		constraints.addAll(createConstraintsDiagonal());
		return constraints;
	}

	/**
	 * Helper function for createConstraints() that creates the constraints regarding rows and columns.
	 * 
	 * @return constraints regarding rows and columns
	 */
	protected Set<Constraint> createConstraintsRowsColumns() {
		int size = problem.size();
		Set<Constraint> constraints = new HashSet<Constraint>();

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

		return constraints;

	}

	/**
	 * Helper function for createConstraints() that creates the constraints regarding diagonals.
	 * 
	 * @return constraints regarding diagonals
	 */
	protected Set<Constraint> createConstraintsDiagonal() {
		int size = problem.size();
		Set<Constraint> constraints = new HashSet<Constraint>();

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
