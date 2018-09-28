package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DoubleBoundsTest {

	@Test(expected = IllegalArgumentException.class)
	public void differentSizeTest() {
		List<Double> lowerBounds = new ArrayList<>();
		lowerBounds.add(-1.0);
		lowerBounds.add(0.0);
		List<Double> upperBounds = new ArrayList<>();
		upperBounds.add(0.0);
		upperBounds.add(1.0);
		upperBounds.add(2.0);
		new DoubleBounds(lowerBounds, upperBounds);
	}

	@Test(expected = IllegalArgumentException.class)
	public void differentLengthTest() {
		double[] lowerBounds = { -1.0, 0.0, 1.0 };
		double[] upperBounds = { 0.0, 1.0 };
		new DoubleBounds(lowerBounds, upperBounds);
	}

	@Test
	public void testArrayBounds() {
		double[] lowerBounds = { -1.0, 0.0, 1.0 };
		double[] upperBounds = { 0.0, 1.0, 2.0 };
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		assertEquals(-1.0, bounds.getLowerBound(0), 0.0);
		assertEquals(0.0, bounds.getLowerBound(1), 0.0);
		assertEquals(1.0, bounds.getLowerBound(2), 0.0);
		assertEquals(0.0, bounds.getUpperBound(0), 0.0);
		assertEquals(1.0, bounds.getUpperBound(1), 0.0);
		assertEquals(2.0, bounds.getUpperBound(2), 0.0);
	}

	@Test
	public void testListBounds() {
		List<Double> lowerBounds = new ArrayList<>();
		lowerBounds.add(-1.0);
		lowerBounds.add(0.0);
		lowerBounds.add(1.0);
		List<Double> upperBounds = new ArrayList<>();
		upperBounds.add(0.0);
		upperBounds.add(1.0);
		upperBounds.add(2.0);
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		assertEquals(-1.0, bounds.getLowerBound(0), 0.0);
		assertEquals(0.0, bounds.getLowerBound(1), 0.0);
		assertEquals(1.0, bounds.getLowerBound(2), 0.0);
		assertEquals(0.0, bounds.getUpperBound(0), 0.0);
		assertEquals(1.0, bounds.getUpperBound(1), 0.0);
		assertEquals(2.0, bounds.getUpperBound(2), 0.0);
	}
}
