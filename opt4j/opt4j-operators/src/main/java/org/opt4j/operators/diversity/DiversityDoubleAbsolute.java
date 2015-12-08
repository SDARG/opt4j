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
 

package org.opt4j.operators.diversity;

import org.opt4j.core.genotype.DoubleGenotype;

/**
 * The {@link DiversityDoubleAbsolute} calculates the {@link Diversity} as the
 * mean absolute difference of each entry in the double vector.
 * 
 * @author glass, lukasiewycz
 * 
 */
public class DiversityDoubleAbsolute implements DiversityDouble {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.diversity.Diversity#diversity(org.opt4j.core.problem
	 * .Genotype, org.opt4j.core.problem.Genotype)
	 */
	@Override
	public double diversity(DoubleGenotype a, DoubleGenotype b) {
		int size = a.size();

		double diversity = 0;
		for (int i = 0; i < size; i++) {
			double diff = a.getUpperBound(i) - a.getLowerBound(i);
			diversity += Math.abs(a.get(i) - b.get(i)) / diff;
		}

		return diversity / size;
	}

}
