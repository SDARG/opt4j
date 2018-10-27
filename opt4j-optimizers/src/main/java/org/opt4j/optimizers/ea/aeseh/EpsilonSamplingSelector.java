package org.opt4j.optimizers.ea.aeseh;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.optimizers.ea.Selector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link EpsilonSamplingSelector} implements a selection process based on
 * the ε-sampling.
 * 
 * @author Fedor Smirnov
 *
 */
@Singleton
public class EpsilonSamplingSelector implements Selector {

	protected final ESamplingSurvivorGeneration survivorGeneration;

	/**
	 * Basic constructor
	 * 
	 * @param survivorGeneration
	 *            the {@link ESamplingSurvivorGeneration} used for picking the
	 *            {@link Individual}s that will survive and act as potential
	 *            parents
	 */
	@Inject
	public EpsilonSamplingSelector(ESamplingSurvivorGeneration survivorGeneration) {
		this.survivorGeneration = survivorGeneration;
	}

	@Override
	public Collection<Individual> getParents(int mu, Collection<Individual> population) {
		// Does nothing. In the AeSeH algorithm, all survivors are considered as
		// possible
		// parents.
		return population;
	}

	@Override
	public Collection<Individual> getLames(int lambda, Collection<Individual> population) {
		int survivorNumber = population.size() - lambda;
		Set<Individual> survivors = survivorGeneration.getSurvivors(population, survivorNumber);
		Set<Individual> lames = new HashSet<>(population);
		lames.removeAll(survivors);
		return lames;
	}

	@Override
	public void init(int maxsize) {
		// Does nothing.
	}
}
