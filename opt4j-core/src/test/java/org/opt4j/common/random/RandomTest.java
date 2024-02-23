/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package org.opt4j.common.random;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.common.random.RandomJava;
import org.opt4j.core.common.random.RandomMersenneTwister;

public class RandomTest {

	protected static final int SAMPLES = 10000;

	@Test
	public void testConsistencyRandomMersenneTwister() {
		RandomMersenneTwister r0 = new RandomMersenneTwister(123);
		RandomMersenneTwister r1 = new RandomMersenneTwister(123);
		Assertions.assertTrue(testConsistency(r0, r1));
	}

	@Test
	public void testConsistencyRandomJava() {
		RandomJava r0 = new RandomJava(123);
		RandomJava r1 = new RandomJava(123);
		Assertions.assertTrue(testConsistency(r0, r1));
	}

	public boolean testConsistency(Rand r0, Rand r1) {
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextDouble(), r1.nextDouble(), 0.0);
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextBoolean(), r1.nextBoolean());
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextInt(), r1.nextInt());
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextInt(100), r1.nextInt(100));
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextFloat(), r1.nextFloat(), 0.0);
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextLong(), r1.nextLong());
		}
		for (int i = 0; i < SAMPLES; i++) {
			Assertions.assertEquals(r0.nextGaussian(), r1.nextGaussian(), 0.0);
		}

		return true;
	}
}
