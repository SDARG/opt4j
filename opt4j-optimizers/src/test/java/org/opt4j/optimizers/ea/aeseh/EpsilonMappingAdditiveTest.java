package org.opt4j.optimizers.ea.aeseh;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;

public class EpsilonMappingAdditiveTest {

	protected static final Objective first = new Objective("first", Sign.MAX);
	protected static final Objective second = new Objective("second", Sign.MIN);

	protected static Set<Individual> getIndividualSet() {
		Objectives firstObj = new Objectives();
		firstObj.add(first, 4);
		firstObj.add(second, 3);

		Objectives secondObj = new Objectives();
		secondObj.add(first, 1);
		secondObj.add(second, 1);

		Objectives thirdObj = new Objectives();
		thirdObj.add(first, 2);
		thirdObj.add(second, 2);

		Objectives infeasibleObj = new Objectives();
		infeasibleObj.add(first, Objective.INFEASIBLE);
		infeasibleObj.add(second, Objective.INFEASIBLE);

		Individual first = mock(Individual.class);
		when(first.getObjectives()).thenReturn(firstObj);
		Individual second = mock(Individual.class);
		when(second.getObjectives()).thenReturn(secondObj);
		Individual third = mock(Individual.class);
		when(third.getObjectives()).thenReturn(thirdObj);
		Individual infeasible = mock(Individual.class);
		when(infeasible.getObjectives()).thenReturn(infeasibleObj);

		Set<Individual> indis = new HashSet<>();
		indis.add(first);
		indis.add(second);
		indis.add(third);
		indis.add(infeasible);
		return indis;
	}

	@Test
	public void testMapObjectives() {
		Set<Individual> indis = getIndividualSet();
		EpsilonMappingAdditive epsilonMapping = new EpsilonMappingAdditive();
		Map<Objective, Double> amplitudeMap = epsilonMapping.findObjectiveAmplitudes(indis);

		Objectives objectives = new Objectives();
		objectives.add(first, 0);
		objectives.add(second, 0);
		double epsilon = 0.5;

		Objectives epsilonEnhanced = epsilonMapping.mapObjectives(objectives, epsilon, amplitudeMap);
		Assertions.assertEquals(1.5, epsilonEnhanced.get(first).getDouble(), 0.0);
		Assertions.assertEquals(-1.0, epsilonEnhanced.get(second).getDouble(), 0.0);
	}

	@Test
	public void testFindAmplitudes() {
		Set<Individual> indis = getIndividualSet();
		EpsilonMappingAdditive epsilonMapping = new EpsilonMappingAdditive();
		Map<Objective, Double> amplitudeMap = epsilonMapping.findObjectiveAmplitudes(indis);
		Assertions.assertTrue(amplitudeMap.containsKey(first));
		Assertions.assertTrue(amplitudeMap.containsKey(second));
		Assertions.assertEquals(3.0, amplitudeMap.get(first), 0.0);
		Assertions.assertEquals(2.0, amplitudeMap.get(second), 0.0);
	}

}
