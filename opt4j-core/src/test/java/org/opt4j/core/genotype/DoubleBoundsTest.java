package org.opt4j.core.genotype;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class DoubleBoundsTest {

	@Test
	public void differentSizeTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			List<Double> lowerBounds = new ArrayList<>();
			lowerBounds.add(-1.0);
			lowerBounds.add(0.0);
			List<Double> upperBounds = new ArrayList<>();
			upperBounds.add(0.0);
			upperBounds.add(1.0);
			upperBounds.add(2.0);
			new DoubleBounds(lowerBounds, upperBounds);
		});
	}

	@Test
	public void differentLengthTest() {
		assertThrows(IllegalArgumentException.class, () -> {
			double[] lowerBounds = { -1.0, 0.0, 1.0 };
			double[] upperBounds = { 0.0, 1.0 };
			new DoubleBounds(lowerBounds, upperBounds);
		});
	}

	@Test
	public void testArrayBounds() {
		double[] lowerBounds = { -1.0, 0.0, 1.0 };
		double[] upperBounds = { 0.0, 1.0, 2.0 };
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		Assertions.assertEquals(-1.0, bounds.getLowerBound(0), 0.0);
		Assertions.assertEquals(0.0, bounds.getLowerBound(1), 0.0);
		Assertions.assertEquals(1.0, bounds.getLowerBound(2), 0.0);
		Assertions.assertEquals(0.0, bounds.getUpperBound(0), 0.0);
		Assertions.assertEquals(1.0, bounds.getUpperBound(1), 0.0);
		Assertions.assertEquals(2.0, bounds.getUpperBound(2), 0.0);
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
		Assertions.assertEquals(-1.0, bounds.getLowerBound(0), 0.0);
		Assertions.assertEquals(0.0, bounds.getLowerBound(1), 0.0);
		Assertions.assertEquals(1.0, bounds.getLowerBound(2), 0.0);
		Assertions.assertEquals(0.0, bounds.getUpperBound(0), 0.0);
		Assertions.assertEquals(1.0, bounds.getUpperBound(1), 0.0);
		Assertions.assertEquals(2.0, bounds.getUpperBound(2), 0.0);
	}
}
