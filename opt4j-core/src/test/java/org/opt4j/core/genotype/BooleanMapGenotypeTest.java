package org.opt4j.core.genotype;



import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


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

	@Test
	public void testInvalidKeyGetIndex() {
		assertThrows(IllegalArgumentException.class, () -> {
			BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
			geno.getIndexOf(3);
		});
	}

	@Test
	public void testInvalidKeySetValue() {
		assertThrows(IllegalArgumentException.class, () -> {
			BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
			geno.setValue(3, true);
		});
	}

	@Test
	public void testInvalidKeyGetValue() {
		assertThrows(IllegalArgumentException.class, () -> {
			BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
			geno.getValue(3);
		});
	}

	@Test
	public void testWrongInit() {
		assertThrows(UnsupportedOperationException.class, () -> {
			BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
			geno.init(new MockRandom(), 2);
		});
	}

	@Test
	public void testConstructor() {
		BooleanMapGenotype<Integer> geno = new BooleanMapGenotype<>(new MockList());
		Assertions.assertTrue(geno.isEmpty());
		geno.setValue(1, true);
		Assertions.assertEquals(1, geno.size());
		geno.init(new MockRandom());
		Assertions.assertEquals(2, geno.size());
		Assertions.assertFalse(geno.getValue(1));
		geno.setValue(1, true);
		Assertions.assertTrue(geno.getValue(1));
		Assertions.assertTrue(geno.containsKey(1));
		Assertions.assertFalse(geno.containsKey(3));
		Assertions.assertEquals(0, geno.getIndexOf(1));
		Set<Integer> keys = new HashSet<>(geno.getKeys());
		Assertions.assertEquals(2, keys.size());
		Assertions.assertTrue(keys.contains(1));
		Assertions.assertTrue(keys.contains(2));
		BooleanMapGenotype<Integer> other = geno.newInstance();
		Assertions.assertNotEquals(geno, other);
		Assertions.assertTrue(other.isEmpty());
		Assertions.assertEquals("[1=true;2=false;]", geno.toString());
	}

	@Test
	public void testNonUniqueKeys() {
		assertThrows(IllegalArgumentException.class, () -> {
			List<Integer> keyList = new ArrayList<>();
			keyList.add(1);
			keyList.add(1);
			new BooleanMapGenotype<>(keyList);
		});
	}

}
