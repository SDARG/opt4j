package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

public class ESamplingSurvivorGenerationBasicTest2 {

	protected Objective firstObj = new Objective("first", Sign.MAX);
	protected Objective secondObj = new Objective("second", Sign.MAX);

	protected List<Individual> nonDominated;

	protected Individual firstIndi;
	protected Individual secondIndi;
	protected Individual thirdIndi;
	protected Individual fourthIndi;

	protected Random rand;

	protected EpsilonMapping mapping;

	protected EpsilonAdaptation adaptation;

	protected Individual getIndividual(double firstValue, double secondValue) {
		Individual result = mock(Individual.class);
		Objectives objectives = new Objectives();
		objectives.add(firstObj, firstValue);
		objectives.add(secondObj, secondValue);
		when(result.getObjectives()).thenReturn(objectives);
		return result;
	}

	@Before
	public void init() {
		nonDominated = new ArrayList<>();
		firstIndi = getIndividual(1.0, 5.0);
		secondIndi = getIndividual(1.1, 3.0);
		thirdIndi = getIndividual(3.0, 1.1);
		fourthIndi = getIndividual(5.0, 1.0);
		nonDominated.add(firstIndi);
		nonDominated.add(fourthIndi);
		nonDominated.add(secondIndi);
		nonDominated.add(thirdIndi);

		rand = mock(Random.class);
		when(rand.nextInt(anyInt())).thenReturn(0);

		adaptation = mock(EpsilonAdaptation.class);
		mapping = new EpsilonMappingAdditive();
	}

	@Test
	public void testGetSurvivors() {
		Set<Individual> extremes = new HashSet<>();
		Individual firstExtreme = getIndividual(0.0, 6.0);
		Individual secondExtreme = getIndividual(6.0, 0.0);
		Individual dominated = getIndividual(1.0, 1.0);
		extremes.add(firstExtreme);
		extremes.add(secondExtreme);
		Set<Individual> population = new HashSet<>(nonDominated);
		population.addAll(extremes);
		population.add(dominated);
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(rand, mapping,
				adaptation, .2, .0, .0, .0);
		Set<Individual> survivors = survivorGeneration.getSurvivors(population, 3);
		assertEquals(3, survivors.size());
		assertTrue(survivors.contains(firstExtreme));
		assertTrue(survivors.contains(secondExtreme));
		assertFalse(survivors.contains(dominated));

		survivors = survivorGeneration.getSurvivors(population, 7);
		assertEquals(7, survivors.size());
		assertTrue(survivors.contains(firstExtreme));
		assertTrue(survivors.contains(secondExtreme));
		assertTrue(survivors.contains(dominated));
	}

	@Test
	public void addNonDominatedSurvivorsTestTooFewSurvivors() {
		Set<Individual> extremes = new HashSet<>();
		Individual firstExtreme = getIndividual(0.0, 6.0);
		Individual secondExtreme = getIndividual(6.0, 0.0);
		// make sure we always have epsilon-dominance
		Individual addition = getIndividual(1.2, 2.9);
		extremes.add(firstExtreme);
		extremes.add(secondExtreme);
		Set<Individual> firstFront = new HashSet<>(nonDominated);
		firstFront.addAll(extremes);
		firstFront.add(addition);
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(rand, mapping,
				adaptation, .2, .0, .0, .0);
		Set<Individual> survivors = survivorGeneration.addNonDominatedSurvivors(extremes, firstFront, 7);
		assertEquals(7, survivors.size());
		assertTrue(survivors.contains(firstExtreme));
		assertTrue(survivors.contains(secondExtreme));
		verify(adaptation).adaptEpsilon(survivorGeneration.adaptiveEpsilonSampling, true);
	}

	@Test
	public void addNonDominatedSurvivorsTestTooManySurvivors() {
		Set<Individual> extremes = new HashSet<>();
		Individual firstExtreme = getIndividual(0.0, 6.0);
		Individual secondExtreme = getIndividual(6.0, 0.0);
		extremes.add(firstExtreme);
		extremes.add(secondExtreme);
		Set<Individual> firstFront = new HashSet<>(nonDominated);
		firstFront.addAll(extremes);
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(rand, mapping,
				adaptation, .2, .0, .0, .0);
		Set<Individual> survivors = survivorGeneration.addNonDominatedSurvivors(extremes, firstFront, 3);
		assertEquals(3, survivors.size());
		assertTrue(survivors.contains(firstExtreme));
		assertTrue(survivors.contains(secondExtreme));
		verify(adaptation).adaptEpsilon(survivorGeneration.adaptiveEpsilonSampling, false);
	}

	@Test
	public void testApplyEpsilonSampling() {
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(rand, mapping,
				adaptation, .2, .0, .0, .0);
		Set<Individual> dominant = new HashSet<>();
		Set<Individual> dominated = new HashSet<>();
		survivorGeneration.applyEpsilonSampling(nonDominated, dominant, dominated, 0.05);
		assertEquals(2, dominant.size());
		assertEquals(2, dominated.size());
		assertTrue(dominant.contains(firstIndi));
		assertTrue(dominant.contains(fourthIndi));
		assertTrue(dominated.contains(secondIndi));
		assertTrue(dominated.contains(thirdIndi));
	}

}
