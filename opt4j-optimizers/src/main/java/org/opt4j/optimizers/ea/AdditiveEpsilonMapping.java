package org.opt4j.optimizers.ea;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

/**
 * Implements the evenly spaced adaptive epsilon function.
 * 
 * @author Fedor Smirnov
 *
 */
public class AdditiveEpsilonMapping implements EpsilonMapping {

	@Override
	public Objectives mapObjectives(Objectives original, double epsilon, Map<Objective, Double> objectiveAmplitudes) {
		Objectives result = new Objectives();
		for (Objective obj : original.getKeys()) {
			double value = original.get(obj).getDouble();
			double delta = epsilon * objectiveAmplitudes.get(obj) * (obj.getSign().equals(Sign.MAX) ? 1 : -1);
			result.add(obj, value + delta);
		}
		return result;
	}

	@Override
	public Map<Objective, Double> findObjectiveAmplitudes(Set<Individual> individuals) {
		Map<Objective, Double> maximumMap = new HashMap<Objective, Double>();
		Map<Objective, Double> minimumMap = new HashMap<Objective, Double>();
		Map<Objective, Double> amplitudeMap = new HashMap<Objective, Double>();
		for (Individual indi : individuals) {
			for (Objective obj : indi.getObjectives().getKeys()) {
				double value = indi.getObjectives().get(obj).getDouble();
				if (!maximumMap.containsKey(obj) || maximumMap.get(obj) < value) {
					maximumMap.put(obj, value);
				} 
				if (!minimumMap.containsKey(obj) || minimumMap.get(obj) > value) {
					minimumMap.put(obj, value);
				}
			}
		}
		for (Objective obj : maximumMap.keySet()) {
			amplitudeMap.put(obj, maximumMap.get(obj) - minimumMap.get(obj));
		}
		return amplitudeMap;
	}
}
