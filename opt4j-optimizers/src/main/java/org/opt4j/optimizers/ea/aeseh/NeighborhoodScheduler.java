package org.opt4j.optimizers.ea.aeseh;

import java.util.Set;

import org.opt4j.core.Individual;

/**
 * Interface for the classes that manage the schedule according to which the
 * neighborhoods are chosen by the {@link AeSeHCoupler} to pick the crossover
 * parents.
 * 
 * @author Fedor Smirnov
 *
 */
public interface NeighborhoodScheduler {

	/**
	 * returns a copy of the neighborhood that shall be used for the creation of
	 * the next pair of parents
	 * 
	 * @return copy of the neighborhood set
	 */
	public Set<Individual> next();

}
