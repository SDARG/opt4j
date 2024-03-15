package org.opt4j.optimizers.ea.aeseh;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdaptiveEpsilonTest {

	@Test
	public void testWrongInput2() {
		assertThrows(IllegalArgumentException.class, () -> {
			new AdaptiveEpsilon(0.1, -.1, 0.4, 0.0);
		});
	}

	@Test
	public void testWrongInput() {
		assertThrows(IllegalArgumentException.class, () -> {
			new AdaptiveEpsilon(0.1, 0.5, 0.4, 0.0);
		});
	}

	@Test
	public void test() {
		AdaptiveEpsilon adaptEps = new AdaptiveEpsilon(0.1, 0.2, 0.4, 0.0);
		Assertions.assertEquals(.1, adaptEps.getEpsilon(), 0.0);
		Assertions.assertEquals(.2, adaptEps.getEpsilonDelta(), 0.0);
		Assertions.assertEquals(.4, adaptEps.getEpsilonDeltaMax(), 0.0);
		Assertions.assertEquals(.0, adaptEps.getEpsilonDeltaMin(), 0.0);
		adaptEps.setEpsilon(.5);
		Assertions.assertEquals(.5, adaptEps.getEpsilon(), 0.0);
		adaptEps.setEpsilonDelta(.3);
		Assertions.assertEquals(.3, adaptEps.getEpsilonDelta(), 0.0);
		adaptEps.setEpsilonDelta(.5);
		Assertions.assertEquals(.4, adaptEps.getEpsilonDelta(), 0.0);
		adaptEps.setEpsilonDelta(-.1);
		Assertions.assertEquals(.0, adaptEps.getEpsilonDelta(), 0.0);
	}
}
