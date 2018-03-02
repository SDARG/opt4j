package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.operators.crossover.Pair;

import com.google.inject.Inject;

/**
 * This class implements a parent selection process based on
 * epsilon-neighborhood. This technique was first proposed in the paper:
 * 
 * Aguirre, Hernán, Akira Oyama, and Kiyoshi Tanaka. "Adaptive ε-sampling and
 * ε-hood for evolutionary many-objective optimization." International
 * Conference on Evolutionary Multi-Criterion Optimization. Springer, Berlin,
 * Heidelberg, 2013.
 * 
 * Please consider citing the paper if you use this class for a scientific
 * publication.
 * 
 * @author Fedor Smirnov
 *
 */
public class AeSeHCoupler implements Coupler {
	protected final EpsilonAdaption epsilonAdaption;
	protected final EpsilonMapping epsilonMapping;
	protected final Random random;
	protected final int plannedNeighborhoodNumber;
	protected final NeighborhoodScheduler scheduler;

	@Inject
	public AeSeHCoupler(EpsilonMapping epsilonMapping, EpsilonAdaption epsilonAdaption, Random random,
			int plannedNeighborhoodNumber, NeighborhoodScheduler scheduler) {
		this.epsilonMapping = epsilonMapping;
		this.epsilonAdaption = epsilonAdaption;
		this.random = random;
		this.plannedNeighborhoodNumber = plannedNeighborhoodNumber;
		this.scheduler = scheduler;
	}

	@Override
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> parents) {
		if (size != parents.size() / 2) {
			throw new IllegalArgumentException("The offspring number should be equal to the population size");
		}
		Collection<Pair<Individual>> result = new HashSet<Pair<Individual>>();
		List<Set<Individual>> neighborhoods = createNeighborhoods(parents);
		// adapt the epsilon
		epsilonAdaption.adaptNeighborhoodEpsilon(neighborhoods.size() > plannedNeighborhoodNumber);
		scheduler.init(neighborhoods);
		for (int i = 0; i < size; i++){
			result.add(pickCouple(scheduler.next()));
		}
		return result;
	}
	
	/**
	 * Pick a couple of parents from the given neighborhood. Here, we just pick two random individuals.
	 * 
	 * @param neighborhood
	 * @return
	 */
	protected Pair<Individual> pickCouple(Set<Individual> neighborhood){
		List<Individual> individualList = new ArrayList<Individual>(neighborhood);
		Individual first = individualList.remove(random.nextInt(individualList.size()));
		Individual second = individualList.remove(random.nextInt(individualList.size()));
		return new Pair<Individual>(first, second);
	}

	/**
	 * Apply the epsilon neighborhood creation.
	 * 
	 * @param survivors
	 * @return
	 */
	protected List<Set<Individual>> createNeighborhoods(List<Individual> survivors) {
		List<Set<Individual>> neighborhoods = new ArrayList<Set<Individual>>();
		Map<Objective, Double> objectiveAmplitudes = epsilonMapping
				.findObjectiveAmplitudes(new HashSet<Individual>(survivors));
		while (!survivors.isEmpty()) {
			// pick a random individual
			int idx = random.nextInt(survivors.size());
			Individual reference = survivors.remove(idx);
			Set<Individual> neighborhood = new HashSet<Individual>();
			Objectives epsilonEnhancedObjectives = epsilonMapping.mapObjectives(reference.getObjectives(),
					epsilonAdaption.getNeighborhoodEspilon(), objectiveAmplitudes);
			// put the individuals epsilon-dominated by the reference into its neighborhood
			for (Individual candidate : survivors) {
				if (epsilonEnhancedObjectives.dominates(candidate.getObjectives()))neighborhood.add(candidate);
			}
			survivors.removeAll(neighborhood);
			neighborhood.add(reference);
			neighborhoods.add(neighborhood);
		}
		return neighborhoods;
	}
}
