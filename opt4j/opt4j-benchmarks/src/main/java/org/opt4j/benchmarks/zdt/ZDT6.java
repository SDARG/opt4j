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
 

package org.opt4j.benchmarks.zdt;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import org.opt4j.benchmarks.DoubleString;

/**
 * Function ZDT 6.
 * 
 * @author lukasiewycz
 * 
 */
public class ZDT6 extends ZDT2 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDT1#f1(org.opt4j.benchmark.DoubleString)
	 */
	@Override
	protected double f1(DoubleString x) {
		double x0 = x.get(0);
		double f1 = 1 - exp(-4 * x0) * pow(sin(6 * PI * x0), 6);
		return f1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDT1#g(org.opt4j.benchmark.DoubleString)
	 */
	@Override
	protected double g(DoubleString x) {
		double sum = 0;
		for (int i = 1; i < x.size(); i++) {
			sum += x.get(i);
		}
		double g = 1 + 9 * pow(sum / (x.size() - 1), 0.25);
		return g;
	}

}
