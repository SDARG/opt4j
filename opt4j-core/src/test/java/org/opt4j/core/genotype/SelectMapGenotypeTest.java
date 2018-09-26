package org.opt4j.core.genotype;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.opt4j.core.genotype.SelectMapGenotype.SelectBounds;

public class SelectMapGenotypeTest {

	public class Inputs {
		protected List<Integer> list;
		protected Map<Integer, List<Integer>> map;
		protected List<Integer> first;
		protected List<Integer> second;

		public Inputs() {
			this.list = new ArrayList<>();
			list.add(1);
			list.add(2);
			this.map = new HashMap<>();
			this.first = new ArrayList<>();
			first.add(3);
			this.second = new ArrayList<>();
			second.add(3);
			second.add(4);
			map.put(1, first);
			map.put(2, second);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetWrongValue() {
		Inputs inputs = new Inputs();
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
		genotype.init(new Random());
		genotype.setValue(2, 5);
	}

	@Test
	public void testSetValue() {
		Inputs inputs = new Inputs();
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
		genotype.init(new Random());
		genotype.setValue(2, 3);
		assertEquals(3, (long) genotype.getValue(2));
		genotype.setValue(2, 4);
		assertEquals(4, (long) genotype.getValue(2));
	}

	@Test
	public void testMapConstructor() {
		Inputs inputs = new Inputs();
		inputs.map.get(2).remove(1);
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
		Collection<Integer> keys = genotype.getKeys();
		assertTrue(keys.contains(1));
		assertTrue(keys.contains(2));
		Random rand = new Random();
		genotype.init(rand);
		assertEquals("[1=3;2=3;]", genotype.toString());
		assertTrue(genotype.containsKey(2));
		assertFalse(genotype.containsKey(3));
		assertNotEquals(genotype, genotype.newInstance());
		assertEquals(0, genotype.getIndexOf(1));
		assertEquals(3, (long) genotype.getValue(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidKey() {
		Inputs inputs = new Inputs();
		SelectMapGenotype<Integer, Integer> geno = new SelectMapGenotype<>(inputs.list, inputs.map);
		geno.getIndexOf(4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testListMapMismatch2() {
		Inputs inputs = new Inputs();
		inputs.map.put(3, new ArrayList<>());
		new SelectMapGenotype<>(inputs.list, inputs.map);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testListMapMismatch1() {
		Inputs inputs = new Inputs();
		inputs.list.add(3);
		new SelectMapGenotype<>(inputs.list, inputs.map);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeys2() {
		Inputs inputs = new Inputs();
		inputs.list.add(1);
		new SelectMapGenotype<>(inputs.list, inputs.map.get(1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueKeys1() {
		Inputs inputs = new Inputs();
		inputs.list.add(1);
		new SelectMapGenotype<>(inputs.list, inputs.map);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testListConstructor() {
		Inputs inputs = new Inputs();
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map.get(1));
		Random rand = new Random();
		genotype.init(rand, 2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyMap() {
		Inputs inputs = new Inputs();
		inputs.list.add(3);
		inputs.map.put(3, new ArrayList<>());
		new SelectBounds<>(inputs.list, inputs.map);
	}

	@Test
	public void testSelectBounds() {
		Inputs inputs = new Inputs();
		SelectBounds<Integer, Integer> bounds = new SelectBounds<>(inputs.list, inputs.map);
		assertEquals(0, (long) bounds.getLowerBound(0));
		assertEquals(0, (long) bounds.getUpperBound(0));
		assertEquals(0, (long) bounds.getLowerBound(1));
		assertEquals(1, (long) bounds.getUpperBound(1));
	}

}
