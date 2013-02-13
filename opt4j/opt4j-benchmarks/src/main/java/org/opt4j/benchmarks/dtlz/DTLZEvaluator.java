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

package org.opt4j.benchmarks.dtlz;

import static org.opt4j.core.Objective.Sign.MIN;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

/**
 * The {@link DTLZEvaluator}
 * 
 * @author lukasiewycz
 * 
 */
abstract class DTLZEvaluator implements Evaluator<DoubleString> {

	protected final List<Objective> objectives = new ArrayList<Objective>();

	protected final int m;

	protected final int n;

	/**
	 * Constructs a {@link DTLZEvaluator}.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	public DTLZEvaluator(int m, int n) {
		super();
		this.m = m;
		this.n = n;

		for (int i = 0; i < m; i++) {
			objectives.add(new Objective("f" + (i + 1), MIN));
		}
	}

	/**
	 * Function f.
	 * 
	 * @param x
	 *            string of double values
	 * @param g
	 *            result of g(x)
	 * @return f(x)
	 */
	protected abstract List<Double> f(List<Double> x, double g);

	/**
	 * Function g.
	 * 
	 * @param x
	 *            string of double values
	 * @return g(x)
	 */
	protected abstract double g(List<Double> x);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(DoubleString x) {
		final double g = g(x.subList(m - 1, n));
		final List<Double> f = f(x.subList(0, m - 1), g);

		Objectives obj = new Objectives();
		for (int i = 0; i < objectives.size(); i++) {
			Objective objective = objectives.get(i);
			obj.add(objective, f.get(i));
		}
		return obj;
	}

}
