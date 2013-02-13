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

package org.opt4j.benchmark.wfg;

import java.util.List;

import org.opt4j.benchmark.K;
import org.opt4j.benchmark.M;

import com.google.inject.Inject;

/**
 * The {@link WFGI4} benchmark function.
 * 
 * @author lukasiewycz
 * 
 */
public class WFGI4 extends WFGEvaluator {

	/**
	 * Constructs a {@link WFGI4} benchmark function.
	 * 
	 * @param k
	 *            the position parameters
	 * @param M
	 *            the number of objectives
	 */
	@Inject
	public WFGI4(@K int k, @M int M) {
		super(k, M);
	}

	public static List<Double> t3(final List<Double> y, final int k, final int M) {
		return WFG6.t2(y, k, M);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.wfg.WFGEvaluator#f(java.util.List)
	 */
	@Override
	public List<Double> f(List<Double> y) {
		y = WFGI1.t2(y, k);
		y = WFGI4.t3(y, k, M);

		return WFGI1.shape(y);
	}
}
