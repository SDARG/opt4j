package org.opt4j.core;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Objective.Sign;

public class ObjectiveTest {

	@Test
	public void getSignTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assertions.assertEquals(Sign.MAX, o.getSign());
	}

	@Test
	public void getNameTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assertions.assertEquals("obj", o.getName());
	}

	@Test
	public void equalsTest() {
		Objective o1 = new Objective("obj1", Sign.MAX);
		Objective o11 = new Objective("obj1", Sign.MAX);
		Objective o2 = new Objective("obj2", Sign.MAX);
		Objective o3 = new Objective("obj1", Sign.MIN);
		Objective o4 = new Objective(null, Sign.MAX);
		Objective o5 = new Objective(null, Sign.MAX);

		Assertions.assertEquals(o1, o1);
		Assertions.assertEquals(o1, o11);
		Assertions.assertEquals(o4, o5);
		Assertions.assertNotEquals(o1, null);
		Assertions.assertNotEquals(o1, "something else");
		Assertions.assertNotEquals(o1, o2);
		Assertions.assertNotEquals(o1, o3);
		Assertions.assertNotEquals(o4, o3);
	}

	@Test
	public void hashCodeTest() {
		String name = "obj1";
		Objective o1 = new Objective(name, Sign.MAX);
		Objective o2 = new Objective(name, Sign.MAX);
		Objective o3 = new Objective(null, Sign.MAX);
		Objective o4 = new Objective(name, null);

		Assertions.assertEquals(o1.hashCode(), o2.hashCode());
		Assertions.assertNotEquals(o1.hashCode(), o3.hashCode());
		Assertions.assertNotEquals(o1.hashCode(), o4.hashCode());
	}

	@Test
	public void compareToTest() {
		Objective o1 = new Objective("obj1", Sign.MAX);
		Objective o11 = new Objective("obj1", Sign.MAX);
		Objective o2 = new Objective("obj2", Sign.MAX);

		Assertions.assertEquals(0, o1.compareTo(o11));
		Assertions.assertEquals(-1 * o1.compareTo(o2), o2.compareTo(o1));
		Assertions.assertEquals(1, o1.compareTo(null));
	}

	@Test
	public void toStringTest() {
		Objective o = new Objective("obj", Sign.MAX);
		Assertions.assertEquals("obj(MAX)", o.toString());
	}

}
