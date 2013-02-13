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
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.benchmark.M;
import org.opt4j.benchmark.N;

import com.google.inject.Inject;

/**
 * The {@link DTLZ7} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ7 extends DTLZEvaluator {

	/**
	 * Constructs a {@link DTLZ7} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	@Inject
	public DTLZ7(@M int m, @N int n) {
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

		for (int i = 0; i < m - 1; i++) {
			f.add(x.get(i));
		}
		f.add(h(f, g));

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
			g += e;

		}
		g *= 1.0 / x.size();
		g += 1;

		return g;
	}

	/**
	 * The h function
	 * 
	 * @param f
	 *            the f results
	 * @param g
	 *            the g result
	 * @return h(f,g)
	 */
	protected double h(List<Double> f, double g) {
		double h = 0;
		for (double e : f) {
			h -= ((e / (1 + g)) * (1 + sin(3 * PI * e)));
		}
		h += f.size() + 1;
		return h;
	}

}
