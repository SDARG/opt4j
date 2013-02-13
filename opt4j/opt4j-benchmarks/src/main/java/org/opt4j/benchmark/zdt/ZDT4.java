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
import static java.lang.Math.cos;
import static java.lang.Math.pow;

import org.opt4j.benchmark.DoubleString;

/**
 * Function ZDT 4.
 * 
 * @author lukasiewycz
 * 
 */
public class ZDT4 extends ZDT1 {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDT1#g(org.opt4j.benchmark.DoubleString)
	 */
	@Override
	protected double g(DoubleString x) {
		double sum = 0;
		for (int i = 1; i < x.size(); i++) {
			sum += pow(x.get(i), 2) - 10 * cos(4 * PI * x.get(i));
		}
		double g = 1 + 10 * (x.size() - 1) + sum;
		return g;
	}

}
