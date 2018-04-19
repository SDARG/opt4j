package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.*;

import org.junit.Test;
import org.opt4j.optimizers.ea.aeseh.EpsilonAdaptationDefault;

public class EpsilonAdaptationDefaultTest {

	protected double eps_start = 0.0;
	protected double eps_delta_start = 0.5;
	protected double eps_delta_max = 1.5;
	protected double eps_delta_min = 0.4;

	@Test
	public void testEpsilonNeighborhood() {
		EpsilonAdaptationDefault adaptation = new EpsilonAdaptationDefault(0, 0, 0, 0, eps_start, eps_delta_start,
				eps_delta_max, eps_delta_min);
		assertEquals(eps_start, adaptation.getNeighborhoodEpsilon(), 0.0);
		adaptation.adaptNeighborhoodEpsilon(true);
		assertEquals(0.5, adaptation.getNeighborhoodEpsilon(), 0.0);
		adaptation.adaptNeighborhoodEpsilon(true);
		assertEquals(1.5, adaptation.epsilonNeighborhoodDelta, 0.0);
		assertEquals(1.5, adaptation.getNeighborhoodEpsilon(), 0.0);
		adaptation.adaptNeighborhoodEpsilon(false);
		assertEquals(0.0, adaptation.getNeighborhoodEpsilon(), 0.0);
		assertEquals(0.75, adaptation.epsilonNeighborhoodDelta, 0.0);
		adaptation.adaptNeighborhoodEpsilon(false);
		assertEquals(0.0, adaptation.getNeighborhoodEpsilon(), 0.0);
		assertEquals(0.4, adaptation.epsilonNeighborhoodDelta, 0.0);
	}

	@Test
	public void testEpsilonSample() {
		EpsilonAdaptationDefault adaptation = new EpsilonAdaptationDefault(eps_start, eps_delta_start, eps_delta_max,
				eps_delta_min, 0, 0, 0, 0);
		assertEquals(eps_start, adaptation.getSamplingEpsilon(), 0.0);
		adaptation.adaptSamplingEpsilon(true);
		assertEquals(0.5, adaptation.getSamplingEpsilon(), 0.0);
		adaptation.adaptSamplingEpsilon(true);
		assertEquals(1.5, adaptation.epsilonSampleDelta, 0.0);
		assertEquals(1.5, adaptation.getSamplingEpsilon(), 0.0);
		adaptation.adaptSamplingEpsilon(false);
		assertEquals(0.0, adaptation.getSamplingEpsilon(), 0.0);
		assertEquals(0.75, adaptation.epsilonSampleDelta, 0.0);
		adaptation.adaptSamplingEpsilon(false);
		assertEquals(0.0, adaptation.getSamplingEpsilon(), 0.0);
		assertEquals(0.4, adaptation.epsilonSampleDelta, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEpsilonNeighborhoodWrongBoundsMax() {
		new EpsilonAdaptationDefault(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.9, 0.8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEpsilonNeighborhoodWrongBoundsMin() {
		new EpsilonAdaptationDefault(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 2.0, 1.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEpsilonSampleWrongBoundsMin() {
		new EpsilonAdaptationDefault(0.0, 1.0, 2.0, 1.1, 0.0, 0.0, 0.0, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEpsilonSampleWrongBoundsMax() {
		new EpsilonAdaptationDefault(0.0, 1.0, 0.5, 0.4, 0.0, 0.0, 0.0, 0.0);
	}

}
