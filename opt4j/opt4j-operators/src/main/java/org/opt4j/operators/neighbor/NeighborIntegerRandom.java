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
 

package org.opt4j.operators.neighbor;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.IntegerGenotype;

import com.google.inject.Inject;

/**
 * The {@link NeighborIntegerRandom} selects on element of an
 * {@link IntegerGenotype} and changes it. The neighbor is created randomly
 * between the lower and upper bounds.
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborIntegerRandom implements NeighborInteger {

	protected final Random random;

	/**
	 * Constructs a {@link NeighborIntegerRandom}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborIntegerRandom(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(IntegerGenotype genotype) {
		int size = genotype.size();

		final int i = random.nextInt(size);

		int value = genotype.get(i);
		int ub = genotype.getUpperBound(i);
		int lb = genotype.getLowerBound(i);
		int diff = ub - lb;

		if (diff > 0) {
			int r = (diff == 1) ? 0 : random.nextInt(diff);
			int n = r + lb;

			if (n >= value) {
				n++;
			}
			genotype.set(i, n);
		}

	}
}
