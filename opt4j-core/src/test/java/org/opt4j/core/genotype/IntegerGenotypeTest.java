package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class IntegerGenotypeTest {

	@Test(expected = IllegalArgumentException.class)
	public void testWrongBoundSettingList() {
		Random rand = new Random();
		List<Integer> lowerBounds = new ArrayList<>();
		lowerBounds.add(1);
		lowerBounds.add(2);
		lowerBounds.add(3);
		List<Integer> upperBounds = new ArrayList<>();
		upperBounds.add(2);
		upperBounds.add(3);
		upperBounds.add(4);
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		IntegerGenotype listBoundGeno = new IntegerGenotype(bounds);
		assertTrue(listBoundGeno.isEmpty());
		listBoundGeno.init(rand, 3);
		assertEquals(3, listBoundGeno.size());
		listBoundGeno.init(rand, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongBoundSettingArray() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		IntegerGenotype listBoundGeno = new IntegerGenotype(bounds);
		assertTrue(listBoundGeno.isEmpty());
		listBoundGeno.init(rand, 3);
		assertEquals(3, listBoundGeno.size());
		listBoundGeno.init(rand, 4);
	}

	@Test
	public void testListBoundGenotype() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		IntegerGenotype listBoundGeno = new IntegerGenotype(bounds);
		assertTrue(listBoundGeno.isEmpty());
		listBoundGeno.init(rand, 3);
		assertEquals(3, listBoundGeno.size());
		IntegerGenotype other = listBoundGeno.newInstance();
		assertNotEquals(listBoundGeno, other);
		assertTrue(other.isEmpty());
		other.init(rand, 2);
		assertTrue(listBoundGeno.get(0) <= 2);
		assertTrue(listBoundGeno.get(1) <= 3);
		assertTrue(listBoundGeno.get(2) <= 4);
		assertTrue(other.get(0) <= 2);
		assertTrue(other.get(1) <= 3);
		assertTrue(listBoundGeno.get(0) >= 1);
		assertTrue(listBoundGeno.get(1) >= 2);
		assertTrue(listBoundGeno.get(2) >= 3);
		assertTrue(other.get(0) >= 1);
		assertTrue(other.get(1) >= 2);
	}

	@Test
	public void testFixedBoundGenotype() {
		Random rand = new Random();
		int lowerBound = 2;
		int upperBound = 10;
		IntegerGenotype fixedBoundGeno = new IntegerGenotype(lowerBound, upperBound);
		assertTrue(fixedBoundGeno.isEmpty());
		fixedBoundGeno.init(rand, 5);
		assertEquals(5, fixedBoundGeno.size());
		IntegerGenotype other = fixedBoundGeno.newInstance();
		assertNotEquals(fixedBoundGeno, other);
		fixedBoundGeno.init(rand, 10);
		assertEquals(10, fixedBoundGeno.size());
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			assertEquals(new Long(lowerBound), new Long(fixedBoundGeno.getLowerBound(index)));
			assertEquals(new Long(lowerBound), new Long(other.getLowerBound(index)));
			assertEquals(new Long(upperBound), new Long(fixedBoundGeno.getUpperBound(index)));
			assertEquals(new Long(upperBound), new Long(other.getUpperBound(index)));
		}
	}
}
