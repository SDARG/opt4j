/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.benchmark.wfg;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmark.DoubleString;
import org.opt4j.benchmark.K;
import org.opt4j.benchmark.M;
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

		return caculateF(x, h, S);
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

	protected static List<Double> caculateF(final List<Double> x, final List<Double> h, final List<Double> S) {
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
