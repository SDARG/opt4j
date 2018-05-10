package org.opt4j.optimizers.ea.aeseh;

import static org.junit.Assert.*;

import org.junit.Test;

public class AdaptiveEpsilonTest {

	@Test(expected = IllegalArgumentException.class)
	public void testWrongInput2() {
		new AdaptiveEpsilon(0.1, -.1, 0.4, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongInput() {
		new AdaptiveEpsilon(0.1, 0.5, 0.4, 0.0);
	}

	@Test
	public void test() {
		AdaptiveEpsilon adaptEps = new AdaptiveEpsilon(0.1, 0.2, 0.4, 0.0);
		assertEquals(.1, adaptEps.getEpsilon(), 0.0);
		assertEquals(.2, adaptEps.getEpsilonDelta(), 0.0);
		assertEquals(.4, adaptEps.getEpsilonDeltaMax(), 0.0);
		assertEquals(.0, adaptEps.getEpsilonDeltaMin(), 0.0);
		adaptEps.setEpsilon(.5);
		assertEquals(.5, adaptEps.getEpsilon(), 0.0);
		adaptEps.setEpsilonDelta(.3);
		assertEquals(.3, adaptEps.getEpsilonDelta(), 0.0);
		adaptEps.setEpsilonDelta(.5);
		assertEquals(.4, adaptEps.getEpsilonDelta(), 0.0);
		adaptEps.setEpsilonDelta(-.1);
		assertEquals(.0, adaptEps.getEpsilonDelta(), 0.0);
	}
}
