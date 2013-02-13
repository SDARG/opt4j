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
public class WFG9 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFG8} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFG9(@K int k, @M int M) {
		super(k, M);
	}

	protected static List<Double> t1(final List<Double> y) {
		final int n = y.size();

		List<Double> w = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			w.add(1.0);
		}

		List<Double> t = new ArrayList<Double>();

		for (int i = 0; i < n - 1; i++) {
			final List<Double> y_sub = y.subList(i + 1, n);
			final List<Double> w_sub = w.subList(i + 1, n);

			final double u = WFGTransFunctions.r_sum(y_sub, w_sub);

			t.add(WFGTransFunctions.b_param(y.get(i), u, 0.98 / 49.98, 0.02, 50));
		}

		t.add(y.get(n - 1));

		return t;
	}

	protected static List<Double> t2(final List<Double> y, final int k) {
		final int n = y.size();

		assert (k >= 1);
		assert (k < n);

		List<Double> t = new ArrayList<Double>();

		for (int i = 0; i < k; i++) {
			t.add(WFGTransFunctions.s_decept(y.get(i), 0.35, 0.001, 0.05));
		}

		for (int i = k; i < n; i++) {
			t.add(WFGTransFunctions.s_multi(y.get(i), 30, 95, 0.35));
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
		y = WFG9.t1(y);
		y = WFG9.t2(y, k);
		y = WFG6.t2(y, k, M);

		return WFG4.shape(y);
	}
}
