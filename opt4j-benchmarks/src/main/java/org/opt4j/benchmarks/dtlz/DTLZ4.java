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

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opt4j.benchmarks.M;
import org.opt4j.benchmarks.N;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link DTLZ4} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ4 extends DTLZ2 {

	protected final double alpha;

	/**
	 * Constructs a {@link DTLZ4} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 * @param alpha
	 *            the parameter alpha
	 */
	@Inject
	public DTLZ4(@M int m, @N int n, @Constant(value = "alpha", namespace = DTLZ4.class) double alpha) {
		super(m, n);
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.dtlz.DTLZEvaluator#f(java.util.List, double)
	 */
	@Override
	protected List<Double> f(List<Double> x, double g) {
		List<Double> f = new ArrayList<>();

		double a = (1.0 + g);

		for (int i = 0; i < m; i++) {
			if (i > 0) {
				a *= cos(PI * pow(x.get(i - 1), alpha) / 2);
			}
			double value = a;
			if (i < x.size()) {
				value *= sin(PI * pow(x.get(i), alpha) / 2);
			}
			f.add(value);
		}

		Collections.reverse(f);
		return f;
	}

}
