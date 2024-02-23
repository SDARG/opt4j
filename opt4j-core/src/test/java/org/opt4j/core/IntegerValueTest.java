package org.opt4j.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IntegerValueTest {

	@Test
	public void getIntegerTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Assertions.assertEquals(1, v.getDouble(), 001);
	}

	@Test
	public void getValueTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Assertions.assertEquals(d, v.getValue());
	}

	@Test
	public void setValueTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Integer d2 = 2;
		v.setValue(d2);

		Assertions.assertEquals(d2, v.getValue());
	}

	@Test
	public void compareToTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue dnull1 = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		IntegerValue d11 = new IntegerValue(1);
		IntegerValue d2 = new IntegerValue(2);

		Assertions.assertEquals(0, dnull.compareTo(dnull1));
		Assertions.assertEquals(0, d1.compareTo(d11));
		Assertions.assertEquals(dnull, dnull1);
		Assertions.assertEquals(d1, d11);
		Assertions.assertTrue(dnull.compareTo(d1) > 0);
		Assertions.assertTrue(d1.compareTo(dnull) < 0);
		Assertions.assertTrue(d1.compareTo(d2) < 0);
		Assertions.assertTrue(d2.compareTo(d1) > 0);
	}

	@Test
	public void hashCodeTest() {
		IntegerValue dnull = new IntegerValue(null);
		Integer d = 1;
		IntegerValue d1 = new IntegerValue(d);

		Assertions.assertEquals(31, dnull.hashCode());
		Assertions.assertEquals(31 + d.hashCode(), d1.hashCode());
	}

	@Test
	public void equalsTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue dnull1 = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		IntegerValue d11 = new IntegerValue(1);
		IntegerValue d2 = new IntegerValue(2);

		Assertions.assertEquals(d1, d1);
		Assertions.assertEquals(d1, d11);
		Assertions.assertEquals(dnull, dnull1);
		Assertions.assertNotEquals(dnull, d1);
		Assertions.assertNotEquals(d1, null);
		Assertions.assertNotEquals(d1, "a string");
		Assertions.assertNotEquals(d1, dnull);
		Assertions.assertNotEquals(d1, d2);
	}

	@Test
	public void toStringTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		Assertions.assertEquals("null", dnull.toString());
		Assertions.assertEquals("1", d1.toString());
	}
}
