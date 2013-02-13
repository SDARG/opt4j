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

		List<Double> h = new ArrayList<Double>();

		for (int m = 1; m <= M; m++) {
			h.add(WFGShapeFunctions.concave(x, m));
		}

		List<Double> S = new ArrayList<Double>();
		for (int i = 0; i < M; i++) {
			S.add(1.0);
		}

		return caculateF(x, h, S);
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
