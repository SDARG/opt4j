package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

/**
 * Class used for the non-dominated sorting. During non-dominated sorting, the
 * evaluated individuals are sorted into fronts based on the number of
 * individuals they are dominated by. The first front consists of points that
 * are not dominated at all and so on.
 * 
 * @author Fedor Smirnov
 *
 */
public class NonDominatedSorting {

	private NonDominatedSorting() {
	}

	/**
	 * Sort the given individuals into non-dominated fronts
	 * 
	 * @param individuals
	 * @return an ordered list of the non-dominated front. The first entry is made
	 *         up by the first front, that is by the Pareto-optimal solutions
	 */
	public static List<List<Individual>> generateFronts(Collection<Individual> individuals) {
		// assign an id to each individual that corresponds to its index in an
		// array
		List<Individual> individualList = new ArrayList<Individual>(individuals);
		Map<Individual, Integer> indexMap = new HashMap<Individual, Integer>();
		for (int i = 0; i < individualList.size(); i++) {
			indexMap.put(individualList.get(i), i);
		}
		List<List<Individual>> fronts = new ArrayList<List<Individual>>();
		// Initialize a map where an individual is assigned to the individuals
		// that it dominates
		Map<Individual, List<Individual>> dominatedIndividualsMap = new HashMap<Individual, List<Individual>>();
		// n is an array where for each individual, the number of individuals
		// that dominate it is stored
		int[] dominatingIndividualNumber = new int[individualList.size()];
		for (Individual e : individualList) {
			dominatedIndividualsMap.put(e, new ArrayList<Individual>());
			dominatingIndividualNumber[indexMap.get(e)] = 0;
		}
		determineDomination(individualList, dominatedIndividualsMap, dominatingIndividualNumber, indexMap);
		// The first front consists of individuals that are dominated by zero
		// other individuals
		List<Individual> f1 = new ArrayList<Individual>();
		for (Individual i : individualList) {
			if (dominatingIndividualNumber[indexMap.get(i)] == 0) {
				f1.add(i);
			}
		}
		fronts.add(f1);
		List<Individual> currentFront = f1;
		// Create the subsequent fronts. Front f_i is made up by individuals
		// that
		// are not dominated if all individuals from fronts f_j with j < i are
		// removed.
		while (!currentFront.isEmpty()) {
			List<Individual> nextFront = getNextFront(currentFront, dominatedIndividualsMap, dominatingIndividualNumber, indexMap);
			if (!nextFront.isEmpty())
				fronts.add(nextFront);
			currentFront = nextFront;
		}
		return fronts;
	}

	/**
	 * Find the next non-dominated front by processing the current non-dominated
	 * front. The individuals found therein are removed from consideration. The
	 * individuals that are then not dominated form the next non-dominated front.
	 * 
	 * @param currentFront
	 *            : The list of individuals forming the current non-dominated front
	 * @param dominatedIndividualsMap
	 *            : map mapping an individual on the collection of individuals that
	 *            it dominates
	 * @param dominatingIndividualNumber
	 *            : an array where the number of dominating individuals is stored
	 *            for each individual
	 * @param individual2IndexMap
	 *            : a map storing the indices of the individuals used to access the
	 *            dominatingIndividualNumber
	 * @return The list of individuals forming the next non-dominated front.
	 */
	protected static List<Individual> getNextFront(List<Individual> currentFront,
			Map<Individual, List<Individual>> dominatedIndividualsMap, int[] dominatingIndividualNumber,
			Map<Individual, Integer> individual2IndexMap) {
		List<Individual> nextFront = new ArrayList<Individual>();
		for (Individual dominant : currentFront) {
			for (Individual dominated : dominatedIndividualsMap.get(dominant)) {
				dominatingIndividualNumber[individual2IndexMap.get(dominated)]--;
				if (dominatingIndividualNumber[individual2IndexMap.get(dominated)] == 0) {
					nextFront.add(dominated);
				}
			}
		}
		return nextFront;
	}

	/**
	 * Compare all possible individual pairs. For each individual, store 1) the
	 * number of individuals it is dominated by and 2) the set of individuals it
	 * dominates.
	 * 
	 * @param individualList
	 *            : A list of the individuals
	 * @param dominatedIndividualsMap
	 *            : A map that is filled during the execution of the method. Each
	 *            individual is mapped onto the set of individuals that are
	 *            dominated by this individual.
	 * @param dominatingIndividualNumber
	 *            : An integer array (initialized with zeros) that is filled during
	 *            the execution of this method. Each individual is associated with
	 *            an entry of this array. The integer therein is the number of
	 *            individuals this individual is dominated by.
	 * @param individual2IndexMap
	 *            : A map mapping each individual onto its index in the
	 *            dominatingIndividualNumber - array.
	 */
	protected static void determineDomination(List<Individual> individualList,
			Map<Individual, List<Individual>> dominatedIndividualsMap, int[] dominatingIndividualNumber,
			Map<Individual, Integer> individual2IndexMap) {
		// compare each individual with each other individual
		for (int i = 0; i < individualList.size(); i++) {
			for (int j = i + 1; j < individualList.size(); j++) {
				Individual p = individualList.get(i);
				Individual q = individualList.get(j);
				Objectives po = p.getObjectives();
				Objectives qo = q.getObjectives();
				if (po.dominates(qo)) {
					dominatedIndividualsMap.get(p).add(q);
					dominatingIndividualNumber[individual2IndexMap.get(q)]++;
				} else if (qo.dominates(po)) {
					dominatedIndividualsMap.get(q).add(p);
					dominatingIndividualNumber[individual2IndexMap.get(p)]++;
				}
			}
		}
	}

	/**
	 * Return the individuals with the best values for the individual objectives
	 * 
	 * @param firstFront
	 * @return the set of the extreme individuals.
	 */
	public static Set<Individual> getExtremeIndividuals(Collection<Individual> firstFront) {
		Map<Objective, Individual> bestIndis = new HashMap<Objective, Individual>();
		Map<Objective, Double> extremeValues = new HashMap<Objective, Double>();
		Individual firstIndi = firstFront.iterator().next();
		List<Objective> objList = new ArrayList<Objective>(firstIndi.getObjectives().getKeys());
		// iterate the individuals
		for (Individual indi : firstFront){
			// iterate the objectives and their values
			double[] values = indi.getObjectives().array();
			for (int i = 0; i < objList.size(); i++){
				Objective obj = objList.get(i);
				double value = values[i];
				if(!bestIndis.containsKey(obj) || extremeValues.get(obj) > value){
					bestIndis.put(obj, indi);
					extremeValues.put(obj, value);
				}
			}
		}
		return new HashSet<Individual>(bestIndis.values());
	}
}
