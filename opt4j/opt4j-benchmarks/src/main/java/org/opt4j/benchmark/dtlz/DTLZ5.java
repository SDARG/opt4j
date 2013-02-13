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
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.opt4j.benchmark.M;
import org.opt4j.benchmark.N;

import com.google.inject.Inject;

/**
 * The {@link DTLZ5} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ5 extends DTLZ2 {

	/**
	 * Constructs a {@link DTLZ5} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	@Inject
	public DTLZ5(@M int m, @N int n) {
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
		List<Double> theta = theta(x, g);

		double a = (1.0 + g);

		for (int i = 0; i < m; i++) {
			if (i > 0) {
				a *= cos(theta.get(i - 1));
			}
			double value = a;
			if (i < x.size()) {
				value *= sin(theta.get(i));
			}
			f.add(value);
		}

		Collections.reverse(f);
		return f;
	}

	/**
	 * The theta function.
	 * 
	 * @param x
	 *            the sublist
	 * @param g
	 *            the result of g(x)
	 * @return theta_i
	 */
	protected List<Double> theta(List<Double> x, double g) {
		List<Double> theta = new ArrayList<Double>();

		theta.add(x.get(0) * PI / 2);

		double t = PI / (4 * (1 + g));
		for (int i = 1; i < x.size(); i++) {
			theta.add(t * (1 + 2 * g * x.get(i)));
		}
		return theta;
	}
}
