package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.optimizers.ea.NonDominatedFronts;

public class ESamplingSurvivorGenerationBasicTest1 {

	protected static Individual extremeIndividual = mock(Individual.class);

	protected static Objectives dominant = mock(Objectives.class);
	protected static Objectives dominated = mock(Objectives.class);

	protected static Individual first = mock(Individual.class);
	protected static Individual second = mock(Individual.class);
	protected static Individual third = mock(Individual.class);
	protected static Individual fourth = mock(Individual.class);
	protected static Individual fifth = mock(Individual.class);

	protected static final Objective firstObj = new Objective("first", Sign.MAX);
	protected static final Objective secondObj = new Objective("second", Sign.MAX);

	protected static Objectives getObjectives(int f, int s) {
		Objectives result = new Objectives();
		result.add(firstObj, f);
		result.add(secondObj, s);
		return result;
	}

	public static List<Individual> getFirstFront() {
		List<Individual> result = new ArrayList<>();
		result.add(extremeIndividual);

		when(dominant.dominates(dominated)).thenReturn(true);
		when(dominant.dominates(dominant)).thenReturn(false);
		when(dominated.dominates(dominated)).thenReturn(false);
		when(dominated.dominates(dominated)).thenReturn(false);

		when(first.getObjectives()).thenReturn(dominant);
		when(second.getObjectives()).thenReturn(dominant);
		when(third.getObjectives()).thenReturn(dominated);
		when(fourth.getObjectives()).thenReturn(dominated);
		when(fifth.getObjectives()).thenReturn(dominated);

		result.add(first);
		result.add(second);
		result.add(third);
		result.add(fourth);
		result.add(fifth);
		return result;
	}

	public static List<List<Individual>> getFronts() {
		Individual first = mock(Individual.class);
		Individual second = mock(Individual.class);
		Individual third = mock(Individual.class);
		Individual fourth = mock(Individual.class);
		Individual fifth = mock(Individual.class);
		Individual sixth = mock(Individual.class);

		List<List<Individual>> fronts = new ArrayList<>();
		List<Individual> firstFront = new ArrayList<>();
		firstFront.add(first);
		firstFront.add(second);
		firstFront.add(third);
		List<Individual> secondFront = new ArrayList<>();
		secondFront.add(fourth);
		List<Individual> thirdFront = new ArrayList<>();
		thirdFront.add(fifth);
		thirdFront.add(sixth);
		fronts.add(firstFront);
		fronts.add(secondFront);
		fronts.add(thirdFront);
		return fronts;
	}

	@Test
	public void testAddDominatedSurvivors() {
		Random mockRandom = mock(Random.class);
		when(mockRandom.nextInt(2)).thenReturn(0);
		EpsilonAdaptation mockAdaption = mock(EpsilonAdaptationDelta.class);
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(mockRandom,
				new EpsilonMappingAdditive(), mockAdaption, 0.0, .0, .0, .0);
		List<List<Individual>> frontsMock = getFronts();
		NonDominatedFronts mockFronts = mock(NonDominatedFronts.class);
		when(mockFronts.getFrontNumber()).thenReturn(3);
		when(mockFronts.getFrontAtIndex(0)).thenReturn(frontsMock.get(0));
		when(mockFronts.getFrontAtIndex(1)).thenReturn(frontsMock.get(1));
		when(mockFronts.getFrontAtIndex(2)).thenReturn(frontsMock.get(2));
		Set<Individual> survivors = survivorGeneration.addDominatedSurvivors(5, mockFronts);
		assertEquals(5, survivors.size());
		assertTrue(survivors.containsAll(frontsMock.get(0)));
		assertTrue(survivors.containsAll(frontsMock.get(1)));
		verify(mockRandom).nextInt(2);

	}

	@Test
	public void testGetExtremeInividuals() {
		Set<Individual> indis = new HashSet<>();

		when(first.getObjectives()).thenReturn(getObjectives(5, 1));
		when(second.getObjectives()).thenReturn(getObjectives(3, 3));
		when(third.getObjectives()).thenReturn(getObjectives(1, 4));
		when(fourth.getObjectives()).thenReturn(getObjectives(3, 2));
		when(fifth.getObjectives()).thenReturn(getObjectives(1, 1));

		Objectives insfeasibleObjectives = new Objectives();
		insfeasibleObjectives.add(firstObj, Objective.INFEASIBLE);
		insfeasibleObjectives.add(secondObj, Objective.INFEASIBLE);

		when(first.toString()).thenReturn("first");
		when(second.toString()).thenReturn("second");
		when(third.toString()).thenReturn("third");
		when(fourth.toString()).thenReturn("fourth");
		when(fifth.toString()).thenReturn("fifth");

		indis.add(first);
		indis.add(second);
		indis.add(third);
		indis.add(fourth);
		indis.add(fifth);

		Random mockRandom = mock(Random.class);
		when(mockRandom.nextInt(2)).thenReturn(0);
		EpsilonAdaptation mockAdaption = mock(EpsilonAdaptationDelta.class);
		ESamplingSurvivorGenerationBasic survivorGeneration = new ESamplingSurvivorGenerationBasic(mockRandom,
				new EpsilonMappingAdditive(), mockAdaption, 0.0, .0, .0, .0);

		Collection<Individual> extremes = survivorGeneration.getExtremeIndividuals(indis);
		assertEquals(2, extremes.size());
		assertTrue(extremes.contains(first));
		assertTrue(extremes.contains(third));
	}

}
