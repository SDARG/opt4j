package org.opt4j.optimizers.ea.aeseh;

import com.google.inject.ImplementedBy;

/**
 * The {@link EpsilonAdaption} manages the adaptation of the ε-value used for
 * survivor selection by {@link AeSeHSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(DefaultEpsilonAdaptation.class)
public interface EpsilonAdaption {

	/**
	 * Returns the current value of the sampling epsilon.
	 * 
	 * @return current ε used for the sampling
	 */
	public double getSamplingEpsilon();

	/**
	 * Returns the current value of the ε used for the creation of the
	 * neighborhoods.
	 * 
	 * @return current ε for the neighborhood creation
	 */
	public double getNeighborhoodEpsilon();

	/**
	 * At the end of the selection process, the sampling ε value is adjusted
	 * depending on the number of created ε-dominant survivors. The goal is to find
	 * an ε-value that results in the creation of exactly α ε-dominant survivors,
	 * with α as the population size.
	 * 
	 * @param tooManyEpsilonDominantIndividuals
	 *            {@code true} if too many individuals have been created
	 */
	public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals);

	/**
	 * At the end of the neighborhood creation, the neighborhood ε value is adjusted
	 * depending on the number of created neighborhoods. The goal is to find an
	 * ε-value where the number of neighborhoods is near a user-defined value.
	 * 
	 * @param tooManyNeighborhoods
	 *            {@code true} if too many individuals have been created
	 */
	public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods);

}
