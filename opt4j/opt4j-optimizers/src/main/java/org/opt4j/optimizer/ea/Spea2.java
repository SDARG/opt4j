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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link Spea2}-Selector is a Java implementation of the <a
 * href="http://e-collection.ethbib.ethz.ch/ecol-pool/incoll/incoll_324.pdf"
 * >SPEA2-MOEA</a>.
 * </p>
 * <p>
 * The {@link Spea2}-Selector will not work properly if the {@link Objectives}
 * are not fixed, i.e., the {@link Objectives} of an {@link Individual} change
 * during the optimization process. This caused by the internal caching of
 * distances, etc. In this case, it is recommended to use the {@link Nsga2}.
 * </p>
 * 
 * @see "SPEA2: Improving the Strength Pareto Evolutionary Algorithm For
 *      Multiobjective Optimization, Eckart Zitzler, Marco Laumanns, and Lothar
 *      Thiele, In Evolutionary Methods for Design, Optimisation, and Control,
 *      pages 19&ndash;26, 2002."
 * @author lukasiewycz
 * 
 */
public class Spea2 implements Selector {

	protected final Rand random;
	protected final int tournament;

	protected final Map<Individual, IndividualSet> map = new LinkedHashMap<Individual, IndividualSet>();
	protected final Set<IndividualSet> individualSets = new LinkedHashSet<IndividualSet>();

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
	private class IndividualSet extends LinkedHashSet<Individual> implements Comparable<IndividualSet> {

		private static final long serialVersionUID = 1L;
		protected final int id;
		protected int fitness;
		protected int strength;
		protected final Objectives objectives;
		protected double nextDistance = 0;

		IndividualSet(Individual individual, int id) {
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
			IndividualSet other = (IndividualSet) obj;
			return (id == other.id);
		}

		public boolean dominates(IndividualSet individualSet) {
			return this.getObjectives().dominates(individualSet.getObjectives());
		}

		@Override
		public int compareTo(IndividualSet o) {
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

				IndividualSet wWinner = map.get(winner);
				IndividualSet wOpponent = map.get(opponent);

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
			List<IndividualSet> dominated = getDominated();

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

				for (IndividualSet w0 : dominated) {
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

			List<IndividualSet> candidates = new ArrayList<IndividualSet>();
			for (IndividualSet individualSet : getNonDominated()) {
				if (individualSet.size() > maxsize) {
					maxsize = individualSet.size();
					candidates.clear();
				}
				if (individualSet.size() == maxsize) {
					candidates.add(individualSet);
				}
			}

			if (candidates.size() <= (count - set.size())) {
				for (IndividualSet individualSet : candidates) {
					Individual individual = individualSet.first();
					remove(individual);
					set.add(individual);
				}
			} else { // candidates.size() > (count - set.size())
				for (IndividualSet individualSet : getNearest(count - set.size(), candidates)) {
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
	protected List<IndividualSet> getNearest(int n, Collection<IndividualSet> candidates) {

		assert (candidates.size() > n);

		List<IndividualSet> lames = new ArrayList<IndividualSet>();

		Map<IndividualSet, List<IndividualSet>> orderedLists = new LinkedHashMap<IndividualSet, List<IndividualSet>>();

		for (IndividualSet w0 : candidates) {
			List<IndividualSet> list = new ArrayList<IndividualSet>();
			for (IndividualSet w1 : candidates) {
				if (w0 != w1) {
					w1.setNextDistance(distance(w0, w1));
					list.add(w1);
				}
			}
			Collections.sort(list, new Comparator<IndividualSet>() {
				@Override
				public int compare(IndividualSet o1, IndividualSet o2) {
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
			List<IndividualSet> lcandidates = new ArrayList<IndividualSet>(orderedLists.keySet());

			int size = lcandidates.size();

			for (int k = 0; k < size - 1; k++) {
				double min = Double.MAX_VALUE;

				for (IndividualSet candidate : lcandidates) {
					double value = distance(candidate, orderedLists.get(candidate).get(k));
					min = Math.min(min, value);
				}

				for (Iterator<IndividualSet> it = lcandidates.iterator(); it.hasNext();) {
					IndividualSet candidate = it.next();
					double value = distance(candidate, orderedLists.get(candidate).get(k));
					if (value > min) {
						it.remove();
					}
				}

				if (lcandidates.size() == 1) {
					break;
				}
			}

			IndividualSet lame = lcandidates.get(0);
			lames.add(lame);
			orderedLists.remove(lame);

			for (List<IndividualSet> list : orderedLists.values()) {
				list.remove(lame);
			}

		}

		assert (lames.size() == n);

		return lames;
	}

	protected double getMinDistance(IndividualSet w0) {
		double min = Double.MAX_VALUE;
		for (IndividualSet w1 : individualSets) {
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
	 * Return the distance of two {@link IndividualSet}s.
	 * 
	 * @param w0
	 *            first set
	 * @param w1
	 *            second set
	 * @return the distance
	 */
	protected double distance(IndividualSet w0, IndividualSet w1) {
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
		IndividualSet w0 = new IndividualSet(individual, id0);

		IndividualSet eq = null;

		for (IndividualSet w1 : individualSets) {
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
		IndividualSet individualSet = map.remove(individual);

		if (individualSet.size() == 1) {
			individualSets.remove(individualSet);
			freeIDs.add(individualSet.getId());
		} else {
			individualSet.remove(individual);
		}

		fitnessDirty = true;
	}

	/**
	 * Calculate the distance between two {@link IndividualSet}s.
	 * 
	 * @param w0
	 *            the first set
	 * @param w1
	 *            the second set
	 * @return the distance
	 */
	protected double calculateDistance(IndividualSet w0, IndividualSet w1) {
		return w0.getObjectives().distance(w1.getObjectives());
	}

	/**
	 * Calculate the fitness.
	 */
	protected void calculateFitness() {
		for (IndividualSet individual : individualSets) {
			int s = 0;
			for (IndividualSet other : individualSets) {
				if (individual != other && individual.dominates(other)) {
					s += other.size();
				}
			}
			individual.setStrength(s);
		}

		for (IndividualSet individual : individualSets) {
			int f = 0;
			for (IndividualSet other : individualSets) {
				if (individual != other && other.dominates(individual)) {
					f += other.getStrength() * other.size();
				}
			}
			individual.setFitness(f);
		}
	}

	/**
	 * Returns all dominated {@link IndividualSet}s (fitness > 0).
	 * 
	 * @return all dominated IndividualSets
	 */
	protected List<IndividualSet> getDominated() {
		List<IndividualSet> dominated = new ArrayList<IndividualSet>();

		for (IndividualSet w0 : individualSets) {
			if (w0.getFitness() > 0.0) {
				dominated.add(w0);
			}
		}

		return dominated;
	}

	/**
	 * Returns all non-dominated {@link IndividualSet}s (fitness == 0).
	 * 
	 * @return all non-dominated individualSets
	 */
	protected List<IndividualSet> getNonDominated() {
		List<IndividualSet> dominated = new ArrayList<IndividualSet>();

		for (IndividualSet w0 : individualSets) {
			if (w0.getFitness() == 0.0) {
				dominated.add(w0);
			}
		}

		return dominated;
	}

	/**
	 * Returns the total number of {@link Individual}s in a collection of
	 * {@link IndividualSet}s.
	 * 
	 * @param collection
	 *            the collection of IndividualSets
	 * @return the total number of Individuals
	 */
	private int countIndividuals(Collection<IndividualSet> collection) {
		int c = 0;
		for (IndividualSet w : collection) {
			c += w.size();
		}
		return c;
	}

}
