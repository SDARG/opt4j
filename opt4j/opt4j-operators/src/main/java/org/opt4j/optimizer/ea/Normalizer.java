package org.opt4j.optimizer.ea;

import static org.opt4j.core.Objective.Sign.MAX;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Value;

import com.google.inject.Singleton;

/**
 * The {@link Normalizer} can be used to normalize {@link Objectives}.
 * 
 * @author reimann
 * 
 */
@Singleton
public class Normalizer implements IndividualStateListener {
	private final Map<Objective, Double> minValues = new HashMap<Objective, Double>();
	private final Map<Objective, Double> maxValues = new HashMap<Objective, Double>();

	/**
	 * Returns normalized {@link Objectives}. Each resulting {@link Objective} is in the range between 0 and 1 and has
	 * to be minimized. Here, 0 is the smallest value seen so far for this {@link Objective} for all evaluated
	 * {@link Individual}s and 1 the biggest value, respectively. If an {@link Objective} is infeasible, it is set to 1.
	 * 
	 * @param objectives
	 *            the objectives to normalize
	 * @return the normalized objectives
	 */
	public Objectives normalize(Objectives objectives) {
		Objectives normalized = new Objectives();

		for (Entry<Objective, Value<?>> entry : objectives) {
			Objective objective = entry.getKey();
			double oldvalue = toMinProblem(entry.getKey(), entry.getValue());

			double newvalue = 1.0;

			if (oldvalue != Double.MAX_VALUE) {
				Double min = minValues.get(objective);
				Double max = maxValues.get(objective);
				assert min != null;
				assert max != null;
				newvalue = (oldvalue - min) / (max - min);
			}
			normalized.add(objective, newvalue);
		}

		return normalized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j .core.Individual)
	 */
	@Override
	public void inidividualStateChanged(Individual individual) {
		if (individual.isEvaluated()) {
			for (Entry<Objective, Value<?>> entry : individual.getObjectives()) {
				Objective objective = entry.getKey();
				double value = toMinProblem(entry.getKey(), entry.getValue());
				if (minValues.get(objective) == null || value < minValues.get(objective)) {
					minValues.put(objective, value);
				}
				if (maxValues.get(objective) == null || value > maxValues.get(objective)) {
					maxValues.put(objective, value);
				}
			}
		}
	}

	/**
	 * Transforms the {@link Objective} to a minimization objective, i.e., if the given objective is to be maximized,
	 * the negation of the given value is returned.
	 * 
	 * @param objective
	 *            the respective objective
	 * @param value
	 *            the value to transform
	 * @return the corresponding double value
	 */
	public static final double toMinProblem(Objective objective, Value<?> value) {
		Double v = value.getDouble();

		Double result = null;
		if (v == null) {
			result = Double.MAX_VALUE;
		} else if (objective.getSign() == MAX) {
			result = -v;
		} else {
			result = v;
		}

		assert result != null;
		return result;
	}
}
