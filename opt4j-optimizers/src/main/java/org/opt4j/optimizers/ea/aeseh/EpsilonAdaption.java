package org.opt4j.optimizers.ea.aeseh;

import com.google.inject.ImplementedBy;

/**
 * Interface for the classes that manage the adaptation of the epsilon value
 * used for survivor selection by {@link AeSeHSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(DefaultEpsilonAdaptation.class)
public interface EpsilonAdaption {

	/**
	 * returns the current value of the sampling epsilon.
	 * 
	 * @return Current epsilon_s
	 */
	public double getSamplingEpsilon();

	/**
	 * Returns the current value of the epsilon used for the creation of the
	 * neighborhoods
	 * 
	 * @return Current epsilon_h
	 */
	public double getNeighborhoodEpsilon();

	/**
	 * At the end of the selection process, the epsilon_s value is adjusted
	 * depending on the number of created epsilon-dominant survivors. The goal is to
	 * find an epsilon_s value that results in the creation of exactly alpha
	 * epsilon-dominant survivors, with alpha as the population size.
	 * 
	 * @param tooManyEpsilonDominantIndividuals
	 *            {@code true} if too many individuals have been created
	 */
	public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals);

	/**
	 * At the end of the neighborhood creation, the epsilon_h value is adjusted
	 * depending on the number of created neighborhoods. The goal is to find a value
	 * where the number of neighborhoods is near a user-defined value.
	 * 
	 * @param tooManyNeighborhoods
	 *            {@code true} if too many individuals have been created
	 */
	public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods);

}
