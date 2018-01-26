package org.opt4j.core;

import org.junit.Assert;
import org.junit.Test;

public class ObjectivesTest {
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
}
