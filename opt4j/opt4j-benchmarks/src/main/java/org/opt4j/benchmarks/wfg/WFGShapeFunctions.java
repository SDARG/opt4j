/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

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
