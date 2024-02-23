package org.opt4j.core.genotype;


import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BooleanGenotypeTest {

	@Test
	public void test() {
		Random random = new Random();
		BooleanGenotype genotype1 = new BooleanGenotype();
		genotype1.init(random, 10);
		Assertions.assertEquals(10, genotype1.size());
		genotype1.init(random, 15);
		Assertions.assertEquals(15, genotype1.size());
		BooleanGenotype genotype2 = genotype1.newInstance();
		Assertions.assertTrue(genotype2.isEmpty());
	}
}
