package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class PermutationGenotypeTest {

	@Test
	public void testEmptyConstructor() {
		Random rand = new Random();
		PermutationGenotype<Integer> permutationGenotype = new PermutationGenotype<>();
		permutationGenotype.add(1);
		permutationGenotype.add(2);
		permutationGenotype.add(3);

		assertEquals(3, permutationGenotype.size());
		permutationGenotype.init(rand);
		assertEquals(3, permutationGenotype.size());
		assertTrue(permutationGenotype.contains(1));
		assertTrue(permutationGenotype.contains(2));
		assertTrue(permutationGenotype.contains(3));

		PermutationGenotype<Integer> other = permutationGenotype.newInstance();
		assertNotEquals(permutationGenotype, other);
		assertTrue(other.isEmpty());
	}

	@Test
	public void testCollectionConstructor() {
		Random rand = new Random();
		Set<Integer> inputCollection = new HashSet<>();
		inputCollection.add(1);
		inputCollection.add(2);
		inputCollection.add(3);
		PermutationGenotype<Integer> permutationGenotype = new PermutationGenotype<>(inputCollection);

		assertEquals(3, permutationGenotype.size());
		permutationGenotype.init(rand);
		assertEquals(3, permutationGenotype.size());
		assertTrue(permutationGenotype.contains(1));
		assertTrue(permutationGenotype.contains(2));
		assertTrue(permutationGenotype.contains(3));

		PermutationGenotype<Integer> other = permutationGenotype.newInstance();
		assertNotEquals(permutationGenotype, other);
		assertTrue(other.isEmpty());
	}

}
