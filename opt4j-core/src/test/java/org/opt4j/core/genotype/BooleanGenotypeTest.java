package org.opt4j.core.genotype;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class BooleanGenotypeTest {

	@Test
	public void test() {
		Random random = new Random();
		BooleanGenotype genotype1 = new BooleanGenotype();
		genotype1.init(random, 10);
		assertEquals(10, genotype1.size());
		genotype1.init(random, 15);
		assertEquals(15, genotype1.size());
		BooleanGenotype genotype2 = genotype1.newInstance();
		assertTrue(genotype2.isEmpty());
	}
}
