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

package org.opt4j.benchmarks.wfg;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmarks.K;
import org.opt4j.benchmarks.M;

import com.google.inject.Inject;

/**
 * The {@link WFGI1} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFGI1 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFGI1} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFGI1(@K int k, @M int M) {
		super(k, M);
	}

	protected static List<Double> t2(final List<Double> y, final int k) {
		return WFG1.t1(y, k);
	}

	protected static List<Double> t3(final List<Double> y, final int k, final int M) {
		return WFG2.t3(y, k, M);
	}

	protected static List<Double> shape(final List<Double> t_p) {

		assert (t_p.size() >= 2);

		final int M = t_p.size();

		final List<Boolean> A = createA(M, false);
		final List<Double> x = calculateX(t_p, A);

		List<Double> h = new ArrayList<>();

		for (int m = 1; m <= M; m++) {
			h.add(WFGShapeFunctions.concave(x, m));
		}

		List<Double> S = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			S.add(1.0);
		}

		return calculateF(x, h, S);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.wfg.WFGEvaluator#f(java.util.List)
	 */
	@Override
	public List<Double> f(List<Double> y) {
		y = WFGI1.t2(y, k);
		y = WFGI1.t3(y, k, M);

		return WFGI1.shape(y);
	}
}
