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
 * The {@link WFG4} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFG5 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFG4} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFG5(@K int k, @M int M) {
		super(k, M);
	}

	protected static List<Double> t1(final List<Double> y) {
		final int n = y.size();

		List<Double> t = new ArrayList<Double>();

		for (int i = 0; i < n; i++) {
			t.add(WFGTransFunctions.s_decept(y.get(i), 0.35, 0.001, 0.05));
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
		y = WFG5.t1(y);
		y = WFG2.t3(y, k, M);

		return WFG4.shape(y);
	}
}
