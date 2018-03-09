package org.opt4j.optimizers.ea;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

import static org.mockito.Mockito.*;

public class NonDominatedSortingTest {

	protected static final Objective firstObj = new Objective("first", Sign.MAX);
	protected static final Objective secondObj = new Objective("second", Sign.MAX);

	protected static Individual first = mock(Individual.class);
	protected static Individual second = mock(Individual.class);
	protected static Individual third = mock(Individual.class);
	protected static Individual fourth = mock(Individual.class);
	protected static Individual fifth = mock(Individual.class);

	protected static Set<Individual> getIndividualSet() {
		Set<Individual> result = new HashSet<Individual>();

		when(first.getObjectives()).thenReturn(getObjectives(5, 1));
		when(second.getObjectives()).thenReturn(getObjectives(3, 3));
		when(third.getObjectives()).thenReturn(getObjectives(1, 4));
		when(fourth.getObjectives()).thenReturn(getObjectives(3, 2));
		when(fifth.getObjectives()).thenReturn(getObjectives(1, 1));
		
		when(first.toString()).thenReturn("first");
		when(second.toString()).thenReturn("second");
		when(third.toString()).thenReturn("third");
		when(fourth.toString()).thenReturn("fourth");
		when(fifth.toString()).thenReturn("fifth");

		result.add(first);
		result.add(second);
		result.add(third);
		result.add(fourth);
		result.add(fifth);

		return result;
	}

	protected static Objectives getObjectives(int f, int s) {
		Objectives result = new Objectives();
		result.add(firstObj, f);
		result.add(secondObj, s);
		return result;
	}

	@Test
	public void testGetExtremeIndividuals() {
		Set<Individual> extremes = NonDominatedSorting
				.getExtremeIndividuals(NonDominatedSorting.generateFronts(getIndividualSet()).get(0));
		assertEquals(2, extremes.size());
		assertTrue(extremes.contains(first));
		assertTrue(extremes.contains(third));
	}

	@Test
	public void testGenerateFronts() {
		List<List<Individual>> fronts = NonDominatedSorting.generateFronts(getIndividualSet());
		assertEquals(3, fronts.size());

		assertEquals(3, fronts.get(0).size());
		assertTrue(fronts.get(0).contains(first));
		assertTrue(fronts.get(0).contains(second));
		assertTrue(fronts.get(0).contains(third));

		assertEquals(1, fronts.get(1).size());
		assertTrue(fronts.get(1).contains(fourth));

		assertEquals(1, fronts.get(2).size());
		assertTrue(fronts.get(2).contains(fifth));
	}

}
