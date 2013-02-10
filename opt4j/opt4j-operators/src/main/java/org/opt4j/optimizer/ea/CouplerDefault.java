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
import java.util.List;

import org.opt4j.core.Individual;
import org.opt4j.operator.crossover.Pair;

/**
 * The {@link CouplerDefault} uses the pairs based on their index in the list:
 * {@code p0+p1,p2+p3,etc.}.
 * 
 * @author glass, lukasiewycz
 * 
 */
public class CouplerDefault implements Coupler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mating.Coupler#getCouples(int, java.util.List)
	 */
	@Override
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> parents) {
		List<Pair<Individual>> couples = new ArrayList<Pair<Individual>>();
		for (int i = 0; i < size; i++) {
			Individual first = parents.get((2 * i) % parents.size());
			Individual second = parents.get((2 * i + 1) % parents.size());
			Pair<Individual> pair = new Pair<Individual>(first, second);
			couples.add(pair);
		}
		return couples;
	}

}
