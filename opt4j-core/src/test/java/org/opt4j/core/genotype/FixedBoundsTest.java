package org.opt4j.core.genotype;


import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class FixedBoundsTest {

	@Test
	public void test() {
		Random rand = new Random();
		FixedBounds<Integer> fixedInt = new FixedBounds<>(2, 10);
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			Assertions.assertEquals(Long.valueOf(2), Long.valueOf(fixedInt.getLowerBound(index)));
			Assertions.assertEquals(Long.valueOf(10), Long.valueOf(fixedInt.getUpperBound(index)));
		}
		FixedBounds<Double> fixedDouble = new FixedBounds<>(.5, 11.7);
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			Assertions.assertEquals(.5, fixedDouble.getLowerBound(index), 0.0);
			Assertions.assertEquals(11.7, fixedDouble.getUpperBound(index), 0.0);
		}
	}
}
