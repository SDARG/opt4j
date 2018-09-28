package org.opt4j.optimizers.ea.aeseh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.NonDominatedFronts;

import com.google.inject.Inject;

/**
 * The {@link ESamplingSurvivorGenerationBasic} implements the basic survivor
 * selection used by the Adaptive ε-sampling and ε-hood for evolutionary
 * many-objective optimization.
 * 
 * @author Fedor Smirnov
 *
 */
public class ESamplingSurvivorGenerationBasic implements ESamplingSurvivorGeneration {

	protected final Random random;
	protected final EpsilonMapping epsilonMapping;
	protected final EpsilonAdaptation epsilonAdaption;
	protected final AdaptiveEpsilon adaptiveEpsilonSampling;

	/**
	 * Basic constructor.
	 * 
	 * @param epsilonMapping
	 *            an {@link EpsilonMapping} that is used to enhance the
	 *            {@link Objectives} during the choice of the survivors
	 * @param epsilonAdaption
	 *            an {@link EpsilonAdaptation} that adjusts the ε valued used
	 *            for the choice of the survivors
	 * @param random
	 *            the {@link Random} used for the sampling
	 */
	@Inject
	public ESamplingSurvivorGenerationBasic(Random random, EpsilonMapping epsilonMapping,
			EpsilonAdaptation epsilonAdaption,
			@Constant(value = "epsilonSample", namespace = ESamplingSurvivorGeneration.class) double epsilonSample,
			@Constant(value = "epsilonSampleDelta", namespace = ESamplingSurvivorGeneration.class) double epsilonSampleDelta,
			@Constant(value = "epsilonSampleDeltaMax", namespace = ESamplingSurvivorGeneration.class) double epsilonSampleDeltaMax,
			@Constant(value = "epsilonSampleDeltaMin", namespace = ESamplingSurvivorGeneration.class) double epsilonSampleDeltaMin) {
		this.random = random;
		this.epsilonMapping = epsilonMapping;
		this.epsilonAdaption = epsilonAdaption;
		this.adaptiveEpsilonSampling = new AdaptiveEpsilon(epsilonSample, epsilonSampleDelta, epsilonSampleDeltaMax,
				epsilonSampleDeltaMin);
	}

	@Override
	public Set<Individual> getSurvivors(Collection<Individual> population, int survivorNumber) {
		Set<Individual> survivors;
		// get the non-dominated front and the extreme solutions
		NonDominatedFronts fronts = new NonDominatedFronts(population);
		Collection<Individual> paretoSolutions = fronts.getFrontAtIndex(0);
		Set<Individual> extremeIndividuals = getExtremeIndividuals(paretoSolutions);

		if (paretoSolutions.size() > survivorNumber) {
			// more non-dominated solutions than survivors => apply ε-sampling
			survivors = addNonDominatedSurvivors(extremeIndividuals, paretoSolutions, survivorNumber);
		} else {
			survivors = addDominatedSurvivors(survivorNumber, fronts);
		}
		return survivors;
	}

	/**
	 * Creates the survivor pool by adding the ε-sampled individuals to the
	 * extreme individuals.
	 * 
	 * @param extremeIndividuals
	 *            the {@link Individual}s with (positively) extreme values for
	 *            their {@link Objective}s
	 * @param firstFront
	 *            the {@link Individual} that are not dominated at all
	 * @param survivorNumber
	 *            the desired number of survivors
	 */
	protected Set<Individual> addNonDominatedSurvivors(Collection<Individual> extremeIndividuals,
			Collection<Individual> firstFront, int survivorNumber) {
		Set<Individual> survivors = new HashSet<>();
		List<Individual> nonDominatedIndividuals = new ArrayList<>(firstFront);
		// the extreme values always survive
		survivors.addAll(extremeIndividuals);
		nonDominatedIndividuals.removeAll(extremeIndividuals);
		Set<Individual> epsilonDominantIndividuals = new HashSet<>();
		Set<Individual> epsilonDominatedIndividuals = new HashSet<>();
		applyEpsilonSampling(nonDominatedIndividuals, epsilonDominantIndividuals, epsilonDominatedIndividuals,
				adaptiveEpsilonSampling.getEpsilon());
		boolean tooManyEpsilonDominantIndividuals = (extremeIndividuals.size()
				+ epsilonDominantIndividuals.size()) > survivorNumber;
		// adapt the sampling epsilon
		boolean epsilonTooBig = !tooManyEpsilonDominantIndividuals;
		epsilonAdaption.adaptEpsilon(adaptiveEpsilonSampling, epsilonTooBig);
		if (tooManyEpsilonDominantIndividuals) {
			// add a random subset of the epsilon dominant individuals
			List<Individual> survivalCandidates = new ArrayList<>(epsilonDominantIndividuals);
			while (survivors.size() < survivorNumber) {
				int idx = random.nextInt(survivalCandidates.size());
				Individual survivor = survivalCandidates.get(idx);
				survivors.add(survivor);
				survivalCandidates.remove(idx);
			}
		} else {
			// add a random subset from the epsilon dominated individuals
			survivors.addAll(epsilonDominantIndividuals);
			List<Individual> survivorCandidates = new ArrayList<>(epsilonDominatedIndividuals);
			while (survivors.size() < survivorNumber) {
				int idx = random.nextInt(survivorCandidates.size());
				Individual survivor = survivorCandidates.get(idx);
				survivorCandidates.remove(idx);
				survivors.add(survivor);
			}
		}
		return survivors;
	}

	/**
	 * Applies ε-sampling by dividing the given individuals into the two sets of
	 * ε-dominant and ε-dominated individuals.
	 * 
	 * @param firstFront
	 *            the input individuals which constitute the first non-dominated
	 *            front of the current population
	 * @param epsilonDominantIndividuals
	 *            the set that will be filled with the epsilon-dominant
	 *            individuals
	 * @param epsilonDominatedIndividuals
	 *            the set that will be filled with epsilon-dominated individuals
	 * @param samplingEpsilon
	 *            the value used for the epsilon sampling
	 */
	protected void applyEpsilonSampling(List<Individual> firstFront, Set<Individual> epsilonDominantIndividuals,
			Set<Individual> epsilonDominatedIndividuals, double samplingEpsilon) {
		// apply epsilon sampling until the individual list is empty
		List<Individual> nonDominatedIndividuals = new ArrayList<>(firstFront);
		Map<Objective, Double> objectiveAmplitudes = epsilonMapping
				.findObjectiveAmplitudes(new HashSet<>(nonDominatedIndividuals));
		while (!nonDominatedIndividuals.isEmpty()) {
			// pick a random individual
			Individual epsilonDominant = nonDominatedIndividuals.get(random.nextInt(nonDominatedIndividuals.size()));
			Set<Individual> epsilonDominated = new HashSet<>();
			nonDominatedIndividuals.remove(epsilonDominant);
			Objectives epsilonEnhancedObjectives = epsilonMapping.mapObjectives(epsilonDominant.getObjectives(),
					samplingEpsilon, objectiveAmplitudes);
			// gather all individuals epsilon dominated by the picked individual
			for (int i = 0; i < nonDominatedIndividuals.size(); i++) {
				Individual comparisonIndividual = nonDominatedIndividuals.get(i);
				if (epsilonEnhancedObjectives.dominates(comparisonIndividual.getObjectives())) {
					epsilonDominated.add(comparisonIndividual);
				}
			}
			nonDominatedIndividuals.removeAll(epsilonDominated);
			epsilonDominantIndividuals.add(epsilonDominant);
			epsilonDominatedIndividuals.addAll(epsilonDominated);
		}
	}

	/**
	 * In the case where the first non-dominated front does not suffice to
	 * create enough survivors, dominated solutions are added to the survivor
	 * pool.
	 * 
	 * @param survivorNumber
	 *            the desired number of survivors
	 * @param fronts
	 *            the non-dominated fronts
	 */
	protected Set<Individual> addDominatedSurvivors(int survivorNumber, NonDominatedFronts fronts) {
		Set<Individual> survivors = new HashSet<>();
		// non-dominated solutions do not suffice to generate the number of
		// survivors => add dominated solutions
		survivors.addAll(fronts.getFrontAtIndex(0));
		int frontIndex = 1;
		// Fill the survivors by iteratively adding the dominated fronts.
		while (survivorNumber > (fronts.getFrontAtIndex(frontIndex).size()) + survivors.size()) {
			survivors.addAll(fronts.getFrontAtIndex(frontIndex));
			frontIndex++;
		}
		List<Individual> currentFront = new ArrayList<>(fronts.getFrontAtIndex(frontIndex));
		while (survivorNumber > survivors.size()) {
			// choose a random survivor from the current front
			Individual survivor = currentFront.get(random.nextInt(currentFront.size()));
			survivors.add(survivor);
			currentFront.remove(survivor);
		}
		return survivors;
	}

	/**
	 * Returns the {@link Individual}s with the best values for the individual
	 * {@link Objective}.
	 * 
	 * @param firstFront
	 *            the list of {@link Individual}s constituting the first
	 *            non-dominated front
	 * 
	 * @return the set of the extreme individuals
	 */
	protected Set<Individual> getExtremeIndividuals(Collection<Individual> firstFront) {
		Map<Objective, Individual> bestIndis = new HashMap<>();
		Map<Objective, Double> extremeValues = new HashMap<>();
		Individual firstIndi = firstFront.iterator().next();
		List<Objective> objList = new ArrayList<>(firstIndi.getObjectives().getKeys());
		// iterate the individuals
		for (Individual indi : firstFront) {
			// iterate the objectives and their values
			double[] values = indi.getObjectives().array();
			for (int i = 0; i < objList.size(); i++) {
				Objective obj = objList.get(i);
				double value = values[i];
				if (!bestIndis.containsKey(obj) || extremeValues.get(obj) > value) {
					bestIndis.put(obj, indi);
					extremeValues.put(obj, value);
				}
			}
		}
		return new HashSet<>(bestIndis.values());
	}
}
