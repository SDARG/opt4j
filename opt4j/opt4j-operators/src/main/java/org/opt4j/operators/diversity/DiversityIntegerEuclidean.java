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

import org.opt4j.core.genotype.IntegerGenotype;

/**
 * The {@link DiversityIntegerEuclidean} is an implementation of the
 * {@link DiversityInteger} that calculates the diversity of two
 * {@link IntegerGenotype} objects by normalizing the values to {@code [0,1]}
 * and calculating the Euclidean distance.
 * 
 * @author lukasiewycz
 * 
 */
public class DiversityIntegerEuclidean implements DiversityInteger {

	@Override
	public double diversity(IntegerGenotype a, IntegerGenotype b) {

		double diversity = 0;
		int size = a.size();
		for (int i = 0; i < size; i++) {
			double diff = a.getUpperBound(i) * a.getLowerBound(i);
			double dist = ((double) a.get(i) - (double) b.get(i)) / diff;
			diversity += dist * dist;
		}

		return Math.sqrt(diversity) / Math.sqrt(size);
	}

}
