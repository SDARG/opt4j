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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.common.random.Rand;
import org.opt4j.operators.crossover.Pair;

import com.google.inject.Inject;

/**
 * The {@link CouplerUnique} uses the set of parents and creates couples
 * randomly from this set. Parent pairs never contain equal {@link Individual}s.
 * 
 * @author lukasiewycz
 * 
 */
public class CouplerUnique implements Coupler {

	protected final Random random;

	/**
	 * Constructs a {@link CouplerRandom}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CouplerUnique(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mating.Coupler#getCouples(int, java.util.List)
	 */
	@Override
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> parents) {
		Collection<Pair<Individual>> couples = new ArrayList<>();

		Set<Individual> set = new HashSet<>(parents);
		List<Individual> p = new ArrayList<>(set);

		int n = p.size();

		for (int i = 0; i < size; i++) {
			Individual first;
			Individual second;
			if (n == 1) {
				first = p.get(0);
				second = first;
			} else {
				int x = random.nextInt(n);
				int y = (x + random.nextInt(n - 1) + 1) % n;
				first = p.get(x);
				second = p.get(y);
				assert (first != second) : x + "==" + y;
			}
			Pair<Individual> pair = new Pair<>(first, second);
			couples.add(pair);
		}
		return couples;
	}

}
