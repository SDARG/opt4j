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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link Spea2}-Selector is a Java implementation of the <a
 * href="http://e-collection.ethbib.ethz.ch/ecol-pool/incoll/incoll_324.pdf"
 * >SPEA2-MOEA</a>, see "SPEA2: Improving the Strength Pareto Evolutionary
 * Algorithm For Multiobjective Optimization, Eckart Zitzler, Marco Laumanns,
 * and Lothar Thiele, In Evolutionary Methods for Design, Optimisation, and
 * Control, pages 19&ndash;26, 2002.".
 * </p>
 * <p>
 * The {@link Spea2}-Selector will not work properly if the {@link Objectives}
 * are not fixed, i.e., the {@link Objectives} of an {@link Individual} change
 * during the optimization process. This caused by the internal caching of
 * distances, etc. In this case, it is recommended to use the {@link Nsga2}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class Spea2 implements Selector {

	protected final Rand random;
	protected final int tournament;

	protected final Map<Individual, Spea2IndividualSet> map = new LinkedHashMap<Individual, Spea2IndividualSet>();
	protected final Set<Spea2IndividualSet> individualSets = new LinkedHashSet<Spea2IndividualSet>();

	protected final LinkedList<Integer> freeIDs = new LinkedList<Integer>();
	protected double[][] distance;
	protected boolean fitnessDirty = true;

	/**
	 * A wrapper for multiple equal (based on their objectives)
	 * {@link Individual}s.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	private static class Spea2IndividualSet extends LinkedHashSet<Individual> implements Comparable<Spea2IndividualSet> {
		private static final long serialVersionUID = 1L;
		protected final int id;
		protected int fitness;
		protected int strength;
		protected final Objectives objectives;
		protected double nextDistance = 0;

		Spea2IndividualSet(Individual individual, int id) {
			this.id = id;
			this.add(individual);
			this.objectives = individual.getObjectives();
			this.fitness = 0;
			this.strength = 0;
		}

		public Objectives getObjectives() {
			return objectives;
		}

		public int getFitness() {
			return fitness;
		}

		public void setFitness(int fitness) {
			this.fitness = fitness;
		}

		public int getStrength() {
			return strength;
		}

		public void setStrength(int strength) {
			this.strength = strength;
		}

		public Individual first() {
			assert (size() != 0);
			return iterator().next();
		}

		public int getId() {
			return id;
		}

		public double getNextDistance() {
			return nextDistance;
		}

		public void setNextDistance(double nextDistance) {
			this.nextDistance = nextDistance;
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public boolean equals(Object obj) {
			Spea2IndividualSet other = (Spea2IndividualSet) obj;
			return (id == other.id);
		}

		public boolean dominates(Spea2IndividualSet individualSet) {
			return this.getObjectives().dominates(individualSet.getObjectives());
		}

		@Override
		public int compareTo(Spea2IndividualSet o) {
			return o.fitness - this.fitness;
		}

	}

	/**
	 * Constructs a {@link Spea2}-{@link Selector}.
	 * 
	 * @param tournament
	 *            the number of individuals that fight against each other to
	 *            become a parent
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public Spea2(@Constant(value = "tournament", namespace = Spea2.class) int tournament, Rand random) {
		this.tournament = tournament;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#init(int)
	 */
	@Override
	public void init(int maxsize) {
		for (int i = 0; i < maxsize; i++) {
			freeIDs.add(i);
		}

		distance = new double[maxsize][maxsize];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getParents(int,
	 * java.util.Collection)
	 */
	@Override
	public Collection<Individual> getParents(int mu, Collection<Individual> population) {
		update(population);

		List<Individual> parents = new ArrayList<Individual>();

		List<Individual> candidates = new ArrayList<Individual>(population);
		int size = candidates.size();

		for (int i = 0; i < mu; i++) {
			Individual winner = candidates.get(random.nextInt(size));

			for (int j = 0; j < tournament; j++) {
				Individual opponent = candidates.get(random.nextInt(size));

				Spea2IndividualSet wWinner = map.get(winner);
				Spea2IndividualSet wOpponent = map.get(opponent);

				double oFitness = wWinner.getFitness();
				double wFitness = wOpponent.getFitness();

				if (oFitness > wFitness || (winner == opponent)) {
					winner = opponent;
				} else if (oFitness == wFitness) {

					double oDist = getMinDistance(wOpponent);
					double wDist = getMinDistance(wWinner);

					if (oDist > wDist) {
						winner = opponent;
					}
				}
			}
			parents.add(winner);
		}

		assert (parents.size() == mu);

		return parents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Selector#getLames(int, java.util.Collection)
	 */
	@Override
	public Collection<Individual> getLames(int lambda, Collection<Individual> population) {
		update(population);

		assert (lambda <= population.size());

		Set<Individual> lames = new LinkedHashSet<Individual>();

		if (lambda > 0) {
			List<Spea2IndividualSet> dominated = getDominated();

			if (countIndividuals(dominated) >= lambda) {

				Collections.sort(dominated);
				List<Individual> lameCandidates = new ArrayList<Individual>();

				int i = 0;
				while (lameCandidates.size() < lambda) {
					lameCandidates.addAll(dominated.get(i));
					i++;
				}
				lames.addAll(lameCandidates.subList(0, lambda));

			} else {

				for (Spea2IndividualSet w0 : dominated) {
					lames.addAll(w0);
				}

				for (Individual individual : getLamesFromNonDominated(lambda - lames.size())) {
					lames.add(individual);
				}
			}

		}

		assert (lames.size() == lambda);

		return lames;
	}

	/**
	 * Returns a specific number of lames from the non-dominated
	 * {@link Individual}s.
	 * 
	 * @param count
	 *            the specified number
	 * @return a collection of the lame individuals
	 */
	public Collection<Individual> getLamesFromNonDominated(int count) {
		Set<Individual> set = new LinkedHashSet<Individual>();

		while (set.size() < count) {
			int maxsize = 0;

			List<Spea2IndividualSet> candidates = new ArrayList<Spea2IndividualSet>();
			for (Spea2IndividualSet individualSet : getNonDominated()) {
				if (individualSet.size() > maxsize) {
					maxsize = individualSet.size();
					candidates.clear();
				}
				if (individualSet.size() == maxsize) {
					candidates.add(individualSet);
				}
			}

			if (candidates.size() <= (count - set.size())) {
				for (Spea2IndividualSet individualSet : candidates) {
					Individual individual = individualSet.first();
					remove(individual);
					set.add(individual);
				}
			} else { // candidates.size() > (count - set.size())
				for (Spea2IndividualSet individualSet : getNearest(count - set.size(), candidates)) {
					Individual individual = individualSet.first();
					remove(individual);
					set.add(individual);
				}

			}

		}

		assert (set.size() == count);

		return set;
	}

	/**
	 * Returns n with nearest neighbor based on distances.
	 * 
	 * @param n
	 *            the number of required IndividualSets
	 * @param candidates
	 *            the candidate IndividualSets
	 * @return the n nearest neighbors
	 */
	protected List<Spea2IndividualSet> getNearest(int n, Collection<Spea2IndividualSet> candidates) {

		assert (candidates.size() > n);

		List<Spea2IndividualSet> lames = new ArrayList<Spea2IndividualSet>();

		Map<Spea2IndividualSet, List<Spea2IndividualSet>> orderedLists = new LinkedHashMap<Spea2IndividualSet, List<Spea2IndividualSet>>();

		for (Spea2IndividualSet w0 : candidates) {
			List<Spea2IndividualSet> list = new ArrayList<Spea2IndividualSet>();
			for (Spea2IndividualSet w1 : candidates) {
				if (w0 != w1) {
					w1.setNextDistance(distance(w0, w1));
					list.add(w1);
				}
			}
			Collections.sort(list, new Comparator<Spea2IndividualSet>() {
				@Override
				public int compare(Spea2IndividualSet o1, Spea2IndividualSet o2) {
					double v = o1.getNextDistance() - o2.getNextDistance();
					if (v < 0) {
						return -1;
					} else if (v > 0) {
						return 1;
					} else {
						return 0;
					}
				}

			});
			orderedLists.put(w0, list);
		}

		while (lames.size() < n) {
			List<Spea2IndividualSet> lcandidates = new ArrayList<Spea2IndividualSet>(orderedLists.keySet());

			int size = lcandidates.size();

			for (int k = 0; k < size - 1; k++) {
				double min = Double.MAX_VALUE;

				for (Spea2IndividualSet candidate : lcandidates) {
					double value = distance(candidate, orderedLists.get(candidate).get(k));
					min = Math.min(min, value);
				}

				for (Iterator<Spea2IndividualSet> it = lcandidates.iterator(); it.hasNext();) {
					Spea2IndividualSet candidate = it.next();
					double value = distance(candidate, orderedLists.get(candidate).get(k));
					if (value > min) {
						it.remove();
					}
				}

				if (lcandidates.size() == 1) {
					break;
				}
			}

			Spea2IndividualSet lame = lcandidates.get(0);
			lames.add(lame);
			orderedLists.remove(lame);

			for (List<Spea2IndividualSet> list : orderedLists.values()) {
				list.remove(lame);
			}

		}

		assert (lames.size() == n);

		return lames;
	}

	protected double getMinDistance(Spea2IndividualSet w0) {
		double min = Double.MAX_VALUE;
		for (Spea2IndividualSet w1 : individualSets) {
			if (w0 != w1) {
				min = Math.min(min, distance(w0, w1));
			}
		}
		return min;
	}

	/**
	 * Update with current population.
	 * 
	 * @param population
	 *            the current population
	 */
	protected void update(Collection<Individual> population) {
		Set<Individual> popSet = new LinkedHashSet<Individual>(population);

		if (!popSet.equals(map.keySet())) {

			Set<Individual> adds = new LinkedHashSet<Individual>(popSet);
			adds.removeAll(map.keySet());

			Set<Individual> removes = new LinkedHashSet<Individual>(map.keySet());
			removes.removeAll(popSet);

			for (Individual individual : removes) {
				remove(individual);
			}

			for (Individual individual : adds) {
				add(individual);
			}
		}

		assert (population.size() == map.size());

		if (fitnessDirty) {
			calculateFitness();
			fitnessDirty = false;
		}

	}

	/**
	 * Return the distance of two {@code Spea2IndividualSet}s.
	 * 
	 * @param w0
	 *            first set
	 * @param w1
	 *            second set
	 * @return the distance
	 */
	protected double distance(Spea2IndividualSet w0, Spea2IndividualSet w1) {
		return distance[w0.getId()][w1.getId()];
	}

	/**
	 * Add a new {@link Individual}.
	 * 
	 * @param individual
	 *            the individual to add
	 */
	protected void add(Individual individual) {
		int id0 = freeIDs.removeFirst();
		Spea2IndividualSet w0 = new Spea2IndividualSet(individual, id0);

		Spea2IndividualSet eq = null;

		for (Spea2IndividualSet w1 : individualSets) {
			int id1 = w1.getId();
			double dist = calculateDistance(w0, w1);
			if (dist == 0.0) {
				eq = w1;
				break;
			}
			distance[id0][id1] = dist;
			distance[id1][id0] = dist;
		}
		if (eq != null) {
			freeIDs.add(id0);
			w0 = eq;
			w0.add(individual);
		} else {
			distance[id0][id0] = 0.0;
			individualSets.add(w0);
		}

		map.put(individual, w0);
		fitnessDirty = true;
	}

	/**
	 * Remove an {@link Individual}.
	 * 
	 * @param individual
	 *            the individual to remove
	 */
	protected void remove(Individual individual) {
		Spea2IndividualSet individualSet = map.remove(individual);

		if (individualSet.size() == 1) {
			individualSets.remove(individualSet);
			freeIDs.add(individualSet.getId());
		} else {
			individualSet.remove(individual);
		}

		fitnessDirty = true;
	}

	/**
	 * Calculate the distance between two {@code Spea2IndividualSet}s.
	 * 
	 * @param w0
	 *            the first set
	 * @param w1
	 *            the second set
	 * @return the distance
	 */
	protected double calculateDistance(Spea2IndividualSet w0, Spea2IndividualSet w1) {
		return w0.getObjectives().distance(w1.getObjectives());
	}

	/**
	 * Calculate the fitness.
	 */
	protected void calculateFitness() {
		for (Spea2IndividualSet individual : individualSets) {
			int s = 0;
			for (Spea2IndividualSet other : individualSets) {
				if (individual != other && individual.dominates(other)) {
					s += other.size();
				}
			}
			individual.setStrength(s);
		}

		for (Spea2IndividualSet individual : individualSets) {
			int f = 0;
			for (Spea2IndividualSet other : individualSets) {
				if (individual != other && other.dominates(individual)) {
					f += other.getStrength() * other.size();
				}
			}
			individual.setFitness(f);
		}
	}

	/**
	 * Returns all dominated {@code Spea2IndividualSet}s (fitness > 0).
	 * 
	 * @return all dominated IndividualSets
	 */
	protected List<Spea2IndividualSet> getDominated() {
		List<Spea2IndividualSet> dominated = new ArrayList<Spea2IndividualSet>();

		for (Spea2IndividualSet w0 : individualSets) {
			if (w0.getFitness() > 0.0) {
				dominated.add(w0);
			}
		}

		return dominated;
	}

	/**
	 * Returns all non-dominated {@code Spea2IndividualSet}s (fitness == 0).
	 * 
	 * @return all non-dominated individualSets
	 */
	protected List<Spea2IndividualSet> getNonDominated() {
		List<Spea2IndividualSet> dominated = new ArrayList<Spea2IndividualSet>();

		for (Spea2IndividualSet w0 : individualSets) {
			if (w0.getFitness() == 0.0) {
				dominated.add(w0);
			}
		}

		return dominated;
	}

	/**
	 * Returns the total number of {@link Individual}s in a collection of
	 * {@code Spea2IndividualSet}s.
	 * 
	 * @param collection
	 *            the collection of IndividualSets
	 * @return the total number of Individuals
	 */
	private int countIndividuals(Collection<Spea2IndividualSet> collection) {
		int c = 0;
		for (Spea2IndividualSet w : collection) {
			c += w.size();
		}
		return c;
	}

}
