package org.opt4j.common.random;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.common.random.RandomJava;
import org.opt4j.core.common.random.RandomMersenneTwister;

public class RandomTest {

	protected static final int SAMPLES = 10000;

	@Test
	public void testConsistencyRandomMersenneTwister() {
		RandomMersenneTwister r0 = new RandomMersenneTwister(123);
		RandomMersenneTwister r1 = new RandomMersenneTwister(123);
		testConsistency(r0, r1);
	}

	@Test
	public void testConsistencyRandomJava() {
		RandomJava r0 = new RandomJava(123);
		RandomJava r1 = new RandomJava(123);
		testConsistency(r0, r1);
	}

	public void testConsistency(Rand r0, Rand r1) {
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextDouble(), r1.nextDouble(), 0.0);
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextBoolean(), r1.nextBoolean());
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextInt(), r1.nextInt());
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextInt(100), r1.nextInt(100));
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextFloat(), r1.nextFloat(), 0.0);
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextLong(), r1.nextLong());
		}
		for (int i = 0; i < SAMPLES; i++) {
			assertEquals(r0.nextGaussian(), r1.nextGaussian(), 0.0);
		}
	}
}
