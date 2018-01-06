package org.opt4j.core;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Objective.Sign;

public class ObjectiveTest {

	@Test
	public void getSignTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assert.assertEquals(Sign.MAX, o.getSign());
	}

	@Test
	public void getNameTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assert.assertEquals("obj", o.getName());
	}

	@Test
	public void equalsTest() {
		Objective o1 = new Objective("obj1", Sign.MAX);
		Objective o11 = new Objective("obj1", Sign.MAX);
		Objective o2 = new Objective("obj2", Sign.MAX);
		Objective o3 = new Objective("obj1", Sign.MIN);
		Objective o4 = new Objective(null, Sign.MAX);
		Objective o5 = new Objective(null, Sign.MAX);

		Assert.assertEquals(o1, o1);
		Assert.assertEquals(o1, o11);
		Assert.assertEquals(o4, o5);
		Assert.assertNotEquals(o1, null);
		Assert.assertNotEquals(o1, "something else");
		Assert.assertNotEquals(o1, o2);
		Assert.assertNotEquals(o1, o3);
		Assert.assertNotEquals(o4, o3);
	}

	@Test
	public void hashCodeTest() {
		String name = "obj1";
		Objective o1 = new Objective(name, Sign.MAX);
		Objective o2 = new Objective(name, Sign.MAX);
		Objective o3 = new Objective(null, Sign.MAX);
		Objective o4 = new Objective(name, null);

		Assert.assertEquals(o1.hashCode(), o2.hashCode());
		Assert.assertNotEquals(o1.hashCode(), o3.hashCode());
		Assert.assertNotEquals(o1.hashCode(), o4.hashCode());
	}

	@Test
	public void compareToTest() {
		Objective o1 = new Objective("obj1", Sign.MAX);
		Objective o11 = new Objective("obj1", Sign.MAX);
		Objective o2 = new Objective("obj2", Sign.MAX);

		Assert.assertEquals(0, o1.compareTo(o11));
		Assert.assertEquals(-1 * o1.compareTo(o2), o2.compareTo(o1));
		Assert.assertEquals(1, o1.compareTo(null));
	}

	@Test
	public void toStringTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assert.assertEquals("obj(MAX)", o.toString());
	}

}
