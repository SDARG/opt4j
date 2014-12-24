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
 * The {@link WFG6} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFG6 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFG6} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFG6(@K int k, @M int M) {
		super(k, M);
	}

	public static List<Double> t2(final List<Double> y, final int k, final int M) {
		final int n = y.size();

		assert (k >= 1);
		assert (k < n);
		assert (M >= 2);
		assert (k % (M - 1) == 0);

		List<Double> t = new ArrayList<Double>();

		for (int i = 1; i <= M - 1; i++) {
			final int head = (i - 1) * k / (M - 1);
			final int tail = i * k / (M - 1);

			final List<Double> y_sub = y.subList(head, tail);

			t.add(WFGTransFunctions.r_nonsep(y_sub, k / (M - 1)));
		}

		final List<Double> y_sub = y.subList(k, n);

		t.add(WFGTransFunctions.r_nonsep(y_sub, n - k));

		return t;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.wfg.WFGEvaluator#f(java.util.List)
	 */
	@Override
	public List<Double> f(List<Double> y) {
		y = WFG1.t1(y, k);
		y = WFG6.t2(y, k, M);

		return WFG4.shape(y);
	}
}
