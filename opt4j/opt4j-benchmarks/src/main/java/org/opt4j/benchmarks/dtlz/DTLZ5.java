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
 

package org.opt4j.benchmarks.dtlz;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opt4j.benchmarks.M;
import org.opt4j.benchmarks.N;

import com.google.inject.Inject;

/**
 * The {@link DTLZ5} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ5 extends DTLZ2 {

	/**
	 * Constructs a {@link DTLZ5} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	@Inject
	public DTLZ5(@M int m, @N int n) {
		super(m, n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.dtlz.DTLZEvaluator#f(java.util.List, double)
	 */
	@Override
	protected List<Double> f(List<Double> x, double g) {
		List<Double> f = new ArrayList<Double>();
		List<Double> theta = theta(x, g);

		double a = (1.0 + g);

		for (int i = 0; i < m; i++) {
			if (i > 0) {
				a *= cos(theta.get(i - 1));
			}
			double value = a;
			if (i < x.size()) {
				value *= sin(theta.get(i));
			}
			f.add(value);
		}

		Collections.reverse(f);
		return f;
	}

	/**
	 * The theta function.
	 * 
	 * @param x
	 *            the sublist
	 * @param g
	 *            the result of g(x)
	 * @return theta_i
	 */
	protected List<Double> theta(List<Double> x, double g) {
		List<Double> theta = new ArrayList<Double>();

		theta.add(x.get(0) * PI / 2);

		double t = PI / (4 * (1 + g));
		for (int i = 1; i < x.size(); i++) {
			theta.add(t * (1 + 2 * g * x.get(i)));
		}
		return theta;
	}
}
