package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class DoubleGenotypeTest {

	@Test(expected = IllegalArgumentException.class)
	public void testWrongSettingBoundList() {
		Random rand = new Random();
		List<Double> lowerBounds = new ArrayList<>();
		lowerBounds.add(-1.0);
		lowerBounds.add(-0.5);
		lowerBounds.add(0.0);
		List<Double> upperBounds = new ArrayList<>();
		upperBounds.add(-0.5);
		upperBounds.add(0.0);
		upperBounds.add(0.5);
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		DoubleGenotype listGeno = new DoubleGenotype(bounds);
		assertTrue(listGeno.isEmpty());
		listGeno.init(rand, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongBoundSettingArray() {
		Random rand = new Random();
		double[] lowerBounds = { -1.0, -0.5, 0.0 };
		double[] upperBounds = { -0.5, 0.0, 0.5 };
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		DoubleGenotype listGeno = new DoubleGenotype(bounds);
		assertTrue(listGeno.isEmpty());
		listGeno.init(rand, 4);
	}

	@Test
	public void testArrayBound() {
		Random rand = new Random();
		double[] lowerBounds = { -1.0, -0.5, 0.0 };
		double[] upperBounds = { -0.5, 0.0, 0.5 };
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		DoubleGenotype listGeno = new DoubleGenotype(bounds);
		assertTrue(listGeno.isEmpty());
		listGeno.init(rand, 3);
		assertEquals(3, listGeno.size());
		DoubleGenotype other = listGeno.newInstance();
		assertNotEquals(listGeno, other);
		assertTrue(other.isEmpty());
		other.init(rand, 2);
		assertEquals(2, other.size());

		assertTrue(listGeno.get(0) <= upperBounds[0]);
		assertTrue(listGeno.get(1) <= upperBounds[1]);
		assertTrue(listGeno.get(2) <= upperBounds[2]);
		assertTrue(listGeno.get(0) >= lowerBounds[0]);
		assertTrue(listGeno.get(1) >= lowerBounds[1]);
		assertTrue(listGeno.get(2) >= lowerBounds[2]);

		assertTrue(other.get(0) <= upperBounds[0]);
		assertTrue(other.get(1) <= upperBounds[1]);
		assertTrue(other.get(0) >= lowerBounds[0]);
		assertTrue(other.get(1) >= lowerBounds[1]);
	}

	@Test
	public void testFixedBound() {
		Random rand = new Random();
		double lowerBound = 0.1;
		double upperBound = 9.9;
		DoubleGenotype fixedGenotype = new DoubleGenotype(lowerBound, upperBound);
		assertTrue(fixedGenotype.isEmpty());
		fixedGenotype.init(rand, 3);
		assertEquals(3, fixedGenotype.size());
		DoubleGenotype other = fixedGenotype.newInstance();
		assertNotEquals(fixedGenotype, other);
		assertTrue(other.isEmpty());
		fixedGenotype.init(rand, 5);
		other.init(rand, 3);
		assertEquals(3, other.size());
		assertEquals(5, fixedGenotype.size());

		assertTrue(other.get(0) <= upperBound);
		assertTrue(other.get(1) <= upperBound);
		assertTrue(other.get(2) <= upperBound);
		assertTrue(other.get(0) >= lowerBound);
		assertTrue(other.get(1) >= lowerBound);
		assertTrue(other.get(2) >= lowerBound);

		assertTrue(fixedGenotype.get(0) <= upperBound);
		assertTrue(fixedGenotype.get(1) <= upperBound);
		assertTrue(fixedGenotype.get(2) <= upperBound);
		assertTrue(fixedGenotype.get(3) <= upperBound);
		assertTrue(fixedGenotype.get(4) <= upperBound);
		assertTrue(fixedGenotype.get(0) >= lowerBound);
		assertTrue(fixedGenotype.get(1) >= lowerBound);
		assertTrue(fixedGenotype.get(2) >= lowerBound);
		assertTrue(fixedGenotype.get(3) >= lowerBound);
		assertTrue(fixedGenotype.get(4) >= lowerBound);
	}

	@Test
	public void testEmptyConstructor() {
		Random rand = new Random();
		DoubleGenotype zeroOne = new DoubleGenotype();
		assertTrue(zeroOne.isEmpty());
		zeroOne.init(rand, 3);
		assertEquals(3, zeroOne.size());
		DoubleGenotype other = zeroOne.newInstance();
		assertTrue(other.isEmpty());
		assertNotEquals(zeroOne, other);
		other.init(rand, 3);
		assertEquals(3, other.size());
		zeroOne.init(rand, 5);
		assertEquals(5, zeroOne.size());

		assertTrue(zeroOne.get(0) <= 1.0);
		assertTrue(zeroOne.get(1) <= 1.0);
		assertTrue(zeroOne.get(2) <= 1.0);
		assertTrue(zeroOne.get(3) <= 1.0);
		assertTrue(zeroOne.get(4) <= 1.0);
		assertTrue(zeroOne.get(0) >= 0.0);
		assertTrue(zeroOne.get(1) >= 0.0);
		assertTrue(zeroOne.get(2) >= 0.0);
		assertTrue(zeroOne.get(3) >= 0.0);
		assertTrue(zeroOne.get(4) >= 0.0);

		assertTrue(other.get(0) <= 1.0);
		assertTrue(other.get(1) <= 1.0);
		assertTrue(other.get(2) <= 1.0);
		assertTrue(other.get(0) >= 0.0);
		assertTrue(other.get(1) >= 0.0);
		assertTrue(other.get(2) >= 0.0);
	}
}
