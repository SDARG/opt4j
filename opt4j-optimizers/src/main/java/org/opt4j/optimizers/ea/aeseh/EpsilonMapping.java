package org.opt4j.optimizers.ea.aeseh;

import java.util.Map;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import com.google.inject.ImplementedBy;

/**
 * The {@link EpsilonMapping} implements the ε mapping used by the
 * {@link EpsilonSamplingSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(EpsilonMappingAdditive.class)
public interface EpsilonMapping {

	/**
	 * Maps the given {@link Objectives} on the objectives used for the check of the ε
	 * dominance.
	 * 
	 * @param original
	 *            the actual objectives of the individual
	 * @param epsilon
	 *            the ε value
	 * @param objectiveAmplitudes
	 *            a map containing the amplitude values of the objectives
	 * @return {@link Objectives} enhanced by the ε value
	 */
	public Objectives mapObjectives(final Objectives original, double epsilon,
			Map<Objective, Double> objectiveAmplitudes);

	/**
	 * Creates a map mapping the {@link Objective}s to their amplitudes (difference between
	 * maximal and minimal value). These extreme values are used during ε mapping to
	 * scale the delta according to the objective values of the {@link Individual} group
	 * under consideration.
	 * 
	 * @param individuals
	 * @return map mapping each {@link Objective} onto its amplitude
	 */
	public Map<Objective, Double> findObjectiveAmplitudes(Set<Individual> individuals);

}
