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
 

package org.opt4j.operators.mutate;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.BooleanGenotype;

import com.google.inject.Inject;

/**
 * Mutate for the {@link BooleanGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateBoolean implements Mutate<BooleanGenotype> {

	protected final Random random;

	/**
	 * Constructs a new {@link MutateBoolean} with the given mutation rate.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public MutateBoolean(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(BooleanGenotype genotype, double p) {
		int size = genotype.size();

		for (int i = 0; i < size; i++) {
			if (random.nextDouble() < p) {
				genotype.set(i, !genotype.get(i));
			}
		}

	}

}
