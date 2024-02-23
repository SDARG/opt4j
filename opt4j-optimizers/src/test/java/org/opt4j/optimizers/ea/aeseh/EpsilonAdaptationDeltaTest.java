package org.opt4j.optimizers.ea.aeseh;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.optimizers.ea.aeseh.EpsilonAdaptationDelta;

public class EpsilonAdaptationDeltaTest {

	protected double eps_start = 0.0;
	protected double eps_delta_start = 0.5;
	protected double eps_delta_max = 1.5;
	protected double eps_delta_min = 0.4;

	@Test
	public void testEpsilonSample() {
		EpsilonAdaptationDelta adaptation = new EpsilonAdaptationDelta();
		AdaptiveEpsilon adaptiveEpsilon = new AdaptiveEpsilon(eps_start, eps_delta_start, eps_delta_max, eps_delta_min);
		adaptation.adaptEpsilon(adaptiveEpsilon, false);
		Assertions.assertEquals(0.5, adaptiveEpsilon.getEpsilon(), 0.0);
		Assertions.assertEquals(1.0, adaptiveEpsilon.getEpsilonDelta(), 0.0);
		adaptation.adaptEpsilon(adaptiveEpsilon, false);
		Assertions.assertEquals(1.5, adaptiveEpsilon.getEpsilonDelta(), 0.0);
		Assertions.assertEquals(1.5, adaptiveEpsilon.getEpsilon(), 0.0);
		adaptation.adaptEpsilon(adaptiveEpsilon, true);
		Assertions.assertEquals(0.0, adaptiveEpsilon.getEpsilon(), 0.0);
		Assertions.assertEquals(0.75, adaptiveEpsilon.epsilonDelta, 0.0);
		adaptation.adaptEpsilon(adaptiveEpsilon, true);
		Assertions.assertEquals(-.75, adaptiveEpsilon.getEpsilon(), 0.0);
		Assertions.assertEquals(0.4, adaptiveEpsilon.epsilonDelta, 0.0);
	}
}
