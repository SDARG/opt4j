package org.opt4j.core.genotype;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PermutationGenotypeTest {

	@Test
	public void testEmptyConstructor() {
		Random rand = new Random();
		PermutationGenotype<Integer> permutationGenotype = new PermutationGenotype<>();
		permutationGenotype.add(1);
		permutationGenotype.add(2);
		permutationGenotype.add(3);

		Assertions.assertEquals(3, permutationGenotype.size());
		permutationGenotype.init(rand);
		Assertions.assertEquals(3, permutationGenotype.size());
		Assertions.assertTrue(permutationGenotype.contains(1));
		Assertions.assertTrue(permutationGenotype.contains(2));
		Assertions.assertTrue(permutationGenotype.contains(3));

		PermutationGenotype<Integer> other = permutationGenotype.newInstance();
		Assertions.assertNotEquals(permutationGenotype, other);
		Assertions.assertTrue(other.isEmpty());
	}

	@Test
	public void testCollectionConstructor() {
		Random rand = new Random();
		Set<Integer> inputCollection = new HashSet<>();
		inputCollection.add(1);
		inputCollection.add(2);
		inputCollection.add(3);
		PermutationGenotype<Integer> permutationGenotype = new PermutationGenotype<>(inputCollection);

		Assertions.assertEquals(3, permutationGenotype.size());
		permutationGenotype.init(rand);
		Assertions.assertEquals(3, permutationGenotype.size());
		Assertions.assertTrue(permutationGenotype.contains(1));
		Assertions.assertTrue(permutationGenotype.contains(2));
		Assertions.assertTrue(permutationGenotype.contains(3));

		PermutationGenotype<Integer> other = permutationGenotype.newInstance();
		Assertions.assertNotEquals(permutationGenotype, other);
		Assertions.assertTrue(other.isEmpty());
	}

}
