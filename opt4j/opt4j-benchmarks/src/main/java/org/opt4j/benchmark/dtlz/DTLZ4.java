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

package org.opt4j.benchmark.dtlz;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opt4j.benchmark.M;
import org.opt4j.benchmark.N;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link DTLZ4} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ4 extends DTLZ2 {

	protected final double alpha;

	/**
	 * Constructs a {@link DTLZ4} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 * @param alpha
	 *            the parameter alpha
	 */
	@Inject
	public DTLZ4(@M int m, @N int n, @Constant(value = "alpha", namespace = DTLZ4.class) double alpha) {
		super(m, n);
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.dtlz.DTLZEvaluator#f(java.util.List, double)
	 */
	@Override
	protected List<Double> f(List<Double> x, double g) {
		List<Double> f = new ArrayList<Double>();

		double a = (1.0 + g);

		for (int i = 0; i < m; i++) {
			if (i > 0) {
				a *= cos(PI * pow(x.get(i - 1), alpha) / 2);
			}
			double value = a;
			if (i < x.size()) {
				value *= sin(PI * pow(x.get(i), alpha) / 2);
			}
			f.add(value);
		}

		Collections.reverse(f);
		return f;
	}

}
