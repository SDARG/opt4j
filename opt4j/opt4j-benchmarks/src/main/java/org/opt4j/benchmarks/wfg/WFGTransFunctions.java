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
import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.min;
import static java.lang.Math.pow;

import java.util.List;

/**
 * The {@link WFGTransFunctions}.
 * 
 * @author lukasiewycz
 * 
 */
class WFGTransFunctions {

	public static double corretToZeroOne(final double a) {
		double epsilon = 1.0e-10;
		final double WFG_min = 0.0;
		final double WFG_max = 1.0;

		final double min_epsilon = WFG_min - epsilon;
		final double max_epsilon = WFG_max + epsilon;

		if (a <= WFG_min && a >= min_epsilon) {
			return WFG_min;
		} else if (a >= WFG_max && a <= max_epsilon) {
			return WFG_max;
		} else {
			return a;
		}

	}

	public static double b_poly(final double y, final double alpha) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (alpha > 0.0);
		assert (alpha != 1.0);

		double result = pow(y, alpha);

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double b_flat(final double y, final double A, final double B, final double C) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (A >= 0.0);
		assert (A <= 1.0);
		assert (B >= 0.0);
		assert (B <= 1.0);
		assert (C >= 0.0);
		assert (C <= 1.0);
		assert (B < C);
		assert (B != 0.0 || A == 0.0);
		assert (B != 0.0 || C != 1.0);
		assert (C != 1.0 || A == 1.0);
		assert (C != 1.0 || B != 0.0);

		final double tmp1 = min(0.0, floor(y - B)) * A * (B - y) / B;
		final double tmp2 = min(0.0, floor(C - y)) * (1.0 - A) * (y - C) / (1.0 - C);

		double result = A + tmp1 - tmp2;

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double b_param(final double y, final double u, final double A, final double B, final double C) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (u >= 0.0);
		assert (u <= 1.0);
		assert (A > 0.0);
		assert (A < 1.0);
		assert (B > 0.0);
		assert (B < C);

		final double v = A - (1.0 - 2.0 * u) * abs(floor(0.5 - u) + A);

		double result = pow(y, B + (C - B) * v);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		result = corretToZeroOne(result);

		return result;
	}

	public static double s_linear(final double y, final double A) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (A > 0.0);
		assert (A < 1.0);

		double result = abs(y - A) / abs(floor(A - y) + A);

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double s_decept(final double y, final double A, final double B, final double C) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (A > 0.0);
		assert (A < 1.0);
		assert (B > 0.0);
		assert (B < 1.0);
		assert (C > 0.0);
		assert (C < 1.0);
		assert (A - B > 0.0);
		assert (A + B < 1.0);

		final double tmp1 = floor(y - A + B) * (1.0 - C + (A - B) / B) / (A - B);
		final double tmp2 = floor(A + B - y) * (1.0 - C + (1.0 - A - B) / B) / (1.0 - A - B);

		double result = 1.0 + (abs(y - A) - B) * (tmp1 + tmp2 + 1.0 / B);

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double s_multi(final double y, final double A, final double B, final double C) {
		assert (y >= 0.0);
		assert (y <= 1.0);
		assert (A >= 1);
		assert (B >= 0.0);
		assert ((4.0 * A + 2.0) * PI >= 4.0 * B);
		assert (C > 0.0);
		assert (C < 1.0);

		final double tmp1 = abs(y - C) / (2.0 * (floor(C - y) + C));
		final double tmp2 = (4.0 * A + 2.0) * PI * (0.5 - tmp1);

		double result = (1.0 + cos(tmp2) + 4.0 * B * pow(tmp1, 2.0)) / (B + 2.0);

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double r_sum(final List<Double> y, final List<Double> w) {
		assert (!y.isEmpty());
		assert (w.size() == y.size());

		double numerator = 0.0;
		double denominator = 0.0;

		for (int i = 0; i < y.size(); i++) {
			assert (w.get(i) > 0.0);

			numerator += w.get(i) * y.get(i);
			denominator += w.get(i);
		}

		double result = numerator / denominator;

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

	public static double r_nonsep(final List<Double> y, final int A) {
		final int y_len = y.size();

		assert (y_len != 0);
		assert (A >= 1);
		assert (A <= y_len);
		assert (y.size() % A == 0);

		double numerator = 0.0;

		for (int j = 0; j < y_len; j++) {
			numerator += y.get(j);

			for (int k = 0; k <= A - 2; k++) {
				numerator += abs(y.get(j) - y.get((j + k + 1) % y_len));
			}
		}

		final double tmp = ceil(A / 2.0);
		final double denominator = y_len * tmp * (1.0 + 2.0 * A - 2.0 * tmp) / A;

		double result = numerator / denominator;

		result = corretToZeroOne(result);

		assert (result >= 0.0) : result;
		assert (result <= 1.0) : result;

		return result;
	}

}
