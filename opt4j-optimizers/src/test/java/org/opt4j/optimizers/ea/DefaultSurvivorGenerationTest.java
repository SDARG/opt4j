package org.opt4j.optimizers.ea;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DefaultSurvivorGenerationTest {

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyEpsilonSampling(){
		EpsilonMapping mockMapping = mock(AdditiveEpsilonMapping.class);
		EpsilonAdaption mockAdaption = mock(DefaultEpsilonAdaptation.class);
		Map<Objective, Double> amplitudeMap = new HashMap<Objective, Double>();
		when(mockMapping.findObjectiveAmplitudes(any(HashSet.class))).thenReturn(amplitudeMap);
		when(mockAdaption.getSamplingEpsilon()).thenReturn(0.0);
		when(mockMapping.mapObjectives(dominant, 0.0, amplitudeMap)).thenReturn(dominant);
		when(mockMapping.mapObjectives(dominated, 0.0, amplitudeMap)).thenReturn(dominated);
		Set<Individual> extremes = new HashSet<Individual>();
		extremes.add(extremeIndividual);
		DefaultSurvivorGeneration survivorGeneration = new DefaultSurvivorGeneration(new Random(), mockMapping, mockAdaption);
		Set<Individual> survivors = survivorGeneration.applyEpsilonSampling(extremes, getFirstFront(), 4);
		assertTrue(survivors.containsAll(extremes));
		assertTrue(survivors.contains(first));
		assertTrue(survivors.contains(second));
		verify(mockAdaption).adaptSamplingEpsilon(false);
		survivorGeneration.applyEpsilonSampling(extremes, getFirstFront(), 2);
		verify(mockAdaption).adaptSamplingEpsilon(true);
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
