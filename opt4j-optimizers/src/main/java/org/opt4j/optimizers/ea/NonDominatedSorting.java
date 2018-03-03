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
import org.opt4j.core.Objective.Sign;
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
	 * @return an ordered list of the non-dominated front. The first entry is
	 *         made up by the first front, that is by the Pareto-optimal
	 *         solutions
	 */
	public static List<List<Individual>> generateFronts(Collection<Individual> individuals) {
		// assign an id to each individual that corresponds to its index in an
		// array
		List<Individual> pop = new ArrayList<Individual>(individuals);
		Map<Individual, Integer> id = new HashMap<Individual, Integer>();
		for (int i = 0; i < pop.size(); i++) {
			id.put(pop.get(i), i);
		}
		List<List<Individual>> fronts = new ArrayList<List<Individual>>();
		// Initialize a map where an individual is assigned to the individuals
		// that it dominates
		Map<Individual, List<Individual>> S = new HashMap<Individual, List<Individual>>();
		// n is an array where for each individual, the number of individuals
		// that dominate it is stored
		int[] n = new int[pop.size()];
		for (Individual e : pop) {
			S.put(e, new ArrayList<Individual>());
			n[id.get(e)] = 0;
		}
		// compare each individual with each other individual
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
		// The first front consists of individuals that are dominated by zero
		// other individuals
		List<Individual> f1 = new ArrayList<Individual>();
		for (Individual i : pop) {
			if (n[id.get(i)] == 0) {
				f1.add(i);
			}
		}
		fronts.add(f1);
		List<Individual> fi = f1;
		// Create the subsequent fronts. Front f_i is made up by individuals
		// that
		// are not dominated if all individuals from fronts f_j with j < i are
		// removed.
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
			if (!h.isEmpty())
				fronts.add(h);
			fi = h;
		}
		return fronts;
	}

	/**
	 * Return the individuals with the best values for the individual objectives
	 * 
	 * @param firstFront
	 * @return the set of the extreme individuals.
	 */
	public static Set<Individual> getExtremeIndividuals(Collection<Individual> firstFront) {
		Map<Objective, Individual> bestIndis = new HashMap<Objective, Individual>();
		for (Individual indi : firstFront) {
			for (Objective o : indi.getObjectives().getKeys()) {
				double value = indi.getObjectives().get(o).getDouble();
				Sign sign = o.getSign();
				if (!bestIndis.containsKey(o)) {
					bestIndis.put(o, indi);
				} else {
					double storedValue = bestIndis.get(o).getObjectives().get(o).getDouble();
					if ((storedValue < value && sign.equals(Sign.MAX))
							|| storedValue > value && sign.equals(Sign.MIN)) {
						bestIndis.put(o, indi);
					}
				}
			}
		}
		return new HashSet<Individual>(bestIndis.values());
	}
}
