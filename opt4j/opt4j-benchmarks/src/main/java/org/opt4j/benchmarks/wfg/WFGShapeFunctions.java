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

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import java.util.List;

/**
 * The {@link WFGShapeFunctions}.
 * 
 * @author lukasiewycz
 * 
 */
class WFGShapeFunctions {

	public static double linear(List<Double> x, int m) {
		final int M = x.size();
		double result = 1.0;

		for (int i = 1; i <= M - m; i++) {
			result *= x.get(i - 1);
		}

		if (m != 1) {
			result *= 1 - x.get(M - m);
		}

		assert (result <= 1);
		assert (result >= 0);

		return result;
	}

	public static double convex(List<Double> x, int m) {
		final int M = x.size();
		double result = 1.0;

		for (int i = 1; i <= M - m; i++) {
			result *= 1.0 - cos(x.get(i - 1) * PI / 2.0);
		}

		if (m != 1) {
			result *= 1.0 - sin(x.get(M - m) * PI / 2.0);
		}

		assert (result <= 1);
		assert (result >= 0);

		return result;
	}

	public static double concave(List<Double> x, int m) {
		final int M = x.size();
		double result = 1.0;

		for (int i = 1; i <= M - m; i++) {
			result *= sin(x.get(i - 1) * PI / 2.0);
		}

		if (m != 1) {
			result *= cos(x.get(M - m) * PI / 2.0);
		}

		assert (result <= 1);
		assert (result >= 0);

		return result;
	}

	public static double mixed(List<Double> x, int A, double alpha) {

		assert (!x.isEmpty());
		assert (A >= 1);
		assert (alpha > 0.0);

		final double tmp = 2.0 * A * PI;
		double result = pow(1.0 - x.get(0) - cos(tmp * x.get(0) + PI / 2.0) / tmp, alpha);

		assert (result <= 1);
		assert (result >= 0);

		return result;
	}

	public static double disc(List<Double> x, int A, double alpha, double beta) {

		assert (!x.isEmpty());
		assert (A >= 1);
		assert (alpha > 0.0);
		assert (beta > 0.0);

		final double tmp1 = A * pow(x.get(0), beta) * PI;
		double result = 1.0 - pow(x.get(0), alpha) * pow(cos(tmp1), 2.0);

		assert (result <= 1);
		assert (result >= 0);

		return result;
	}
}
