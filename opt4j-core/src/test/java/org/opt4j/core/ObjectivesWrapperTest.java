package org.opt4j.core;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opt4j.core.Individual.State;

import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

import java.util.Iterator;
import java.util.Map.Entry;

public class ObjectivesWrapperTest {

	/**
	 * Tests that the list is initially empty, is set after an individual is
	 * evaluated, and is then returned upon request.
	 */
	@Test
	public void testNormal() {
		IndividualFactory factoryMock = mock(IndividualFactory.class);
		ObjectivesWrapper tested = new ObjectivesWrapper(factoryMock);
		assertTrue(tested.optimizationObjectives.isEmpty());

		Individual decodedMock = mock(Individual.class);
		when(decodedMock.getState()).thenReturn(State.PHENOTYPED);
		tested.individualStateChanged(decodedMock);
		assertTrue(tested.optimizationObjectives.isEmpty());

		Individual evaluatedMock = mock(Individual.class);
		when(evaluatedMock.getState()).thenReturn(State.EVALUATED);
		Objectives objs = new Objectives();
		Objective obj1 = new Objective("obj1");
		Objective obj2 = new Objective("obj2");
		objs.add(obj1, 1);
		objs.add(obj2, 2);
		when(evaluatedMock.getObjectives()).thenReturn(objs);
		tested.individualStateChanged(evaluatedMock);
		assertTrue(tested.optimizationObjectives.size() == 2);
		Iterator<Entry<Objective, Value<?>>> iterator = objs.iterator();
		assertEquals(iterator.next().getKey(), tested.getOptimizationObjectives().get(0));
		assertEquals(iterator.next().getKey(), tested.getOptimizationObjectives().get(1));
	}

	/**
	 * Tests that an exception is thrown if the objectives are requested before the
	 * first individual evaluation.
	 */
	@Test(expected = IllegalStateException.class)
	public void testNotYetInit() {
		IndividualFactory factoryMock = mock(IndividualFactory.class);
		ObjectivesWrapper tested = new ObjectivesWrapper(factoryMock);
		assertTrue(tested.optimizationObjectives.isEmpty());

		Individual decodedMock = mock(Individual.class);
		when(decodedMock.getState()).thenReturn(State.PHENOTYPED);
		tested.individualStateChanged(decodedMock);
		assertTrue(tested.optimizationObjectives.isEmpty());
		tested.getOptimizationObjectives();
	}
}
