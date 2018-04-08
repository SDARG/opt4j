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

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmarks.DoubleString;
import org.opt4j.benchmarks.K;
import org.opt4j.benchmarks.M;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

/**
 * The {@link WFGEvaluator}. All WFG test functions are derived from this class.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class WFGEvaluator implements Evaluator<DoubleString> {

	protected final int M;
	protected final int k;

	protected final List<Objective> keys = new ArrayList<Objective>();

	/**
	 * Constructs a {@link WFGEvaluator}.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFGEvaluator(@K int k, @M int M) {
		this.M = M;
		this.k = k;

		for (int i = 0; i < M; i++) {
			keys.add(new Objective("f" + (i + 1)));
		}
	}

	public abstract List<Double> f(List<Double> y);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(DoubleString phenotype) {
		List<Double> f = f(phenotype);

		assert (f.size() == keys.size());

		Objectives objectives = new Objectives();
		for (int i = 0; i < M; i++) {
			objectives.add(keys.get(i), f.get(i));
		}
		return objectives;
	}

	protected static List<Boolean> createA(final int M, final boolean degenerate) {
		assert (M >= 2);

		List<Boolean> A = new ArrayList<Boolean>();
		for (int i = 0; i < M - 1; i++) {
			A.add(!degenerate || i == 0);
		}
		return A;
	}

	protected static List<Double> calculateF(final List<Double> x, final List<Double> h) {

		assert (x.size() == h.size());

		final int M = h.size();

		List<Double> S = new ArrayList<Double>();
		for (int m = 1; m <= M; m++) {
			S.add(m * 2.0);
		}

		return calculateF(x, h, S);
	}

	protected static List<Double> normalizeZ(final List<Double> z, final List<Double> z_max) {
		final List<Double> result = new ArrayList<Double>();

		for (int i = 0; i < z.size(); i++) {
			assert (z.get(i) >= 0.0);
			assert (z.get(i) <= z_max.get(i));
			assert (z_max.get(i) > 0.0);

			result.add(z.get(i) / z_max.get(i));
		}

		return result;
	}

	protected static List<Double> calculateX(final List<Double> t_p, final List<Boolean> A) {
		final int size = t_p.size();

		assert (size != 0);
		assert (A.size() == t_p.size() - 1);

		final List<Double> result = new ArrayList<Double>();

		for (int i = 0; i < t_p.size() - 1; i++) {
			int A_i = A.get(i) ? 1 : 0;

			final double tmp1 = max(t_p.get(size - 1), A_i);
			result.add(tmp1 * (t_p.get(i) - 0.5) + 0.5);
		}

		result.add(t_p.get(size - 1));

		return result;
	}

	protected static List<Double> calculateF(final List<Double> x, final List<Double> h, final List<Double> S) {
		assert (x.size() == h.size());
		assert (h.size() == S.size());

		final List<Double> result = new ArrayList<Double>();

		for (int i = 0; i < h.size(); i++) {
			assert (S.get(i) > 0.0);

			result.add(x.get(x.size() - 1) + S.get(i) * h.get(i));
		}

		return result;
	}

}
