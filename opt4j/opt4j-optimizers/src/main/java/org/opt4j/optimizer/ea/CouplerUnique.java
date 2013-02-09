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

package org.opt4j.optimizer.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Individual;
import org.opt4j.operator.crossover.Pair;

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
		Collection<Pair<Individual>> couples = new ArrayList<Pair<Individual>>();

		Set<Individual> set = new HashSet<Individual>(parents);
		List<Individual> p = new ArrayList<Individual>(set);

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
			Pair<Individual> pair = new Pair<Individual>(first, second);
			couples.add(pair);
		}
		return couples;
	}

}
