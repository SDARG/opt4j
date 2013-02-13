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

import static java.lang.Math.sqrt;

import org.opt4j.benchmarks.DoubleString;

/**
 * Function ZDT 1.
 * 
 * @author lukasiewycz, helwig
 * 
 */
public class ZDT1 extends ZDTEvaluator<DoubleString> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#f1(org.opt4j.core.Phenotype)
	 */
	@Override
	protected double f1(DoubleString x) {
		return x.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#g(org.opt4j.core.Phenotype)
	 */
	@Override
	protected double g(DoubleString x) {
		double sum = 0;
		for (int i = 1; i < x.size(); i++) {
			sum += x.get(i);
		}
		double g = 1.0 + 9.0 * sum / (x.size() - 1);
		return g;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.zdt.ZDTEvaluator#h(double, double)
	 */
	@Override
	protected double h(double f1, double g) {
		double h = 1.0 - sqrt(f1 / g);
		return h;
	}

}
