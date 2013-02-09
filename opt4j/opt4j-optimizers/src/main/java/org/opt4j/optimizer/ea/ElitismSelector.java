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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Individual;

import com.google.inject.Inject;

/**
 * The {@link ElitismSelector} is a single objective elitism select. If a
 * multi-objective problem is optimized, the objectives are summed up to a
 * single value.
 * 
 * @author lukasiewycz
 * 
 */
public class ElitismSelector implements Selector {

	protected final Random random;

	protected final Map<Individual, Double> fitness = new HashMap<Individual, Double>();

	/**
	 * Comparator that sorts the {@link Individual}s based on their fitness
	 * values.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class FitnessComparator implements Comparator<Individual> {
		@Override
		public int compare(Individual o1, Individual o2) {
			final double f1 = fitness.get(o1);
			final double f2 = fitness.get(o2);
			if (f1 < f2) {
				return -1;
			} else if (f2 < f1) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * Constructs an {@link ElitismSelector}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public ElitismSelector(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getLames(int, java.util.Collection)
	 */
	@Override
	public Collection<Individual> getLames(int lambda, Collection<Individual> population) {
		List<Individual> list = new ArrayList<Individual>(population);
		calculateFitness(list);
		Collections.sort(list, new FitnessComparator());
		Collections.reverse(list);

		List<Individual> lames = new ArrayList<Individual>();
		for (int i = 0; i < lambda; i++) {
			lames.add(list.get(i));
		}
		return lames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getParents(int,
	 * java.util.Collection)
	 */
	@Override
	public Collection<Individual> getParents(int mu, Collection<Individual> population) {
		List<Individual> parents = new ArrayList<Individual>();
		List<Individual> individuals = new ArrayList<Individual>(population);

		for (int i = 0; i < mu; i++) {
			int r = random.nextInt(individuals.size());
			parents.add(individuals.get(r));
		}
		return parents;
	}

	/**
	 * Calculates the fitness of the {@link Individual}s: the sum of all double
	 * values (these always have to be minimized) of the objectives.
	 * 
	 * @param population
	 */
	protected void calculateFitness(Collection<Individual> population) {
		fitness.clear();
		for (Individual individual : population) {
			double f = 0;
			for (double v : individual.getObjectives().array()) {
				f += v;
			}
			fitness.put(individual, f);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#init(int)
	 */
	@Override
	public void init(int maxsize) {
		// do nothing
	}

}
