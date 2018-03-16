package org.opt4j.optimizers.ea;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Objective.Sign;
import org.opt4j.operators.crossover.Pair;
import org.opt4j.optimizers.ea.aeseh.AdditiveEpsilonMapping;
import org.opt4j.optimizers.ea.aeseh.AeSeHCoupler;
import org.opt4j.optimizers.ea.aeseh.DefaultEpsilonAdaptation;
import org.opt4j.optimizers.ea.aeseh.EpsilonAdaption;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class AeSeHCouplerTest {

	protected static EpsilonAdaption mockAdaption = mock(DefaultEpsilonAdaptation.class);

	protected static Objective firstObj = new Objective("first", Sign.MAX);
	protected static Objective secondObj = new Objective("second", Sign.MAX);

	protected static Individual first = mock(Individual.class);
	protected static Individual second = mock(Individual.class);
	protected static Individual third = mock(Individual.class);
	protected static Individual fourth = mock(Individual.class);

	protected static List<Individual> getSurvivors() {
		List<Individual> survivors = new ArrayList<Individual>();
		when(first.getObjectives()).thenReturn(getObj(5, 1));
		survivors.add(first);
		when(second.getObjectives()).thenReturn(getObj(3, 3));
		survivors.add(second);
		when(third.getObjectives()).thenReturn(getObj(1, 4));
		survivors.add(third);
		when(fourth.getObjectives()).thenReturn(getObj(3, 3));
		survivors.add(fourth);
		return survivors;
	}

	protected static Objectives getObj(int firstValue, int secondValue) {
		Objectives result = new Objectives();
		result.add(firstObj, firstValue);
		result.add(secondObj, secondValue);
		return result;
	}

	@Test
	public void testGetCouples() {
		AeSeHCoupler coupler = new AeSeHCoupler(new AdditiveEpsilonMapping(), mockAdaption, new Random(), 2);
		Collection<Pair<Individual>> couples = coupler.getCouples(2, getSurvivors());
		assertEquals(2, couples.size());
	}

	@Test
	public void testCreateNeighborhoods() {
		AeSeHCoupler coupler = new AeSeHCoupler(new AdditiveEpsilonMapping(), mockAdaption, new Random(), 2);
		Map<Objective, Double> amplitudeMap = new HashMap<Objective, Double>();
		amplitudeMap.put(firstObj, 0.001);
		amplitudeMap.put(secondObj, 0.001);
		List<Individual> survivors = getSurvivors();
		when(mockAdaption.getNeighborhoodEpsilon()).thenReturn(0.0001);
		List<Set<Individual>> neighborhoods = coupler.createNeighborhoods(survivors);
		assertEquals(3, neighborhoods.size());
		verify(mockAdaption).adaptNeighborhoodEpsilon(true);
		coupler = new AeSeHCoupler(new AdditiveEpsilonMapping(), mockAdaption, new Random(), 5);
		neighborhoods = coupler.createNeighborhoods(survivors);
		verify(mockAdaption).adaptNeighborhoodEpsilon(false);
	}

	@Test
	public void testGetCouple() {
		AeSeHCoupler coupler = new AeSeHCoupler(new AdditiveEpsilonMapping(), mockAdaption, new Random(), 2);
		Individual first = mock(Individual.class);
		Individual second = mock(Individual.class);
		Set<Individual> indiSet = new HashSet<Individual>();
		indiSet.add(first);
		indiSet.add(second);
		Pair<Individual> couple = coupler.pickCouple(indiSet);
		assertTrue(couple.getFirst().equals(first) || couple.getSecond().equals(first));
		assertTrue(couple.getFirst().equals(second) || couple.getSecond().equals(second));
		indiSet.remove(first);
		couple = coupler.pickCouple(indiSet);
		assertTrue(couple.getFirst().equals(second));
		assertTrue(couple.getSecond().equals(second));
	}

}
