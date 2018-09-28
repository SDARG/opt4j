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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

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

	protected final List<Objective> objectives = new ArrayList<>();

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
