package org.opt4j.optimizers.ea.aeseh;

import java.util.Collection;
import java.util.Set;

import org.opt4j.core.Individual;

import com.google.inject.ImplementedBy;

/**
 * 
 * The {@link ESamplingSurvivorGeneration} generates the survivor pool during
 * the selection implemented by {@link EpsilonSamplingSelector}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(ESamplingSurvivorGenerationBasic.class)
public interface ESamplingSurvivorGeneration {

	/**
	 * Generates the survivors out of the input collection of {@link Individual}s.
	 * 
	 * @param population
	 *            the current population (union of the parent- and the
	 *            offspring-sets from the current iteration)
	 * @param survivorNumber
	 *            the number of survivors to create
	 * @return the survivors (used as the pool for the parent candidates for the
	 *         next generation)
	 */
	public Set<Individual> getSurvivors(Collection<Individual> population, int survivorNumber);

}
