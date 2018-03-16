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
import org.opt4j.optimizers.ea.NonDominatedSorting;

import com.google.inject.Inject;

/**
 * The basic survivor selection used by the Adaptive ε-sampling and ε-hood for
 * evolutionary many-objective optimization, without any extensions.
 * 
 * @author Fedor Smirnov
 *
 */
public class DefaultSurvivorGeneration implements ESamplingSurvivorGeneration {

	protected final Random random;
	protected final EpsilonMapping epsilonMapping;
	protected final EpsilonAdaption epsilonAdaption;

	@Inject
	public DefaultSurvivorGeneration(Random random, EpsilonMapping epsilonMapping, EpsilonAdaption epsilonAdaption) {
		this.random = random;
		this.epsilonMapping = epsilonMapping;
		this.epsilonAdaption = epsilonAdaption;
	}

	@Override
	public Set<Individual> getSurvivors(Collection<Individual> population, int survivorNumber) {
		Set<Individual> survivors;
		// get the non-dominated front and the extreme solutions
		List<List<Individual>> fronts = NonDominatedSorting.generateFronts(population);
		Collection<Individual> paretoSolutions = fronts.get(0);
		Set<Individual> extremeIndividuals = NonDominatedSorting.getExtremeIndividuals(paretoSolutions);

		if (paretoSolutions.size() > survivorNumber) {
			// more non-dominated solutions than survivors => apply ε-sampling
			survivors = addNonDominatedSurvivors(extremeIndividuals, paretoSolutions, survivorNumber);
		} else {
			survivors = addDominatedSurvivors(survivorNumber, fronts);
		}
		return survivors;
	}

	/**
	 * Create the survivor pool by adding the epsilon-sampled individuals to the
	 * extreme individuals.
	 * 
	 * @param extremeIndividuals
	 * @param firstFront
	 * @param survivorNumber
	 */
	protected Set<Individual> addNonDominatedSurvivors(Collection<Individual> extremeIndividuals,
			Collection<Individual> firstFront, int survivorNumber) {
		Set<Individual> survivors = new HashSet<Individual>();
		List<Individual> nonDominatedIndividuals = new ArrayList<Individual>(firstFront);
		// the extreme values always survive
		survivors.addAll(extremeIndividuals);
		nonDominatedIndividuals.removeAll(extremeIndividuals);
		Set<Individual> epsilonDominantIndividuals = new HashSet<Individual>();
		Set<Individual> epsilonDominatedIndividuals = new HashSet<Individual>();
		applyEpsilonSampling(nonDominatedIndividuals, epsilonDominantIndividuals, epsilonDominatedIndividuals, epsilonAdaption.getSamplingEpsilon());
		boolean tooManyEpsilonDominantIndividuals = (extremeIndividuals.size()
				+ epsilonDominantIndividuals.size()) > survivorNumber;
		// adapt the sampling epsilon
		epsilonAdaption.adaptSamplingEpsilon(tooManyEpsilonDominantIndividuals);
		if (tooManyEpsilonDominantIndividuals) {
			// add a random subset of the epsilon dominant individuals
			List<Individual> survivalCandidates = new ArrayList<Individual>(epsilonDominantIndividuals);
			while (survivors.size() < survivorNumber) {
				int idx = random.nextInt(survivalCandidates.size());
				Individual survivor = survivalCandidates.get(idx);
				survivors.add(survivor);
				survivalCandidates.remove(idx);
			}
		} else {
			// add a random subset from the epsilon dominated individuals
			survivors.addAll(epsilonDominantIndividuals);
			List<Individual> survivorCandidates = new ArrayList<Individual>(epsilonDominatedIndividuals);
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
	 * Apply epsilon sampling by dividing the given individuals into the two sets of epsilon-dominant and epsilon-dominated individuals.
	 * 
	 * @param firstFront : The input individuals who constitute the first non-dominated front of the current population. 
	 * @param epsilonDominantIndividuals : The set that will be filled with the epsilon-dominant individuals.
	 * @param epsilonDominatedIndividuals : The set that will be filled with epsilon-dominated individuals
	 * @param samplingEpsilon : The value used for the epsilon sampling.
	 */
	protected void applyEpsilonSampling(List<Individual> firstFront, Set<Individual> epsilonDominantIndividuals,
			Set<Individual> epsilonDominatedIndividuals, double samplingEpsilon) {
		// apply epsilon sampling until the individual list is empty
		List<Individual> nonDominatedIndividuals = new ArrayList<Individual>(firstFront);
		Map<Objective, Double> objectiveAmplitudes = epsilonMapping
				.findObjectiveAmplitudes(new HashSet<Individual>(nonDominatedIndividuals));
		while (!nonDominatedIndividuals.isEmpty()) {
			// pick a random individual
			Individual epsilonDominant = nonDominatedIndividuals.get(random.nextInt(nonDominatedIndividuals.size()));
			Set<Individual> epsilonDominated = new HashSet<Individual>();
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
	 * In the case where the first non-dominated front does not suffice to create
	 * enough survivors, dominated solutions are added to the survivor pool.
	 * 
	 * @param survivorNumber
	 * @param fronts
	 */
	protected Set<Individual> addDominatedSurvivors(int survivorNumber, List<List<Individual>> fronts) {
		Set<Individual> survivors = new HashSet<Individual>();
		// non-dominated solutions do not suffice to generate the number of
		// survivors => add dominated solutions
		survivors.addAll(fronts.get(0));
		int frontIndex = 1;
		// Fill the survivors by iteratively adding the dominated fronts.
		while (survivorNumber > fronts.get(frontIndex).size() + survivors.size()) {
			survivors.addAll(fronts.get(frontIndex));
			frontIndex++;
		}
		List<Individual> currentFront = fronts.get(frontIndex);
		while (survivorNumber > survivors.size()) {
			// choose a random survivor from the current front
			Individual survivor = currentFront.get(random.nextInt(currentFront.size()));
			survivors.add(survivor);
			currentFront.remove(survivor);
		}
		return survivors;
	}
}
