package org.opt4j.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DoubleValueTest {

	@Test
	public void getDoubleTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Assertions.assertTrue(d == v.getDouble());
	}

	@Test
	public void getValueTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Assertions.assertEquals(d, v.getValue());
	}

	@Test
	public void setValueTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Double d2 = 2.0;
		v.setValue(d2);

		Assertions.assertEquals(d2, v.getValue());
	}

	@Test
	public void compareToTest() {
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue dnull1 = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		DoubleValue d11 = new DoubleValue(1.0);
		DoubleValue d2 = new DoubleValue(2.0);

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
		DoubleValue dnull = new DoubleValue(null);
		Double d = 1.0;
		DoubleValue d1 = new DoubleValue(d);

		Assertions.assertEquals(31, dnull.hashCode());
		Assertions.assertEquals(31 + d.hashCode(), d1.hashCode());
	}

	@Test
	public void equalsTest() {
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue dnull1 = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		DoubleValue d11 = new DoubleValue(1.0);
		DoubleValue d2 = new DoubleValue(2.0);

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
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		Assertions.assertEquals("null", dnull.toString());
		Assertions.assertEquals("1.0", d1.toString());
	}
}
