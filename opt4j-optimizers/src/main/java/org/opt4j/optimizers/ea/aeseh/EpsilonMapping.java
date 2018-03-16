package org.opt4j.optimizers.ea.aeseh;

import java.util.Map;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import com.google.inject.ImplementedBy;

/**
 * Interface for the classes applying the epsilon mapping used by the
 * {@link AeSeHSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(AdditiveEpsilonMapping.class)
public interface EpsilonMapping {

	/**
	 * Map the given objectives on the objectives used for the check of the
	 * epsilon dominance.
	 * 
	 * @param original
	 *            : the actual objectives of the individual
	 * @param epsilon
	 *            : the epsilon value
	 * @param objectiveAmplitudes
	 *            : a map containing the amplitude values of the objectives
	 * @return : objectives enhanced by the epsilon value
	 */
	public Objectives mapObjectives(final Objectives original, double epsilon, Map<Objective, Double> objectiveAmplitudes);

	/**
	 * Create a map mapping the objectives to their amplitudes (difference
	 * between maximal and minimal value). These extreme values are used during
	 * epsilon mapping to scale the delta according to the objective values of
	 * the individual group under consideration.
	 * 
	 * @param individuals
	 * @return map mapping each objective onto ist amplitude
	 */
	public Map<Objective, Double> findObjectiveAmplitudes(Set<Individual> individuals);

}
