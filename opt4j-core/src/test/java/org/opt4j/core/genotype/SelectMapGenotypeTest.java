package org.opt4j.core.genotype;


import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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

	@Test
	public void testSetWrongValue() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
			genotype.init(new Random());
			genotype.setValue(2, 5);
		});
	}

	@Test
	public void testSetValue() {
		Inputs inputs = new Inputs();
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
		genotype.init(new Random());
		genotype.setValue(2, 3);
		Assertions.assertEquals(3, (long) genotype.getValue(2));
		genotype.setValue(2, 4);
		Assertions.assertEquals(4, (long) genotype.getValue(2));
	}

	@Test
	public void testMapConstructor() {
		Inputs inputs = new Inputs();
		inputs.map.get(2).remove(1);
		SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map);
		Collection<Integer> keys = genotype.getKeys();
		Assertions.assertTrue(keys.contains(1));
		Assertions.assertTrue(keys.contains(2));
		Random rand = new Random();
		genotype.init(rand);
		Assertions.assertEquals("[1=3;2=3;]", genotype.toString());
		Assertions.assertTrue(genotype.containsKey(2));
		Assertions.assertFalse(genotype.containsKey(3));
		Assertions.assertNotEquals(genotype, genotype.newInstance());
		Assertions.assertEquals(0, genotype.getIndexOf(1));
		Assertions.assertEquals(3, (long) genotype.getValue(1));
	}

	@Test
	public void testInvalidKey() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			SelectMapGenotype<Integer, Integer> geno = new SelectMapGenotype<>(inputs.list, inputs.map);
			geno.getIndexOf(4);
		});
	}

	@Test
	public void testListMapMismatch2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			inputs.map.put(3, new ArrayList<>());
			new SelectMapGenotype<>(inputs.list, inputs.map);
		});
	}

	@Test
	public void testListMapMismatch1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			inputs.list.add(3);
			new SelectMapGenotype<>(inputs.list, inputs.map);
		});
	}

	@Test
	public void testNonUniqueKeys2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			inputs.list.add(1);
			new SelectMapGenotype<>(inputs.list, inputs.map.get(1));
		});
	}

	@Test
	public void testNonUniqueKeys1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			inputs.list.add(1);
			new SelectMapGenotype<>(inputs.list, inputs.map);
		});
	}

	@Test
	public void testListConstructor() {
		assertThrows(UnsupportedOperationException.class, () -> {
			Inputs inputs = new Inputs();
			SelectMapGenotype<Integer, Integer> genotype = new SelectMapGenotype<>(inputs.list, inputs.map.get(1));
			Random rand = new Random();
			genotype.init(rand, 2);
		});
	}

	@Test
	public void testEmptyMap() {
		assertThrows(IllegalArgumentException.class, () -> {
			Inputs inputs = new Inputs();
			inputs.list.add(3);
			inputs.map.put(3, new ArrayList<>());
			new SelectBounds<>(inputs.list, inputs.map);
		});
	}

	@Test
	public void testSelectBounds() {
		Inputs inputs = new Inputs();
		SelectBounds<Integer, Integer> bounds = new SelectBounds<>(inputs.list, inputs.map);
		Assertions.assertEquals(0, (long) bounds.getLowerBound(0));
		Assertions.assertEquals(0, (long) bounds.getUpperBound(0));
		Assertions.assertEquals(0, (long) bounds.getLowerBound(1));
		Assertions.assertEquals(1, (long) bounds.getUpperBound(1));
	}

}
