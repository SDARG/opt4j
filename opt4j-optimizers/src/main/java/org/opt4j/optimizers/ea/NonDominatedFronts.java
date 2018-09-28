package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;

/**
 * The {@link NonDominatedFronts} sorts each evaluated {@link Individual} into
 * fronts based on the number of other individuals it is dominated by. The first
 * front consists of points that are not dominated at all and so on.
 * 
 * @author Fedor Smirnov
 *
 */
public class NonDominatedFronts {

	protected final List<Collection<Individual>> fronts;

	/**
	 * Creates the {@link NonDominatedFronts} for the given collection of
	 * {@link Individual}s.
	 * 
	 * @param individuals
	 *            the {@link Individual}s that are sorted into non dominated
	 *            fronts
	 */
	public NonDominatedFronts(Collection<Individual> individuals) {
		this.fronts = generateFronts(individuals);
	}

	/**
	 * Sorts the given {@link Individual}s into non-dominated fronts.
	 * 
	 * @param individuals
	 *            the collection of {@link Individual}s that shall be sorted
	 * @return the non-dominated fronts
	 */
	protected List<Collection<Individual>> generateFronts(Collection<Individual> individuals) {
		List<Collection<Individual>> fronts = new ArrayList<>();
		// Assigns an id to each individual that corresponds to its index in an
		// array.
		Map<Individual, Integer> indexMap = new HashMap<>();
		int index = 0;
		for (Individual individual : individuals) {
			indexMap.put(individual, index++);
		}
		// Initializes a map where an individual is assigned to the individuals
		// that it dominates.
		Map<Individual, List<Individual>> dominatedIndividualsMap = new HashMap<>();
		// Creates an array where for each individual, the number of individuals
		// that dominate it is stored.
		int[] dominatingIndividualNumber = new int[individuals.size()];
		for (Individual e : individuals) {
			dominatedIndividualsMap.put(e, new ArrayList<>());
			dominatingIndividualNumber[indexMap.get(e)] = 0;
		}
		determineDomination(individuals, dominatedIndividualsMap, dominatingIndividualNumber, indexMap);
		// The first front consists of individuals that are dominated by zero
		// other individuals.
		List<Individual> f1 = new ArrayList<>();
		for (Individual i : individuals) {
			if (dominatingIndividualNumber[indexMap.get(i)] == 0) {
				f1.add(i);
			}
		}
		fronts.add(f1);
		List<Individual> currentFront = f1;
		// Creates the subsequent fronts. Front f_i is made up by individuals
		// that
		// are not dominated if all individuals from fronts f_j with j < i are
		// removed.
		while (!currentFront.isEmpty()) {
			List<Individual> nextFront = getNextFront(currentFront, dominatedIndividualsMap, dominatingIndividualNumber,
					indexMap);
			if (!nextFront.isEmpty()) {
				fronts.add(nextFront);
			}
			currentFront = nextFront;
		}
		return fronts;
	}

	/**
	 * Returns the front at the specified index.
	 * 
	 * @param index
	 *            the specified index
	 * @return the front at the specified index
	 */
	public Collection<Individual> getFrontAtIndex(int index) {
		return fronts.get(index);
	}

	/**
	 * Returns the number of non-dominated fronts.
	 * 
	 * @return the number of non-dominated fronts
	 */
	public int getFrontNumber() {
		return fronts.size();
	}

	/**
	 * Finds the next non-dominated front by processing the current
	 * non-dominated front. The {@link Individual}s found therein are removed
	 * from consideration. The individuals that are then not dominated form the
	 * next non-dominated front.
	 * 
	 * @param currentFront
	 *            the list of individuals forming the current non-dominated
	 *            front
	 * @param dominatedIndividualsMap
	 *            map mapping an individual on the collection of individuals
	 *            that it dominates
	 * @param dominatingIndividualNumber
	 *            an array where the number of dominating individuals is stored
	 *            for each individual
	 * @param individual2IndexMap
	 *            a map storing the indices of the individuals used to access
	 *            the dominatingIndividualNumber
	 * @return the list of individuals forming the next non-dominated front
	 */
	protected List<Individual> getNextFront(List<Individual> currentFront,
			Map<Individual, List<Individual>> dominatedIndividualsMap, int[] dominatingIndividualNumber,
			Map<Individual, Integer> individual2IndexMap) {
		List<Individual> nextFront = new ArrayList<>();
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
	 * Compares all possible {@link Individual} pairs. For each individual,
	 * stores 1) the number of individuals it is dominated by and 2) the set of
	 * individuals it dominates.
	 * 
	 * @param individuals
	 *            a collection of individuals
	 * @param dominatedIndividualsMap
	 *            A map that is filled during the execution of the method. Each
	 *            individual is mapped onto the set of individuals that are
	 *            dominated by this individual.
	 * @param dominatingIndividualNumber
	 *            An integer array (initialized with zeros) that is filled
	 *            during the execution of this method. Each individual is
	 *            associated with an entry of this array. The integer therein is
	 *            the number of individuals this individual is dominated by.
	 * @param individual2IndexMap
	 *            a map mapping each individual onto its index in the
	 *            dominatingIndividualNumber - array
	 */
	protected void determineDomination(Collection<Individual> individuals,
			Map<Individual, List<Individual>> dominatedIndividualsMap, int[] dominatingIndividualNumber,
			Map<Individual, Integer> individual2IndexMap) {
		List<Individual> individualList = new ArrayList<>(individuals);
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
				// Neither of the two points dominates the other one, so that
				// neither the array
				// keeping track of the domination number nor the map containing
				// the dominating
				// individuals has to be adjusted. Nothing is done in this case.
			}
		}
	}
}
