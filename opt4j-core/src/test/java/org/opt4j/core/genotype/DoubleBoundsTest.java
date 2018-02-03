package org.opt4j.core.genotype;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DoubleBoundsTest {

	@Test
	public void testArrayBounds() {
		double[] lowerBounds = {-1.0, 0.0, 1.0};
		double[] upperBounds = {0.0, 1.0, 2.0};
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		assertEquals(-1.0, bounds.getLowerBound(0), 0.0);
		assertEquals(0.0, bounds.getLowerBound(1), 0.0);
		assertEquals(1.0, bounds.getLowerBound(2), 0.0);
		assertEquals(0.0, bounds.getUpperBound(0), 0.0);
		assertEquals(1.0, bounds.getUpperBound(1), 0.0);
		assertEquals(2.0, bounds.getUpperBound(2), 0.0);
	}
	
	@Test
	public void testListBounds(){
		List<Double> lowerBounds = new ArrayList<Double>();
		lowerBounds.add(-1.0);
		lowerBounds.add(0.0);
		lowerBounds.add(1.0);
		List<Double> upperBounds = new ArrayList<Double>();
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