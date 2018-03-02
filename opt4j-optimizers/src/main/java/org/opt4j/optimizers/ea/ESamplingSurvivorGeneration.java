package org.opt4j.optimizers.ea;

import java.util.Collection;
import java.util.Set;

import org.opt4j.core.Individual;

/**
 * 
 * Interface for the classes which generate the survivor pool during the
 * selection implemented by {@link AeSeHSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
public interface ESamplingSurvivorGeneration {

	/**
	 * Generate the survivors out of the input collection.
	 * 
	 * @param population
	 *            : the current population (union of the parent- and the
	 *            offspring-sets from the current iteration)
	 * @param survivorNumber : The number of survivors to create
	 * @return : the survivors (used as the parent generation for the next
	 *         iteration)
	 */
	public Set<Individual> getSurvivors(Collection<Individual> population, int survivorNumber);

}
