package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

public class FixedBoundsTest {

	@Test
	public void test() {
		Random rand = new Random();
		FixedBounds<Integer> fixedInt = new FixedBounds<>(2, 10);
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			assertEquals(new Long(2), new Long(fixedInt.getLowerBound(index)));
			assertEquals(new Long(10), new Long(fixedInt.getUpperBound(index)));
		}
		FixedBounds<Double> fixedDouble = new FixedBounds<>(.5, 11.7);
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			assertEquals(.5, fixedDouble.getLowerBound(index), 0.0);
			assertEquals(11.7, fixedDouble.getUpperBound(index), 0.0);
		}
	}
}
