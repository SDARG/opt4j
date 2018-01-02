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

import java.util.List;

import org.opt4j.benchmarks.BinaryString;

/**
 * Function ZDT 5.
 * 
 * @author lukasiewycz
 * 
 */
public class ZDT5 extends ZDTEvaluator<BinaryString> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#f1(org.opt4j.core.Phenotype)
	 */
	@Override
	protected double f1(BinaryString list) {
		double f1 = 1.0 + u(list, 0, 30);
		return f1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#g(org.opt4j.core.Phenotype)
	 */
	@Override
	protected double g(BinaryString list) {
		double sum = 0;
		int begin = 30;
		while (begin + 5 <= list.size()) {
			int end = begin + 5;
			sum += v(u(list, begin, end));
			begin = end;
		}
		return sum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#h(double, double)
	 */
	@Override
	protected double h(double f1, double g) {
		return 1.0 / f1;
	}

	/**
	 * Counts the number of {@code true}s in the array.
	 * 
	 * @param x
	 *            list of booleans
	 * @param begin
	 *            start of the array
	 * @param end
	 *            end of the array
	 * @return the number of {@code true}s in the array
	 */
	protected int u(List<Boolean> x, int begin, int end) {
		int u = 0;
		for (int i = begin; i < end; i++) {
			if (x.get(i)) {
				u++;
			}
		}
		return u;
	}

	/**
	 * Returns {@code 2+u} if {@code u<5} else {@code 1}.
	 * 
	 * @param u
	 *            the value {@code u <= 5}
	 * @return {@code 2+u} if {@code u<5} else {@code 1}
	 */
	protected int v(int u) {
		if (u < 5) {
			return 2 + u;
		} else if (u == 5) {
			return 1;
		}
		throw new IllegalArgumentException("Wrong argument u = " + u);
	}

}
