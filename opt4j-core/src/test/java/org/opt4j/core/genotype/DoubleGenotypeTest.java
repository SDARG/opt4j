package org.opt4j.core.genotype;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class DoubleGenotypeTest {

	@Test
	public void testWrongSettingBoundList() {
		assertThrows(IllegalArgumentException.class, () -> {
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
			Assertions.assertTrue(listGeno.isEmpty());
			listGeno.init(rand, 4);
		});
	}

	@Test
	public void testWrongBoundSettingArray() {
		assertThrows(IllegalArgumentException.class, () -> {
			Random rand = new Random();
			double[] lowerBounds = { -1.0, -0.5, 0.0 };
			double[] upperBounds = { -0.5, 0.0, 0.5 };
			DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
			DoubleGenotype listGeno = new DoubleGenotype(bounds);
			Assertions.assertTrue(listGeno.isEmpty());
			listGeno.init(rand, 4);
		});
	}

	@Test
	public void testArrayBound() {
		Random rand = new Random();
		double[] lowerBounds = { -1.0, -0.5, 0.0 };
		double[] upperBounds = { -0.5, 0.0, 0.5 };
		DoubleBounds bounds = new DoubleBounds(lowerBounds, upperBounds);
		DoubleGenotype listGeno = new DoubleGenotype(bounds);
		Assertions.assertTrue(listGeno.isEmpty());
		listGeno.init(rand, 3);
		Assertions.assertEquals(3, listGeno.size());
		DoubleGenotype other = listGeno.newInstance();
		Assertions.assertNotEquals(listGeno, other);
		Assertions.assertTrue(other.isEmpty());
		other.init(rand, 2);
		Assertions.assertEquals(2, other.size());

		Assertions.assertTrue(listGeno.get(0) <= upperBounds[0]);
		Assertions.assertTrue(listGeno.get(1) <= upperBounds[1]);
		Assertions.assertTrue(listGeno.get(2) <= upperBounds[2]);
		Assertions.assertTrue(listGeno.get(0) >= lowerBounds[0]);
		Assertions.assertTrue(listGeno.get(1) >= lowerBounds[1]);
		Assertions.assertTrue(listGeno.get(2) >= lowerBounds[2]);

		Assertions.assertTrue(other.get(0) <= upperBounds[0]);
		Assertions.assertTrue(other.get(1) <= upperBounds[1]);
		Assertions.assertTrue(other.get(0) >= lowerBounds[0]);
		Assertions.assertTrue(other.get(1) >= lowerBounds[1]);
	}

	@Test
	public void testFixedBound() {
		Random rand = new Random();
		double lowerBound = 0.1;
		double upperBound = 9.9;
		DoubleGenotype fixedGenotype = new DoubleGenotype(lowerBound, upperBound);
		Assertions.assertTrue(fixedGenotype.isEmpty());
		fixedGenotype.init(rand, 3);
		Assertions.assertEquals(3, fixedGenotype.size());
		DoubleGenotype other = fixedGenotype.newInstance();
		Assertions.assertNotEquals(fixedGenotype, other);
		Assertions.assertTrue(other.isEmpty());
		fixedGenotype.init(rand, 5);
		other.init(rand, 3);
		Assertions.assertEquals(3, other.size());
		Assertions.assertEquals(5, fixedGenotype.size());

		Assertions.assertTrue(other.get(0) <= upperBound);
		Assertions.assertTrue(other.get(1) <= upperBound);
		Assertions.assertTrue(other.get(2) <= upperBound);
		Assertions.assertTrue(other.get(0) >= lowerBound);
		Assertions.assertTrue(other.get(1) >= lowerBound);
		Assertions.assertTrue(other.get(2) >= lowerBound);

		Assertions.assertTrue(fixedGenotype.get(0) <= upperBound);
		Assertions.assertTrue(fixedGenotype.get(1) <= upperBound);
		Assertions.assertTrue(fixedGenotype.get(2) <= upperBound);
		Assertions.assertTrue(fixedGenotype.get(3) <= upperBound);
		Assertions.assertTrue(fixedGenotype.get(4) <= upperBound);
		Assertions.assertTrue(fixedGenotype.get(0) >= lowerBound);
		Assertions.assertTrue(fixedGenotype.get(1) >= lowerBound);
		Assertions.assertTrue(fixedGenotype.get(2) >= lowerBound);
		Assertions.assertTrue(fixedGenotype.get(3) >= lowerBound);
		Assertions.assertTrue(fixedGenotype.get(4) >= lowerBound);
	}

	@Test
	public void testEmptyConstructor() {
		Random rand = new Random();
		DoubleGenotype zeroOne = new DoubleGenotype();
		Assertions.assertTrue(zeroOne.isEmpty());
		zeroOne.init(rand, 3);
		Assertions.assertEquals(3, zeroOne.size());
		DoubleGenotype other = zeroOne.newInstance();
		Assertions.assertTrue(other.isEmpty());
		Assertions.assertNotEquals(zeroOne, other);
		other.init(rand, 3);
		Assertions.assertEquals(3, other.size());
		zeroOne.init(rand, 5);
		Assertions.assertEquals(5, zeroOne.size());

		Assertions.assertTrue(zeroOne.get(0) <= 1.0);
		Assertions.assertTrue(zeroOne.get(1) <= 1.0);
		Assertions.assertTrue(zeroOne.get(2) <= 1.0);
		Assertions.assertTrue(zeroOne.get(3) <= 1.0);
		Assertions.assertTrue(zeroOne.get(4) <= 1.0);
		Assertions.assertTrue(zeroOne.get(0) >= 0.0);
		Assertions.assertTrue(zeroOne.get(1) >= 0.0);
		Assertions.assertTrue(zeroOne.get(2) >= 0.0);
		Assertions.assertTrue(zeroOne.get(3) >= 0.0);
		Assertions.assertTrue(zeroOne.get(4) >= 0.0);

		Assertions.assertTrue(other.get(0) <= 1.0);
		Assertions.assertTrue(other.get(1) <= 1.0);
		Assertions.assertTrue(other.get(2) <= 1.0);
		Assertions.assertTrue(other.get(0) >= 0.0);
		Assertions.assertTrue(other.get(1) >= 0.0);
		Assertions.assertTrue(other.get(2) >= 0.0);
	}
}
