package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class IntegerMapGenotypeTest {

	class MockObject {
		private final double field1;
		private final int field2;

		public MockObject(double field1, int field2) {
			this.field1 = field1;
			this.field2 = field2;
		}

		public double getField1() {
			return field1;
		}

		public int getField2() {
			return field2;
		}

		@Override
		public String toString() {
			return "mock";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			long temp;
			temp = Double.doubleToLongBits(field1);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + field2;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			MockObject other = (MockObject) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			if (Double.doubleToLongBits(field1) != Double.doubleToLongBits(other.field1)) {
				return false;
			}
			return field2 == other.field2;
		}

		private IntegerMapGenotypeTest getOuterType() {
			return IntegerMapGenotypeTest.this;
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testForbiddenInit() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.init(rand, 3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeys() {
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		inputList.add(mockup1);
		new IntegerMapGenotype<>(inputList, bounds);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValueMissingKey() {
		int lower = 1;
		int upper = 2;
		MockObject m1 = new MockObject(1.0, 1);
		List<MockObject> list = new ArrayList<>();
		list.add(m1);
		IntegerMapGenotype<MockObject> geno = new IntegerMapGenotype<>(list, lower, upper);
		geno.getValue(new MockObject(1.0, 2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeysBoundConstructor() {
		int lower = 1;
		int upper = 2;
		MockObject m1 = new MockObject(1.0, 1);
		List<MockObject> list = new ArrayList<>();
		list.add(m1);
		list.add(m1);
		new IntegerMapGenotype<>(list, lower, upper);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetIndexInvalidKey() {
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		MockObject mockup4 = new MockObject(4.0, 4);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.getIndexOf(mockup4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidKey() {
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		MockObject mockup4 = new MockObject(4.0, 4);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.setValue(mockup4, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetOutOfLowerBoundsValue() {
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.setValue(mockup3, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetOutOfUpperBoundsValue() {
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.setValue(mockup3, 5);
	}

	@Test
	public void testToString() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 1, 2, 3 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		integerMapGenotype.init(rand);
		assertEquals("[mock=1;mock=2;mock=3;]", integerMapGenotype.toString());
	}

	@Test
	public void testFixedBoundConstructor() {
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, 1, 3);
		for (int i = 1; i < 4; i++) {
			assertEquals(1, integerMapGenotype.getLowerBound(i));
			assertEquals(3, integerMapGenotype.getUpperBound(i));
		}
	}

	@Test
	public void testBoundConstructor() {
		Random rand = new Random();
		int[] lowerBounds = { 1, 2, 3 };
		int[] upperBounds = { 2, 3, 4 };
		Bounds<Integer> bounds = new IntegerBounds(lowerBounds, upperBounds);
		MockObject mockup1 = new MockObject(1.0, 1);
		MockObject mockup2 = new MockObject(2.0, 2);
		MockObject mockup3 = new MockObject(3.0, 3);
		MockObject mockup4 = new MockObject(4.0, 4);
		List<MockObject> inputList = new ArrayList<>();
		inputList.add(mockup1);
		inputList.add(mockup2);
		inputList.add(mockup3);
		IntegerMapGenotype<MockObject> integerMapGenotype = new IntegerMapGenotype<>(inputList, bounds);
		assertTrue(integerMapGenotype.isEmpty());
		assertEquals(integerMapGenotype.getLowerBound(0), 1);
		assertEquals(integerMapGenotype.getLowerBound(1), 2);
		assertEquals(integerMapGenotype.getLowerBound(2), 3);
		assertEquals(integerMapGenotype.getUpperBound(0), 2);
		assertEquals(integerMapGenotype.getUpperBound(1), 3);
		assertEquals(integerMapGenotype.getUpperBound(2), 4);
		integerMapGenotype.init(rand);
		assertEquals(3, integerMapGenotype.size());
		assertTrue(integerMapGenotype.containsKey(mockup1));
		assertTrue(integerMapGenotype.containsKey(mockup2));
		assertTrue(integerMapGenotype.containsKey(mockup3));
		assertFalse(integerMapGenotype.containsKey(mockup4));
		assertEquals(0, integerMapGenotype.getIndexOf(mockup1));
		assertEquals(1, integerMapGenotype.getIndexOf(mockup2));
		assertEquals(2, integerMapGenotype.getIndexOf(mockup3));
		Collection<MockObject> keys = integerMapGenotype.getKeys();
		assertTrue(keys.contains(mockup1));
		assertTrue(keys.contains(mockup2));
		assertTrue(keys.contains(mockup3));
		assertFalse(keys.contains(mockup4));
		assertTrue(integerMapGenotype.getValue(mockup1) >= 1);
		assertTrue(integerMapGenotype.getValue(mockup1) <= 2);
		assertTrue(integerMapGenotype.getValue(mockup2) >= 2);
		assertTrue(integerMapGenotype.getValue(mockup2) <= 3);
		assertTrue(integerMapGenotype.getValue(mockup3) >= 3);
		assertTrue(integerMapGenotype.getValue(mockup3) <= 4);
		integerMapGenotype.setValue(mockup3, 4);
		assertEquals(new Long(4), new Long(integerMapGenotype.getValue(mockup3)));
		IntegerMapGenotype<MockObject> other = integerMapGenotype.newInstance();
		assertNotEquals(integerMapGenotype, other);
		assertTrue(other.isEmpty());
		other.setValue(mockup2, 2);
		assertEquals(new Long(2), new Long(other.getValue(mockup2)));
		assertEquals(new Long(1), new Long(other.getValue(mockup1)));
	}

}
