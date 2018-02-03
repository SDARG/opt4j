package org.opt4j.core.genotype;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IntegerBoundsTest {
	@Test
	public void testArrayBounds() {
		int[] lowerBounds = { -1, 0, 1 };
		int[] upperBounds = { 0, 1, 2 };
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		assertEquals(new Long(-1), new Long(bounds.getLowerBound(0)));
		assertEquals(new Long(0), new Long(bounds.getLowerBound(1)));
		assertEquals(new Long(1), new Long(bounds.getLowerBound(2)));
		assertEquals(new Long(0), new Long(bounds.getUpperBound(0)));
		assertEquals(new Long(1), new Long(bounds.getUpperBound(1)));
		assertEquals(new Long(2), new Long(bounds.getUpperBound(2)));
	}

	@Test
	public void testListBounds() {
		List<Integer> lowerBounds = new ArrayList<Integer>();
		lowerBounds.add(-1);
		lowerBounds.add(0);
		lowerBounds.add(1);
		List<Integer> upperBounds = new ArrayList<Integer>();
		upperBounds.add(0);
		upperBounds.add(1);
		upperBounds.add(2);
		IntegerBounds bounds = new IntegerBounds(lowerBounds, upperBounds);
		assertEquals(new Long(-1), new Long(bounds.getLowerBound(0)));
		assertEquals(new Long(0), new Long(bounds.getLowerBound(1)));
		assertEquals(new Long(1), new Long(bounds.getLowerBound(2)));
		assertEquals(new Long(0), new Long(bounds.getUpperBound(0)));
		assertEquals(new Long(1), new Long(bounds.getUpperBound(1)));
		assertEquals(new Long(2), new Long(bounds.getUpperBound(2)));
	}
}
