package org.opt4j.optimizers.ea;

/**
 * Interface for the classes that manage the adaptation of the epsilon value
 * used for survivor selection by {@link AeSeHSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
public interface EpsilonAdaption {

	/**
	 * Returns the current value of the sampling epsilon.
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
	public double getNeighborhoodEspilon();

	/**
	 * At the end of the selection process, the epsilon_s value is adjusted
	 * depending on the number of created epsilon-dominant survivors. The goal
	 * is to find an epsilon_s value that results in the creation of exactly
	 * alpha epsilon-dominant survivors, with alpha as the population size.
	 * 
	 * @param tooManyEpsilonDominantIndividuals
	 *            : TRUE => too many dominant survivors were created; FALSE =>
	 *            not enough dominant survivors were created
	 */
	public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals);

	/**
	 * At the end of the neighborhood creation, the epsilon_h value is adjusted
	 * depending on the number of created neighborhoods. The goal is to find a
	 * value where the number of neighborhoods is near a user-defined value.
	 * 
	 * @param tooManyNeighborhoods
	 *            : TRUE => too many neighborhoods created; FALSE => not enough
	 *            neighborhoods created
	 */
	public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods);

}
