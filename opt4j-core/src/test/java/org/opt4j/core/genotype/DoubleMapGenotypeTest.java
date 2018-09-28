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

public class DoubleMapGenotypeTest {

	private static double[] lower = { 1.0, 1.0 };
	private static double[] lower2 = { -1.0, -1.0 };
	private static double[] upper = { 1.0, 1.0 };

	class MockBounds extends DoubleBounds {

		public MockBounds() {
			super(DoubleMapGenotypeTest.lower, DoubleMapGenotypeTest.upper);
		}

	}

	class MockObject {
		protected final int field;

		public MockObject(int field) {
			this.field = field;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + field;
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
			return field == other.field;
		}

		@Override
		public String toString() {
			return "m" + field;
		}

		private DoubleMapGenotypeTest getOuterType() {
			return DoubleMapGenotypeTest.this;
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testWrongInitCall() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleBounds bounds = new DoubleBounds(lower2, upper);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, bounds);
		doubleMapGeno.init(new Random(), 10);
	}

	@Test
	public void testSetValue() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleBounds bounds = new DoubleBounds(lower2, upper);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, bounds);
		doubleMapGeno.setValue(m2, 0.4);
		assertEquals(0.4, doubleMapGeno.getValue(m2), 0.0);
		doubleMapGeno.init(new Random());
		doubleMapGeno.setValue(m1, 0.5);
		assertEquals(0.5, doubleMapGeno.getValue(m1), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetValueOutOfBounds2() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.setValue(m1, -2.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetValueOutOfBounds1() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.setValue(m1, 2.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetValueInvalidKey() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		MockObject m3 = new MockObject(3);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.setValue(m3, 1.0);
	}

	@Test
	public void testNewInstance() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.init(new Random());
		DoubleMapGenotype<MockObject> other = doubleMapGeno.newInstance();
		assertNotEquals(doubleMapGeno, other);
		assertTrue(other.isEmpty());
	}

	@Test
	public void testToString() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.init(new Random());
		assertEquals("[m1=1.0;m2=1.0;]", doubleMapGeno.toString());
	}

	@Test
	public void testGetIndexOf() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		assertEquals(0, doubleMapGeno.getIndexOf(m1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetIndexOfInvalidKey() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		MockObject m3 = new MockObject(3);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		doubleMapGeno.getIndexOf(m3);
	}

	@Test
	public void testGetKeys() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		Collection<MockObject> ks = doubleMapGeno.getKeys();
		assertEquals(2, ks.size());
		assertTrue(ks.contains(m1));
		assertTrue(ks.contains(m2));
	}

	@Test()
	public void testContainsKey() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		MockObject m2 = new MockObject(2);
		MockObject m3 = new MockObject(3);
		keys.add(m1);
		keys.add(m2);
		DoubleMapGenotype<MockObject> doubleMapGeno = new DoubleMapGenotype<>(keys, new MockBounds());
		assertTrue(doubleMapGeno.containsKey(m1));
		assertFalse(doubleMapGeno.containsKey(m3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeys() {
		List<MockObject> keys = new ArrayList<>();
		MockObject m1 = new MockObject(1);
		keys.add(m1);
		keys.add(m1);
		new DoubleMapGenotype<>(keys, new MockBounds());
	}

}
