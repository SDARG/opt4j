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

public class BooleanMapGenotypeTest {

	@SuppressWarnings("serial")
	class MockRandom extends Random {
		@Override
		public boolean nextBoolean() {
			return false;
		}
	}

	@SuppressWarnings("serial")
	class MockList extends ArrayList<Integer> {
		public MockList() {
			add(1);
			add(2);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKeyGetIndex() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		geno.getIndexOf(3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKeySetValue() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		geno.setValue(3, true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKeyGetValue() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		geno.getValue(3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testWrongInit() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		geno.init(new MockRandom(), 2);
	}

	@Test
	public void testConstructor() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		assertTrue(geno.isEmpty());
		geno.setValue(1, true);
		assertEquals(1, geno.size());
		geno.init(new MockRandom());
		assertEquals(2, geno.size());
		assertFalse(geno.getValue(1));
		geno.setValue(1, true);
		assertTrue(geno.getValue(1));
		assertTrue(geno.containsKey(1));
		assertFalse(geno.containsKey(3));
		assertEquals(0, geno.getIndexOf(1));
		Set<Integer> keys = new HashSet<>(geno.getKeys());
		assertEquals(2, keys.size());
		assertTrue(keys.contains(1));
		assertTrue(keys.contains(2));
		BooleanMapGenotype<Integer> other = geno.newInstance();
		assertNotEquals(geno, other);
		assertTrue(other.isEmpty());
		assertEquals("[1=true;2=false;]", geno.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeys() {
		List<Integer> keyList = new ArrayList<>();
		keyList.add(1);
		keyList.add(1);
		new BooleanMapGenotype<>(keyList);
	}

}
