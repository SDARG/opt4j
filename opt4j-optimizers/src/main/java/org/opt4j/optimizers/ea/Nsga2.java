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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.opt4j.core.Individual;
import org.opt4j.core.common.archive.FrontDensityIndicator;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link Nsga2} {@link Selector}, see "A Fast Elitist Non-Dominated Sorting
 * Genetic Algorithm for Multi-Objective Optimization: NSGA-II, K. Deb, Samir
 * Agrawal, Amrit Pratap, and T. Meyarivan, Parallel MockProblem Solving from
 * Nature, 2000".
 * 
 * @see Nsga2Module
 * @author lukasiewycz, noorshams
 * 
 */
public class Nsga2 implements Selector {

	protected final Random random;
	protected final int tournament;
	protected final FrontDensityIndicator indicator;

	/**
	 * Constructs a {@link Nsga2} {@link Selector}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param tournament
	 *            the tournament value
	 */
	@Inject
	public Nsga2(Rand random, @Constant(value = "tournament", namespace = Nsga2.class) int tournament,
			FrontDensityIndicator indicator) {
		this.random = random;
		this.tournament = tournament;
		this.indicator = indicator;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getParents(int,
	 * java.util.Collection)
	 */
	@Override
	public Collection<Individual> getParents(int mu, Collection<Individual> population) {
		List<Individual> all = new ArrayList<>(population);
		List<Individual> parents = new ArrayList<>();

		NonDominatedFronts fronts = new NonDominatedFronts(all);
		Map<Individual, Integer> rank = getRank(fronts);
		Map<Individual, Double> distance = new HashMap<>();

		final int size = all.size();

		for (int i = 0; i < mu; i++) {
			Individual winner = all.get(random.nextInt(size));

			for (int t = 0; t < tournament; t++) {
				Individual opponent = all.get(random.nextInt(size));
				if (rank.get(opponent) < rank.get(winner) || opponent == winner) {
					winner = opponent;
				} else if (rank.get(opponent) == rank.get(winner)) {
					// The winner is determined considering the crowding
					// distance

					if (!distance.containsKey(winner)) {
						List<Individual> front = new ArrayList<>(fronts.getFrontAtIndex(rank.get(winner)));
						distance.putAll(indicator.getDensityValues(front));
					}

					// Opponent wins, if it has a better crowding distance
					if (distance.get(opponent) > distance.get(winner)) {
						winner = opponent;
					}

				}
			}

			parents.add(winner);
		}

		return parents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getLames(int, java.util.Collection)
	 */
	@Override
	public Collection<Individual> getLames(int size, Collection<Individual> population) {
		List<Individual> lames = new ArrayList<>();

		NonDominatedFronts fronts = new NonDominatedFronts(population);
		for (int i = fronts.getFrontNumber() - 1; i >= 0; i--) {
			List<Individual> front = new ArrayList<>(fronts.getFrontAtIndex(i));
			if (lames.size() + front.size() < size) {
				lames.addAll(front);
			} else {
				final Map<Individual, Double> density = indicator.getDensityValues(front);
				Collections.sort(front, (Individual o1, Individual o2) -> density.get(o1).compareTo(density.get(o2)));
				lames.addAll(front.subList(0, size - lames.size()));
			}
		}
		return lames;
	}

	/**
	 * Determine the ranks of fronts.
	 * 
	 * @param fronts
	 *            the fronts
	 * @return the ranks
	 */
	protected Map<Individual, Integer> getRank(NonDominatedFronts fronts) {
		Map<Individual, Integer> ranks = new HashMap<>();
		for (int i = 0; i < fronts.getFrontNumber(); i++) {
			for (Individual p : fronts.getFrontAtIndex(i)) {
				ranks.put(p, i);
			}
		}
		return ranks;
	}
}
