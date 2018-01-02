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
import org.opt4j.core.Objectives;
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
		List<Individual> all = new ArrayList<Individual>(population);
		List<Individual> parents = new ArrayList<Individual>();

		List<List<Individual>> fronts = fronts(all);
		Map<Individual, Integer> rank = getRank(fronts);
		Map<Individual, Double> distance = new HashMap<Individual, Double>();

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
						List<Individual> front = fronts.get(rank.get(winner));
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
		List<Individual> lames = new ArrayList<Individual>();

		List<List<Individual>> fronts = fronts(population);
		Collections.reverse(fronts);

		for (List<Individual> front : fronts) {
			if (lames.size() + front.size() < size) {
				lames.addAll(front);
			} else {
				final Map<Individual, Double> density = indicator.getDensityValues(front);
				Collections.sort(front, new Comparator<Individual>() {
					@Override
					public int compare(Individual o1, Individual o2) {
						return density.get(o1).compareTo(density.get(o2));
					}
				});
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
	protected Map<Individual, Integer> getRank(List<List<Individual>> fronts) {
		Map<Individual, Integer> ranks = new HashMap<Individual, Integer>();
		for (int i = 0; i < fronts.size(); i++) {
			for (Individual p : fronts.get(i)) {
				ranks.put(p, i);
			}
		}
		return ranks;
	}

	/**
	 * Evaluate the fronts and set the correspondent rank values.
	 * 
	 * @param individuals
	 *            the individuals
	 * @return the fronts
	 */
	public List<List<Individual>> fronts(Collection<Individual> individuals) {

		List<Individual> pop = new ArrayList<Individual>(individuals);
		Map<Individual, Integer> id = new HashMap<Individual, Integer>();
		for (int i = 0; i < pop.size(); i++) {
			id.put(pop.get(i), i);
		}

		List<List<Individual>> fronts = new ArrayList<List<Individual>>();

		Map<Individual, List<Individual>> S = new HashMap<Individual, List<Individual>>();
		int[] n = new int[pop.size()];

		for (Individual e : pop) {
			S.put(e, new ArrayList<Individual>());
			n[id.get(e)] = 0;
		}

		for (int i = 0; i < pop.size(); i++) {
			for (int j = i + 1; j < pop.size(); j++) {
				Individual p = pop.get(i);
				Individual q = pop.get(j);

				Objectives po = p.getObjectives();
				Objectives qo = q.getObjectives();

				if (po.dominates(qo)) {
					S.get(p).add(q);
					n[id.get(q)]++;
				} else if (qo.dominates(po)) {
					S.get(q).add(p);
					n[id.get(p)]++;
				}
			}
		}

		List<Individual> f1 = new ArrayList<Individual>();
		for (Individual i : pop) {
			if (n[id.get(i)] == 0) {
				f1.add(i);
			}
		}
		fronts.add(f1);
		List<Individual> fi = f1;
		while (!fi.isEmpty()) {
			List<Individual> h = new ArrayList<Individual>();
			for (Individual p : fi) {
				for (Individual q : S.get(p)) {
					n[id.get(q)]--;
					if (n[id.get(q)] == 0) {
						h.add(q);
					}
				}
			}
			fronts.add(h);
			fi = h;
		}
		return fronts;
	}

}
