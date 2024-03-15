package org.opt4j.optimizers.ea;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

		when(first.getObjectives()).thenReturn(getObjectives(5, 1));
		when(second.getObjectives()).thenReturn(getObjectives(3, 3));
		when(third.getObjectives()).thenReturn(getObjectives(1, 4));
		when(fourth.getObjectives()).thenReturn(getObjectives(3, 2));
		when(fifth.getObjectives()).thenReturn(getObjectives(1, 1));

		Objectives insfeasibleObjectives = new Objectives();
		insfeasibleObjectives.add(firstObj, Objective.INFEASIBLE);
		insfeasibleObjectives.add(secondObj, Objective.INFEASIBLE);
		when(infeasible.getObjectives()).thenReturn(insfeasibleObjectives);

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
		Assertions.assertEquals(4, fronts.getFrontNumber());

		Assertions.assertEquals(3, fronts.getFrontAtIndex(0).size());
		Assertions.assertTrue(fronts.getFrontAtIndex(0).contains(first));
		Assertions.assertTrue(fronts.getFrontAtIndex(0).contains(second));
		Assertions.assertTrue(fronts.getFrontAtIndex(0).contains(third));

		Assertions.assertEquals(1, fronts.getFrontAtIndex(1).size());
		Assertions.assertTrue(fronts.getFrontAtIndex(1).contains(fourth));

		Assertions.assertEquals(1, fronts.getFrontAtIndex(2).size());
		Assertions.assertTrue(fronts.getFrontAtIndex(2).contains(fifth));
	}
}
