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
 

package org.opt4j.operators.normalize;

import org.opt4j.core.genotype.DoubleGenotype;

/**
 * The {@link NormalizeDoubleWrap} normalizes the {@link DoubleGenotype} by
 * wrapping the values at the borders.
 * 
 * @author lukasiewycz
 * 
 */
public class NormalizeDoubleWrap extends NormalizeDoubleElementwise {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.normalize.NormalizeDoubleElementwise#normalize(double,
	 * double, double)
	 */
	@Override
	public double normalize(double value, double lb, double ub) {
		double diff = ub - lb;
		double result = value;
		if (result < lb) {
			double distance = lb - result;
			result += Math.ceil(distance / diff) * diff;
		}
		if (result > ub) {
			double distance = result - ub;
			result -= Math.ceil(distance / diff) * diff;
		}
		return result;
	}

}
