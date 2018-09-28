package org.opt4j.optimizers.ea.aeseh;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

/**
 * The {@link EpsilonMappingAdditive} implements the evenly spaced adaptive ε
 * function.
 * 
 * @author Fedor Smirnov
 *
 */
public class EpsilonMappingAdditive implements EpsilonMapping {

	/**
	 * Applies ε mapping by enhancing all of the given {@link Objective}s by the
	 * ε fraction of the objective amplitude.
	 * 
	 * @param original
	 *            the {@link Objectives} that are enhanced by this method
	 * @param epsilon
	 *            the fraction used for the enhancement
	 * @param objectiveAmplitudes
	 *            the map mapping its objective onto its amplitude
	 * @return enhanced {@link Objectives} where each {@link Objective} is
	 *         improved by the ε fraction of the objective's amplitude
	 */
	@Override
	public Objectives mapObjectives(Objectives original, double epsilon, Map<Objective, Double> objectiveAmplitudes) {
		Objectives result = new Objectives();
		Iterator<Objective> iterator = original.getKeys().iterator();
		double[] values = original.array();
		for (int i = 0; i < original.size(); i++) {
			Objective obj = iterator.next();
			double value = values[i] * (obj.getSign().equals(Sign.MIN) ? 1 : -1);
			if (objectiveAmplitudes.containsKey(obj)) {
				// the ε mapping is only applied if the objective is feasible
				// for at least one
				// individual
				value += epsilon * objectiveAmplitudes.get(obj) * (obj.getSign().equals(Sign.MAX) ? 1 : -1);
			}
			result.add(obj, value);
		}
		return result;
	}

	@Override
	public Map<Objective, Double> findObjectiveAmplitudes(Set<Individual> individuals) {
		Map<Objective, Double> maximumMap = new HashMap<>();
		Map<Objective, Double> minimumMap = new HashMap<>();
		Map<Objective, Double> amplitudeMap = new HashMap<>();
		for (Individual indi : individuals) {
			Objectives objectives = indi.getObjectives();
			Iterator<Objective> iterator = objectives.getKeys().iterator();
			double[] values = objectives.array();
			for (int i = 0; i < objectives.size(); i++) {
				Objective obj = iterator.next();
				if (objectives.get(obj).getValue() == Objective.INFEASIBLE) {
					continue;
				}
				double value = values[i];
				if (!maximumMap.containsKey(obj) || maximumMap.get(obj) < value) {
					maximumMap.put(obj, value);
				}
				if (!minimumMap.containsKey(obj) || minimumMap.get(obj) > value) {
					minimumMap.put(obj, value);
				}
			}
		}
		for (Entry<Objective, Double> maxEntry : maximumMap.entrySet()) {
			Objective obj = maxEntry.getKey();
			Double maximum = maxEntry.getValue();
			Double minimum = minimumMap.get(obj);
			amplitudeMap.put(obj, maximum - minimum);
		}
		return amplitudeMap;
	}
}
