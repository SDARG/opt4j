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
 

package org.opt4j.benchmarks.wfg;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmarks.K;
import org.opt4j.benchmarks.M;

import com.google.inject.Inject;

/**
 * The {@link WFG8} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFG8 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFG8} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFG8(@K int k, @M int M) {
		super(k, M);
	}

	protected static List<Double> t1(final List<Double> y, final int k) {
		final int n = y.size();

		assert (k >= 1);
		assert (k < n);

		List<Double> w = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			w.add(1.0);
		}

		List<Double> t = new ArrayList<Double>();

		for (int i = 0; i < k; i++) {
			t.add(y.get(i));
		}

		for (int i = k; i < n; i++) {
			final List<Double> y_sub = y.subList(0, i);
			final List<Double> w_sub = w.subList(0, i);

			final double u = WFGTransFunctions.r_sum(y_sub, w_sub);

			t.add(WFGTransFunctions.b_param(y.get(i), u, 0.98 / 49.98, 0.02, 50));
		}

		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.wfg.WFGEvaluator#f(java.util.List)
	 */
	@Override
	public List<Double> f(List<Double> y) {
		y = WFG8.t1(y, k);
		y = WFG1.t1(y, k);
		y = WFG2.t3(y, k, M);

		return WFG4.shape(y);
	}
}
