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
