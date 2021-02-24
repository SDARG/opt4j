package org.opt4j.optimizers.ea;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

public class NonDominatedFrontsTest {

	protected static final Objective firstObj = new Objective("first", Sign.MAX);
	protected static final Objective secondObj = new Objective("second", Sign.MAX);

	protected static Individual first = mock(Individual.class);
	protected static Individual second = mock(Individual.class);
	protected static Individual third = mock(Individual.class);
	protected static Individual fourth = mock(Individual.class);
	protected static Individual fifth = mock(Individual.class);
	protected static Individual infeasible = mock(Individual.class);

	protected static Set<Individual> getIndividualSet() {
		Set<Individual> result = new HashSet<>();

		when(first.dominates(fourth)).thenReturn(true);
		when(first.dominates(fifth)).thenReturn(true);
		when(first.dominates(infeasible)).thenReturn(true);
		when(first.dominates(second)).thenReturn(false);
		when(first.dominates(third)).thenReturn(false);

		when(second.dominates(fourth)).thenReturn(true);
		when(second.dominates(fifth)).thenReturn(true);
		when(second.dominates(infeasible)).thenReturn(true);
		when(second.dominates(first)).thenReturn(false);
		when(second.dominates(third)).thenReturn(false);

		when(third.dominates(fourth)).thenReturn(false);
		when(third.dominates(fifth)).thenReturn(true);
		when(third.dominates(infeasible)).thenReturn(true);
		when(third.dominates(second)).thenReturn(false);
		when(third.dominates(first)).thenReturn(false);

		when(fourth.dominates(first)).thenReturn(false);
		when(fourth.dominates(second)).thenReturn(false);
		when(fourth.dominates(third)).thenReturn(false);
		when(fourth.dominates(fifth)).thenReturn(true);
		when(fourth.dominates(infeasible)).thenReturn(true);

		when(fifth.dominates(first)).thenReturn(false);
		when(fifth.dominates(second)).thenReturn(false);
		when(fifth.dominates(third)).thenReturn(false);
		when(fifth.dominates(fourth)).thenReturn(false);
		when(fifth.dominates(infeasible)).thenReturn(true);
		
		when(infeasible.dominates(first)).thenReturn(false);
		when(infeasible.dominates(second)).thenReturn(false);
		when(infeasible.dominates(third)).thenReturn(false);
		when(infeasible.dominates(fourth)).thenReturn(false);
		when(infeasible.dominates(fifth)).thenReturn(false);

		when(first.toString()).thenReturn("first");
		when(second.toString()).thenReturn("second");
		when(third.toString()).thenReturn("third");
		when(fourth.toString()).thenReturn("fourth");
		when(fifth.toString()).thenReturn("fifth");
		when(infeasible.toString()).thenReturn("infeasible");

		result.add(first);
		result.add(second);
		result.add(third);
		result.add(fourth);
		result.add(fifth);
		result.add(infeasible);

		return result;
	}

	protected static Objectives getObjectives(int f, int s) {
		Objectives result = new Objectives();
		result.add(firstObj, f);
		result.add(secondObj, s);
		return result;
	}

	@Test
	public void testGenerateFronts() {
		NonDominatedFronts fronts = new NonDominatedFronts(getIndividualSet());
		assertEquals(4, fronts.getFrontNumber());

		assertEquals(3, fronts.getFrontAtIndex(0).size());
		assertTrue(fronts.getFrontAtIndex(0).contains(first));
		assertTrue(fronts.getFrontAtIndex(0).contains(second));
		assertTrue(fronts.getFrontAtIndex(0).contains(third));

		assertEquals(1, fronts.getFrontAtIndex(1).size());
		assertTrue(fronts.getFrontAtIndex(1).contains(fourth));

		assertEquals(1, fronts.getFrontAtIndex(2).size());
		assertTrue(fronts.getFrontAtIndex(2).contains(fifth));
	}
}
