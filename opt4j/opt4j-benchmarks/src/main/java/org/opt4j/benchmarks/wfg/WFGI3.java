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
 * The {@link WFGI3} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFGI3 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFGI3} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFGI3(@K int k, @M int M) {
		super(k, M);
	}

	public static List<Double> t1(final List<Double> y) {
		final int n = y.size();

		List<Double> w = new ArrayList<Double>();
		for (int i = 0; i < n; i++) {
			w.add(1.0);
		}

		List<Double> t = new ArrayList<Double>();
		t.add(y.get(0));

		for (int i = 1; i < n; i++) {

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
		y = WFGI3.t1(y);
		y = WFGI1.t2(y, k);
		y = WFGI1.t3(y, k, M);

		return WFGI1.shape(y);
	}
}
