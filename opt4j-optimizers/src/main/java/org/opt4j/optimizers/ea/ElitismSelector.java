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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.opt4j.core.Individual;
import org.opt4j.core.common.random.Rand;

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

	protected final Map<Individual, Double> fitness = new HashMap<>();

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
		List<Individual> list = new ArrayList<>(population);
		calculateFitness(list);
		Collections.sort(list, new FitnessComparator());
		Collections.reverse(list);

		List<Individual> lames = new ArrayList<>();
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
		List<Individual> parents = new ArrayList<>();
		List<Individual> individuals = new ArrayList<>(population);

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
	 * @param individuals
	 *            the individuals to process
	 */
	protected void calculateFitness(Collection<Individual> individuals) {
		fitness.clear();
		for (Individual individual : individuals) {
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
