package org.opt4j.core;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Objective.Sign;

public class ObjectivesTest {
	@Test
	public void getValuesTest() {
		Objectives objectives0 = new Objectives();
		objectives0.add(new Objective("a"), 0);
		objectives0.add(new Objective("b"), 1);
		Assert.assertEquals(2, objectives0.getValues().size());
		Assert.assertTrue(objectives0.getValues().contains(new IntegerValue(0)));
		Assert.assertTrue(objectives0.getValues().contains(new IntegerValue(1)));
	}

	@Test
	public void getTest() {
		Objectives objectives0 = new Objectives();
		Objective obj = new Objective("a");
		objectives0.add(obj, 0);
		objectives0.add(new Objective("b"), 1);
		Assert.assertEquals(obj, objectives0.get(new IntegerValue(0)));
		Assert.assertNull(objectives0.get(new IntegerValue(3)));
	}

	@Test
	public void addNamedTest() {
		Objectives objectives0 = new Objectives();
		Objective obj = new Objective("a");
		objectives0.add("a", Sign.MIN, 2);
		Assert.assertEquals(obj, objectives0.iterator().next().getKey());
		Assert.assertEquals(new IntegerValue(2), objectives0.iterator().next().getValue());
	}

	@Test
	public void addValueTest() {
		Objectives objectives0 = new Objectives();
		Objective obj = new Objective("a");
		objectives0.add("a", Sign.MIN, new DoubleValue(2.0));
		Assert.assertEquals(obj, objectives0.iterator().next().getKey());
		Assert.assertEquals(new DoubleValue(2.0), objectives0.iterator().next().getValue());
	}

	@Test
	public void addDoubleTest() {
		Objectives objectives0 = new Objectives();
		Objective obj = new Objective("a");
		objectives0.add("a", Sign.MIN, 2.0);
		Assert.assertEquals(obj, objectives0.iterator().next().getKey());
		Assert.assertEquals(new DoubleValue(2.0), objectives0.iterator().next().getValue());
	}

	@Test
	public void addIntTest() {
		Objectives objectives0 = new Objectives();
		Objective obj = new Objective("a");
		objectives0.add("a", Sign.MIN, 3);
		Assert.assertEquals(obj, objectives0.iterator().next().getKey());
		Assert.assertEquals(new IntegerValue(3), objectives0.iterator().next().getValue());
	}

	@Test
	public void isEqualTest() {
		Objectives objectives0 = new Objectives();
		objectives0.add("a", Sign.MIN, 3);

		Objectives objectives1 = new Objectives();
		objectives1.add("a", Sign.MIN, 3);

		Objectives objectives2 = new Objectives();
		objectives2.add("a", Sign.MIN, 4);

		Assert.assertTrue(objectives0.isEqual(objectives1));
		Assert.assertFalse(objectives0.isEqual(objectives2));
	}

	@Test
	public void sizeTest() {
		Objectives objectives = new Objectives();
		objectives.add(new Objective("x"), 1.0);

		Assert.assertEquals(1, objectives.size());
	}

	@Test
	public void distanceTest() {
		Objectives objectives0 = new Objectives();
		objectives0.add(new Objective("a"), 0.0);
		objectives0.add(new Objective("b"), 0.0);

		Objectives objectives1 = new Objectives();
		objectives1.add(new Objective("a"), 1.0);
		objectives1.add(new Objective("b"), 1.0);

		Assert.assertEquals(Math.sqrt(2.0), objectives0.distance(objectives1), 0.0001);
		Assert.assertEquals(objectives0.distance(objectives1), objectives1.distance(objectives0), 0.0001);
		Assert.assertEquals(0.0, objectives0.distance(objectives0), 0.0001);
	}

	@Test
	public void toStringTest() {
		Objectives objectives0 = new Objectives();
		objectives0.add("a", Sign.MIN, 3);

		Assert.assertEquals("a(MIN)=3 ", objectives0.toString());
	}
}
