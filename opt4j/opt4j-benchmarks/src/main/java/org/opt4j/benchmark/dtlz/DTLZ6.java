/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.benchmark.dtlz;

import static java.lang.Math.pow;

import java.util.List;

import org.opt4j.benchmark.M;
import org.opt4j.benchmark.N;

import com.google.inject.Inject;

/**
 * The {@link DTLZ6} function.
 * 
 * @author lukasiewycz
 * 
 */
public class DTLZ6 extends DTLZ5 {

	/**
	 * Constructs a {@link DTLZ6} function.
	 * 
	 * @param m
	 *            the number of objective functions.
	 * @param n
	 *            the size of the search space (length of the x vector).
	 */
	@Inject
	public DTLZ6(@M int m, @N int n) {
		super(m, n);
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
			g += pow(e, 0.1);

		}
		return g;
	}

}
