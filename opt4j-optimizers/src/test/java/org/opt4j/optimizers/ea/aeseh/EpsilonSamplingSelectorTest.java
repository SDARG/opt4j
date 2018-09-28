package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

public class EpsilonSamplingSelectorTest {

	@Test
	public void testGetParents() {
		ESamplingSurvivorGeneration survivorGeneration = mock(ESamplingSurvivorGenerationBasic.class);
		EpsilonSamplingSelector selector = new EpsilonSamplingSelector(survivorGeneration);
		Individual first = mock(Individual.class);
		Individual second = mock(Individual.class);
		Individual third = mock(Individual.class);
		Population population = new Population();
		population.add(first);
		population.add(second);
		population.add(third);
		Collection<Individual> parents = selector.getParents(3, population);
		assertEquals(population, parents);
	}

	@Test
	public void testGetLames() {
		ESamplingSurvivorGeneration survivorGeneration = mock(ESamplingSurvivorGenerationBasic.class);
		EpsilonSamplingSelector selector = new EpsilonSamplingSelector(survivorGeneration);

		Individual first = mock(Individual.class);
		Individual second = mock(Individual.class);
		Individual third = mock(Individual.class);

		Set<Individual> survivors = new HashSet<>();
		survivors.add(first);
		survivors.add(second);

		Population population = new Population();
		population.add(first);
		population.add(second);
		population.add(third);

		when(survivorGeneration.getSurvivors(population, 2)).thenReturn(survivors);
		Collection<Individual> result = selector.getLames(1, population);
		verify(survivorGeneration).getSurvivors(population, 2);
		assertEquals(1, result.size());
		assertTrue(result.contains(third));
	}

}
