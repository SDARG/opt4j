package org.opt4j.core.genotype;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class IntegerBoundsTest {

	@Test
	public void differentSizeTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			List<Integer> lowerBounds = new ArrayList<>();
			lowerBounds.add(-1);
			lowerBounds.add(0);
			lowerBounds.add(1);
			List<Integer> upperBounds = new ArrayList<>();
			upperBounds.add(0);
			upperBounds.add(1);
			new IntegerBounds(lowerBounds, upperBounds);
		});	
	}

	@Test
	public void differentLengthTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			int[] lowerBounds = { -1, 0, 1 };
			int[] upperBounds = { 0, 1 };
			new IntegerBounds(lowerBounds, upperBounds);
		});
	}

	@Test
	public void testArrayBounds() {
		int[] lowerBounds = { -1, 0, 1 };
		int[] upperBounds = { 0, 1, 2 };
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		Assertions.assertEquals(Long.valueOf(-1), Long.valueOf(bounds.getLowerBound(0)));
		Assertions.assertEquals(Long.valueOf(0), Long.valueOf(bounds.getLowerBound(1)));
		Assertions.assertEquals(Long.valueOf(1), Long.valueOf(bounds.getLowerBound(2)));
		Assertions.assertEquals(Long.valueOf(0), Long.valueOf(bounds.getUpperBound(0)));
		Assertions.assertEquals(Long.valueOf(1), Long.valueOf(bounds.getUpperBound(1)));
		Assertions.assertEquals(Long.valueOf(2), Long.valueOf(bounds.getUpperBound(2)));
	}

	@Test
	public void testListBounds() {
		List<Integer> lowerBounds = new ArrayList<>();
		lowerBounds.add(-1);
		lowerBounds.add(0);
		lowerBounds.add(1);
		List<Integer> upperBounds = new ArrayList<>();
		upperBounds.add(0);
		upperBounds.add(1);
		upperBounds.add(2);
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		Assertions.assertEquals(Long.valueOf(-1), Long.valueOf(bounds.getLowerBound(0)));
		Assertions.assertEquals(Long.valueOf(0), Long.valueOf(bounds.getLowerBound(1)));
		Assertions.assertEquals(Long.valueOf(1), Long.valueOf(bounds.getLowerBound(2)));
		Assertions.assertEquals(Long.valueOf(0), Long.valueOf(bounds.getUpperBound(0)));
		Assertions.assertEquals(Long.valueOf(1), Long.valueOf(bounds.getUpperBound(1)));
		Assertions.assertEquals(Long.valueOf(2), Long.valueOf(bounds.getUpperBound(2)));
	}
}
