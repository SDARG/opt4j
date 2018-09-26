package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class SelectGenotypeTest {

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

		private SelectGenotypeTest getOuterType() {
			return SelectGenotypeTest.this;
		}
	}

	public void testGenotype(SelectGenotype<MockObject> selectGenotype, Set<MockObject> mockObjects) {
		Random rand = new Random();
		assertTrue(selectGenotype.isEmpty());
		selectGenotype.init(rand, 5);
		assertEquals(5, selectGenotype.size());
		for (int i = 0; i < 5; i++) {
			assertEquals(0, selectGenotype.getLowerBound(i));
			assertEquals(3, selectGenotype.getUpperBound(i));
			assertTrue(mockObjects.contains(selectGenotype.getValue(i)));
		}

		SelectGenotype<MockObject> other = selectGenotype.newInstance();
		assertNotEquals(selectGenotype, other);
		assertTrue(other.isEmpty());
	}

	@Test
	public void testArrayConstructor() {
		MockObject obj1 = new MockObject(1.0, 1);
		MockObject obj2 = new MockObject(2.0, 2);
		MockObject obj3 = new MockObject(3.0, 3);
		MockObject[] inputArray = { obj1, obj2, obj3, obj1 };
		Set<MockObject> mockObjects = new HashSet<>();
		mockObjects.add(obj1);
		mockObjects.add(obj2);
		mockObjects.add(obj3);
		SelectGenotype<MockObject> selectGenotype = new SelectGenotype<>(inputArray);
		testGenotype(selectGenotype, mockObjects);
		assertFalse(selectGenotype.isEmpty());
	}

	@Test
	public void testListConstructor() {
		List<MockObject> inputList = new ArrayList<>();
		Set<MockObject> mockObjects = new HashSet<>();
		MockObject obj1 = new MockObject(1.0, 1);
		MockObject obj2 = new MockObject(2.0, 2);
		MockObject obj3 = new MockObject(3.0, 3);
		inputList.add(obj1);
		inputList.add(obj2);
		inputList.add(obj3);
		inputList.add(obj1);
		mockObjects.add(obj1);
		mockObjects.add(obj2);
		mockObjects.add(obj3);
		SelectGenotype<MockObject> selectGenotype = new SelectGenotype<>(inputList);
		testGenotype(selectGenotype, mockObjects);
		assertFalse(selectGenotype.isEmpty());
	}

}
