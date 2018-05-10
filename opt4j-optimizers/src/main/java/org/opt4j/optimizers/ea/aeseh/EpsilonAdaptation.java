package org.opt4j.optimizers.ea.aeseh;

import com.google.inject.ImplementedBy;

/**
 * The {@link EpsilonAdaptation} manages the adaptation of the ε-value stored in
 * the {@link AdaptiveEpsilon}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(EpsilonAdaptationDefault.class)
public interface EpsilonAdaptation {

	/**
	 * Adjusts the ε-value according to the given {@link AdaptiveEpsilon}.
	 * 
	 * @param adaptiveEpsilon
	 *            the {@link AdaptiveEpsilon} contains the ε-value and the
	 *            information about its adaptation
	 * @param epsilonTooBig
	 *            {@code} if the current epsilon is too big
	 */
	public void adaptEpsilon(AdaptiveEpsilon adaptiveEpsilon, boolean epsilonTooBig);

	/**
	 * At the end of the selection process, the sampling ε value is adjusted
	 * depending on the number of created ε-dominant survivors. The goal is to
	 * find an ε-value that results in the creation of exactly α ε-dominant
	 * survivors, with α as the population size.
	 * 
	 * @param tooManyEpsilonDominantIndividuals
	 *            {@code true} if too many individuals have been created
	 */
	//public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals);

	/**
	 * At the end of the neighborhood creation, the neighborhood ε value is
	 * adjusted depending on the number of created neighborhoods. The goal is to
	 * find an ε-value where the number of neighborhoods is near a user-defined
	 * value.
	 * 
	 * @param tooManyNeighborhoods
	 *            {@code true} if too many individuals have been created
	 */
	//public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods);
}
