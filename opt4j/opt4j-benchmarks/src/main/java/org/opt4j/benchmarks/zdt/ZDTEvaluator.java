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
 

package org.opt4j.benchmarks.zdt;

import static org.opt4j.core.Objective.Sign.MIN;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

/**
 * Base class for the ZDT evaluators. ZDT problems have two objectives: f1 and
 * f2.
 * 
 * Base class for the double valued ZDT problem functions: {@link ZDT1},
 * {@link ZDT2}, {@link ZDT3}, {@link ZDT4}, {@link ZDT5}, {@link ZDT6}.
 * 
 * The ZDT are based on the functions {@code f1}, {@code f2}, {@code g}, and
 * {@code h}.
 * 
 * The ZDT problem is stated as following:
 * 
 * Minimize {@code (f1(x),f2(x))}
 * 
 * subject to {@code f2(x) = g(x2,...,xn)*h(f1(x1),g(x2,...,xn))} where
 * {@code x =(x1,...,xn)}
 * 
 * @author lukasiewycz
 * @param <A>
 *            the type of the phenotype
 * 
 */
abstract class ZDTEvaluator<A extends Object> implements Evaluator<A> {

	protected final Objective f1obj = new Objective("f1", MIN);
	protected final Objective f2obj = new Objective("f2", MIN);

	/**
	 * Function f1.
	 * 
	 * @param x
	 *            the phenotype
	 * @return f1(x)
	 */
	protected abstract double f1(A x);

	/**
	 * MinOnesResult of the g function times the result of the h function.
	 * 
	 * @param g
	 *            the result of the g function
	 * @param h
	 *            the result of the h function
	 * @return g*h
	 */
	protected double f2(double g, double h) {
		return g * h;
	}

	/**
	 * Function g.
	 * 
	 * @param x
	 *            the phenotype
	 * @return g(x)
	 */
	protected abstract double g(A x);

	/**
	 * Function h.
	 * 
	 * @param f1
	 *            the result of f1
	 * @param g
	 *            the result of g
	 * @return h(f1,g)
	 */
	protected abstract double h(double f1, double g);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(java.lang.Object)
	 */
	@Override
	public Objectives evaluate(A x) {
		final double f1 = f1(x);
		final double g = g(x);
		final double h = h(f1, g);
		final double f2 = f2(g, h);

		Objectives objectives = new Objectives();
		objectives.add(f1obj, f1);
		objectives.add(f2obj, f2);
		return objectives;
	}

}
