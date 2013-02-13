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
