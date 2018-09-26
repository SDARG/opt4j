package org.opt4j.optimizers.ea.aeseh;

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
import org.opt4j.core.start.Constant;
import org.opt4j.operators.crossover.Pair;
import org.opt4j.optimizers.ea.Coupler;

import com.google.inject.Inject;

/**
 * The {@link EpsilonNeighborhoodCoupler} implements a parent selection process
 * based on by the ε-neighborhood.
 * 
 * @author Fedor Smirnov
 *
 */
public class EpsilonNeighborhoodCoupler implements Coupler {

	protected final EpsilonAdaptation epsilonAdaption;
	protected final EpsilonMapping epsilonMapping;
	protected final Random random;
	protected final int plannedNeighborhoodNumber;
	protected final AdaptiveEpsilon adaptiveEpsilonNeighborhood;

	/**
	 * Basic constructor.
	 * 
	 * @param epsilonMapping
	 *            an {@link EpsilonMapping} that is used to enhance the
	 *            {@link Objectives} during the creation of the neighborhoods
	 * @param epsilonAdaption
	 *            an {@link EpsilonAdaptation} that adjusts the ε valued used
	 *            for the creation of the neighborhoods
	 * @param random
	 *            a {@link Random}
	 * @param plannedNeighborhoodNumber
	 *            A value provided by the user. The ε used for the creation of
	 *            the neighborhoods is adjusted in order to create a number of
	 *            neighborhoods similar to this value.
	 */
	@Inject
	public EpsilonNeighborhoodCoupler(EpsilonMapping epsilonMapping, EpsilonAdaptation epsilonAdaption, Random random,
			@Constant(value = "neighborhoodNumber", namespace = EpsilonNeighborhoodCoupler.class) int plannedNeighborhoodNumber,
			@Constant(value = "epsilonNeighborhood", namespace = EpsilonNeighborhoodCoupler.class) double epsilonNeighborhood,
			@Constant(value = "epsilonNeighborhoodDelta", namespace = EpsilonNeighborhoodCoupler.class) double epsilonNeighborhoodDelta,
			@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = EpsilonNeighborhoodCoupler.class) double epsilonNeighborhoodDeltaMax,
			@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = EpsilonNeighborhoodCoupler.class) double epsilonNeighborhoodDeltaMin) {
		this.epsilonMapping = epsilonMapping;
		this.epsilonAdaption = epsilonAdaption;
		this.random = random;
		this.plannedNeighborhoodNumber = plannedNeighborhoodNumber;
		this.adaptiveEpsilonNeighborhood = new AdaptiveEpsilon(epsilonNeighborhood, epsilonNeighborhoodDelta,
				epsilonNeighborhoodDeltaMax, epsilonNeighborhoodDeltaMin);
	}

	/**
	 * Generates parent couples. Distributes the parent {@link Individual}s onto
	 * neighborhoods. Both parents of a couple are picked from the same
	 * neighborhood. Uses a {@link NeighborhoodSchedulerRoundRobin} to arbitrate
	 * the neighborhoods from where the parent couples are picked.
	 * 
	 * @param size
	 *            the number of couples that is generated
	 * @param survivors
	 *            the {@link Individual}s that can be used as parents
	 * @return a collection of {@link Individual} pairs, that will be used to
	 *         generate the next generation of individuals
	 * 
	 */
	@Override
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> survivors) {
		Collection<Pair<Individual>> result = new HashSet<>();
		List<Set<Individual>> neighborhoods = createNeighborhoods(survivors);
		NeighborhoodSchedulerRoundRobin scheduler = new NeighborhoodSchedulerRoundRobin(neighborhoods);
		for (int i = 0; i < size; i++) {
			result.add(pickCouple(scheduler.next()));
		}
		return result;
	}

	/**
	 * Picks a couple of parents from the given neighborhood. Here, we just pick
	 * two random individuals.
	 * 
	 * @param neighborhood
	 *            the set of similar {@link Individual}s from where the parents
	 *            are picked
	 * @return the pair that was picked as parents for a crossover
	 */
	protected Pair<Individual> pickCouple(Set<Individual> neighborhood) {
		if (neighborhood.size() == 1) {
			Individual hermit = neighborhood.iterator().next();
			return new Pair<>(hermit, hermit);
		}
		List<Individual> individualList = new ArrayList<>(neighborhood);
		Individual first = individualList.remove(random.nextInt(individualList.size()));
		Individual second = individualList.remove(random.nextInt(individualList.size()));
		return new Pair<>(first, second);
	}

	/**
	 * Applies the epsilon neighborhood creation.
	 * 
	 * @param survivorPool
	 *            a list of {@link Individual}s that can be used as parents
	 * @return a list of individual sets. Each set is considered as a
	 *         neighborhood.
	 */
	protected List<Set<Individual>> createNeighborhoods(List<Individual> survivorPool) {
		List<Set<Individual>> neighborhoods = new ArrayList<>();
		Map<Objective, Double> objectiveAmplitudes = epsilonMapping
				.findObjectiveAmplitudes(new HashSet<>(survivorPool));
		List<Individual> survivors = new ArrayList<>(survivorPool);
		while (!survivors.isEmpty()) {
			// pick a random individual
			int idx = random.nextInt(survivors.size());
			Individual reference = survivors.remove(idx);
			Set<Individual> neighborhood = new HashSet<>();
			Objectives epsilonEnhancedObjectives = epsilonMapping.mapObjectives(reference.getObjectives(),
					adaptiveEpsilonNeighborhood.getEpsilon(), objectiveAmplitudes);
			// put the individuals epsilon-dominated by the reference into its
			// neighborhood
			for (Individual candidate : survivors) {
				if (epsilonEnhancedObjectives.dominates(candidate.getObjectives())) {
					neighborhood.add(candidate);
				}
			}
			survivors.removeAll(neighborhood);
			neighborhood.add(reference);
			neighborhoods.add(neighborhood);
		}
		// adapt the epsilon
		boolean epsilonTooBig = neighborhoods.size() < plannedNeighborhoodNumber;
		epsilonAdaption.adaptEpsilon(adaptiveEpsilonNeighborhood, epsilonTooBig);
		return neighborhoods;
	}
}
