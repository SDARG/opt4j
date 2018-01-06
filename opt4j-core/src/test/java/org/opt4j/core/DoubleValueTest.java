package org.opt4j.core;

import org.junit.Assert;
import org.junit.Test;

public class DoubleValueTest {

	@Test
	public void getDoubleTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Assert.assertTrue(d == v.getDouble());
	}

	@Test
	public void getValueTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Assert.assertEquals(d, v.getValue());
	}

	@Test
	public void setValueTest() {
		Double d = 1.0;
		DoubleValue v = new DoubleValue(d);

		Double d2 = 2.0;
		v.setValue(d2);

		Assert.assertEquals(d2, v.getValue());
	}

	@Test
	public void compareToTest() {
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue dnull1 = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		DoubleValue d11 = new DoubleValue(1.0);
		DoubleValue d2 = new DoubleValue(2.0);

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
		DoubleValue dnull = new DoubleValue(null);
		Double d = 1.0;
		DoubleValue d1 = new DoubleValue(d);

		Assert.assertEquals(31, dnull.hashCode());
		Assert.assertEquals(31 + d.hashCode(), d1.hashCode());
	}

	@Test
	public void equalsTest() {
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue dnull1 = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		DoubleValue d11 = new DoubleValue(1.0);
		DoubleValue d2 = new DoubleValue(2.0);

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
		DoubleValue dnull = new DoubleValue(null);
		DoubleValue d1 = new DoubleValue(1.0);
		Assert.assertEquals("null", dnull.toString());
		Assert.assertEquals("1.0", d1.toString());
	}
}
