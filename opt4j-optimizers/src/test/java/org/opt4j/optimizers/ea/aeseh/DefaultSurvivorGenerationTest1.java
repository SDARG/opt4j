package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.optimizers.ea.aeseh.AdditiveEpsilonMapping;
import org.opt4j.optimizers.ea.aeseh.DefaultEpsilonAdaptation;
import org.opt4j.optimizers.ea.aeseh.DefaultSurvivorGeneration;
import org.opt4j.optimizers.ea.aeseh.EpsilonAdaption;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DefaultSurvivorGenerationTest1 {

	protected static Individual extremeIndividual = mock(Individual.class);

	protected static Objectives dominant = mock(Objectives.class);
	protected static Objectives dominated = mock(Objectives.class);
	
	protected static Individual first = mock(Individual.class);
	protected static Individual second = mock(Individual.class);
	protected static Individual third = mock(Individual.class);
	protected static Individual fourth = mock(Individual.class);
	protected static Individual fifth = mock(Individual.class);

	public static List<Individual> getFirstFront() {
		List<Individual> result = new ArrayList<Individual>();
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

		List<List<Individual>> fronts = new ArrayList<List<Individual>>();
		List<Individual> firstFront = new ArrayList<Individual>();
		firstFront.add(first);
		firstFront.add(second);
		firstFront.add(third);
		List<Individual> secondFront = new ArrayList<Individual>();
		secondFront.add(fourth);
		List<Individual> thirdFront = new ArrayList<Individual>();
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
		EpsilonAdaption mockAdaption = mock(DefaultEpsilonAdaptation.class);
		DefaultSurvivorGeneration survivorGeneration = new DefaultSurvivorGeneration(mockRandom,
				new AdditiveEpsilonMapping(), mockAdaption);
		List<List<Individual>> fronts = getFronts();
		Set<Individual> survivors = survivorGeneration.addDominatedSurvivors(5, fronts);
		assertEquals(5, survivors.size());
		assertTrue(survivors.containsAll(fronts.get(0)));
		assertTrue(survivors.containsAll(fronts.get(1)));
		verify(mockRandom).nextInt(2);

	}

}
