package org.opt4j.core.genotype;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class IntegerGenotypeTest {

	@Test
	public void testWrongBoundSettingList() {
		assertThrows(IllegalArgumentException.class, () -> {
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
			Assertions.assertTrue(listBoundGeno.isEmpty());
			listBoundGeno.init(rand, 3);
			Assertions.assertEquals(3, listBoundGeno.size());
			listBoundGeno.init(rand, 4);
		});
	}

	@Test
	public void testWrongBoundSettingArray() {
		assertThrows(IllegalArgumentException.class, () -> {
			Random rand = new Random();
			int[] lowerBounds = { 1, 2, 3 };
			int[] upperBounds = { 2, 3, 4 };
			IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
			IntegerGenotype listBoundGeno = new IntegerGenotype(bounds);
			Assertions.assertTrue(listBoundGeno.isEmpty());
			listBoundGeno.init(rand, 3);
			Assertions.assertEquals(3, listBoundGeno.size());
			listBoundGeno.init(rand, 4);
		});
	}

	@Test
	public void testListBoundGenotype() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		IntegerGenotype listBoundGeno = new IntegerGenotype(bounds);
		Assertions.assertTrue(listBoundGeno.isEmpty());
		listBoundGeno.init(rand, 3);
		Assertions.assertEquals(3, listBoundGeno.size());
		IntegerGenotype other = listBoundGeno.newInstance();
		Assertions.assertNotEquals(listBoundGeno, other);
		Assertions.assertTrue(other.isEmpty());
		other.init(rand, 2);
		Assertions.assertTrue(listBoundGeno.get(0) <= 2);
		Assertions.assertTrue(listBoundGeno.get(1) <= 3);
		Assertions.assertTrue(listBoundGeno.get(2) <= 4);
		Assertions.assertTrue(other.get(0) <= 2);
		Assertions.assertTrue(other.get(1) <= 3);
		Assertions.assertTrue(listBoundGeno.get(0) >= 1);
		Assertions.assertTrue(listBoundGeno.get(1) >= 2);
		Assertions.assertTrue(listBoundGeno.get(2) >= 3);
		Assertions.assertTrue(other.get(0) >= 1);
		Assertions.assertTrue(other.get(1) >= 2);
	}

	@Test
	public void testFixedBoundGenotype() {
		Random rand = new Random();
		int lowerBound = 2;
		int upperBound = 10;
		IntegerGenotype fixedBoundGeno = new IntegerGenotype(lowerBound, upperBound);
		Assertions.assertTrue(fixedBoundGeno.isEmpty());
		fixedBoundGeno.init(rand, 5);
		Assertions.assertEquals(5, fixedBoundGeno.size());
		IntegerGenotype other = fixedBoundGeno.newInstance();
		Assertions.assertNotEquals(fixedBoundGeno, other);
		fixedBoundGeno.init(rand, 10);
		Assertions.assertEquals(10, fixedBoundGeno.size());
		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt();
			Assertions.assertEquals(Long.valueOf(lowerBound), Long.valueOf(fixedBoundGeno.getLowerBound(index)));
			Assertions.assertEquals(Long.valueOf(lowerBound), Long.valueOf(other.getLowerBound(index)));
			Assertions.assertEquals(Long.valueOf(upperBound), Long.valueOf(fixedBoundGeno.getUpperBound(index)));
			Assertions.assertEquals(Long.valueOf(upperBound), Long.valueOf(other.getUpperBound(index)));
		}
	}
}
