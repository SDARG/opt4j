package org.opt4j.core;

import org.junit.Assert;
import org.junit.Test;

public class IntegerValueTest {

	@Test
	public void getIntegerTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Assert.assertEquals(1, v.getDouble(), 001);
	}

	@Test
	public void getValueTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Assert.assertEquals(d, v.getValue());
	}

	@Test
	public void setValueTest() {
		Integer d = 1;
		IntegerValue v = new IntegerValue(d);

		Integer d2 = 2;
		v.setValue(d2);

		Assert.assertEquals(d2, v.getValue());
	}

	@Test
	public void compareToTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue dnull1 = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		IntegerValue d11 = new IntegerValue(1);
		IntegerValue d2 = new IntegerValue(2);

		Assert.assertEquals(0, dnull.compareTo(dnull1));
		Assert.assertEquals(0, d1.compareTo(d11));
		Assert.assertEquals(dnull, dnull1);
		Assert.assertEquals(d1, d11);
		Assert.assertTrue(dnull.compareTo(d1) > 0);
		Assert.assertTrue(d1.compareTo(dnull) < 0);
		Assert.assertTrue(d1.compareTo(d2) < 0);
		Assert.assertTrue(d2.compareTo(d1) > 0);
	}

	@Test
	public void hashCodeTest() {
		IntegerValue dnull = new IntegerValue(null);
		Integer d = 1;
		IntegerValue d1 = new IntegerValue(d);

		Assert.assertEquals(31, dnull.hashCode());
		Assert.assertEquals(31 + d.hashCode(), d1.hashCode());
	}

	@Test
	public void equalsTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue dnull1 = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		IntegerValue d11 = new IntegerValue(1);
		IntegerValue d2 = new IntegerValue(2);

		Assert.assertEquals(d1, d1);
		Assert.assertEquals(d1, d11);
		Assert.assertEquals(dnull, dnull1);
		Assert.assertNotEquals(dnull, d1);
		Assert.assertNotEquals(d1, null);
		Assert.assertNotEquals(d1, "a string");
		Assert.assertNotEquals(d1, dnull);
		Assert.assertNotEquals(d1, d2);
	}

	@Test
	public void toStringTest() {
		IntegerValue dnull = new IntegerValue(null);
		IntegerValue d1 = new IntegerValue(1);
		Assert.assertEquals("null", dnull.toString());
		Assert.assertEquals("1", d1.toString());
	}
}
