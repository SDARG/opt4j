package org.opt4j.optimizers.ea;

import java.util.List;
import java.util.Set;

import org.opt4j.core.Individual;

/**
 * Interface for the classes that manage the schedule according to which the
 * neighborhoods are chosen by the {@link AeSeHCoupler} to pick the crossover
 * parent.
 * 
 * @author Fedor Smirnov
 *
 */
public interface NeighborhoodScheduler {

	/**
	 * Perform the operations specific to the current neighborhood that have to
	 * be done before the scheduling.
	 * 
	 * @param neighborhoods
	 */
	public void init(List<Set<Individual>> neighborhoods);

	/**
	 * Return a copy of the neighborhood that shall be used for the creation of
	 * the next pair of parents.
	 * 
	 * @return Copy of the neighborhood set.
	 */
	public Set<Individual> next();

}
