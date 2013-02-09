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
package org.opt4j.common.archive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opt4j.core.Individual;

/**
 * The {@link Crowding} calculates the crowding distance of {@link Individual}s
 * as used for instance in the the {@code Nsga2} algorithm. A bounding box
 * around each individual is defined and restricted by the neighboring
 * individuals in each dimension. The crowding distance is the sum of the
 * side-lengths of the bounding box. If an individual does not have neighbors on
 * all sides, the crowding distance is maximal. A normalization in each
 * dimension is applied.
 * 
 * @author lukasiewycz
 * 
 * @see "A fast and elitist multiobjective genetic algorithm : NSGA-II, K. Deb,
 *      A. Pratap, S. Agarwal, and T. Meyarivan Evolutionary Computation,
 *      IEEETransactions on, vol. 6, no. 2, pp. 182-197, August 2002."
 * 
 */
public class Crowding implements FrontDensityIndicator {

	/**
	 * Calculates the crowding distance for a collection of {@link Individual}s.
	 * 
	 * @param individuals
	 *            the individuals
	 * @return a map of the individuals to the corresponding crowding distance
	 */
	private Map<Individual, Double> getValues(Collection<Individual> individuals) {
		Map<Individual, Double> map = new HashMap<Individual, Double>();
		List<Individual> list = new ArrayList<Individual>(individuals);

		for (Individual individual : list) {
			map.put(individual, 0.0);
		}

		int m = list.get(0).getObjectives().array().length;

		for (int i = 0; i < m; i++) {
			final int dim = i;
			Collections.sort(list, new Comparator<Individual>() {
				@Override
				public int compare(Individual o1, Individual o2) {
					double i1 = o1.getObjectives().array()[dim];
					double i2 = o2.getObjectives().array()[dim];
					return (int) Math.signum(i1 - i2);
				}
			});

			Individual minIndividual = list.get(0);
			Individual maxIndividual = list.get(list.size() - 1);

			double diff = maxIndividual.getObjectives().array()[dim] - minIndividual.getObjectives().array()[dim];
			if (diff > 0) {
				map.put(minIndividual, Double.MAX_VALUE);
				map.put(maxIndividual, Double.MAX_VALUE);

				for (int j = 1; j < list.size() - 1; j++) {
					double p = list.get(j - 1).getObjectives().array()[dim];
					double n = list.get(j + 1).getObjectives().array()[dim];
					Individual ind = list.get(j);
					map.put(ind, map.get(ind) + ((n - p) / diff));
				}
			}
		}

		return map;
	}

	/**
	 * Returns an ordered list of the {@link Individual}s corresponding to their
	 * crowding distance. {@link Individual}s with a high crowding distance are
	 * sorted to the front.
	 * 
	 * @param values
	 *            the map of individuals to their crowding distance values
	 * @return the sorted list of individuals based on their crowding distance
	 */
	public List<Individual> order(final Map<Individual, Double> values) {
		List<Individual> list = new ArrayList<Individual>(values.keySet());
		Collections.sort(list, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				return values.get(o2).compareTo(values.get(o1));
			}
		});
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.optimizer.ea.FrontDensityIndicator#getDensityValues(java.util
	 * .Collection)
	 */
	@Override
	public Map<Individual, Double> getDensityValues(Collection<Individual> individuals) {
		return getValues(individuals);
	}

}