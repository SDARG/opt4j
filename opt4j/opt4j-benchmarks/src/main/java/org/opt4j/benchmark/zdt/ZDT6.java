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

package org.opt4j.benchmark.zdt;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.pow;
import static java.lang.Math.sin;

import org.opt4j.benchmark.DoubleString;

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
