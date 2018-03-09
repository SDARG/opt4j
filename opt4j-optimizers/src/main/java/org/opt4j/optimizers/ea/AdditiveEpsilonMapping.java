package org.opt4j.optimizers.ea;

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
 * Implements the evenly spaced adaptive epsilon function.
 * 
 * @author Fedor Smirnov
 *
 */
public class AdditiveEpsilonMapping implements EpsilonMapping {

	@Override
	public Objectives mapObjectives(Objectives original, double epsilon, Map<Objective, Double> objectiveAmplitudes) {
		Objectives result = new Objectives();
		Iterator<Objective> iterator = original.getKeys().iterator();
		double[] values = original.array();
		for (int i = 0; i < original.size(); i++) {
			Objective obj = iterator.next();
			double value = values[i] * (obj.getSign().equals(Sign.MIN) ? 1 : -1);
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
			Objectives objectives = indi.getObjectives();
			Iterator<Objective> iterator = objectives.getKeys().iterator();
			double[] values = objectives.array();
			for (int i = 0; i < objectives.size(); i++) {
				Objective obj = iterator.next();
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
