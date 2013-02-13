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

package org.opt4j.benchmarks.dtlz;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opt4j.benchmarks.M;
import org.opt4j.benchmarks.N;

import com.google.inject.Inject;

/**
 * The {@link DTLZ2} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ2 extends DTLZEvaluator {

	/**
	 * Constructs a {@link DTLZ2} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	@Inject
	public DTLZ2(@M int m, @N int n) {
		super(m, n);
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
				a *= cos(PI * x.get(i - 1) / 2);
			}
			double value = a;
			if (i < x.size()) {
				value *= sin(PI * x.get(i) / 2);
			}
			f.add(value);
		}

		Collections.reverse(f);
		return f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.dtlz.DTLZEvaluator#g(java.util.List)
	 */
	@Override
	protected double g(List<Double> x) {
		double g = 0;
		for (double e : x) {
			g += (e - 0.5) * (e - 0.5);
		}
		return g;
	}

}
