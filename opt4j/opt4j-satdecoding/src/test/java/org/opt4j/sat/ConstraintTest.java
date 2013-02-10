package org.opt4j.sat;


import org.junit.Assert;
import org.junit.Test;
import org.opt4j.sat.Constraint.Operator;

public class ConstraintTest {

	@Test
	public void equalityTestOfOperator() {
		Object object1 = "literal1";
		Object object2 = "literal2";

		Constraint c1 = new Constraint(Operator.LE, 2);
		c1.add(2, new Literal(object1, true));
		c1.add(2, new Literal(object2, true));

		Constraint c2 = new Constraint(Operator.GE, 2);
		c2.add(2, new Literal(object1, true));
		c2.add(2, new Literal(object2, true));

		Constraint c3 = new Constraint(Operator.EQ, 2);
		c3.add(2, new Literal(object1, true));
		c3.add(2, new Literal(object2, true));

		Assert.assertFalse(c1.equals(c2));
		Assert.assertFalse(c1.equals(c3));
		Assert.assertFalse(c2.equals(c3));
	}

	@Test
	public void equalityTestOfRhs() {
		Object object1 = "literal1";
		Object object2 = "literal2";

		Constraint c1 = new Constraint(Operator.LE, 0);
		c1.add(2, new Literal(object1, true));
		c1.add(2, new Literal(object2, true));

		Constraint c2 = new Constraint(Operator.LE, 2);
		c2.add(2, new Literal(object1, true));
		c2.add(2, new Literal(object2, true));

		Assert.assertFalse(c1.equals(c2));
	}

	@Test
	public void equalityTestOfLiterals() {
		Object object1 = "literal1";
		Object object2 = "literal2";

		Constraint c1 = new Constraint(Operator.LE, 2);
		c1.add(2, new Literal(object1, true));
		c1.add(2, new Literal(object2, false));

		Constraint c2 = new Constraint(Operator.LE, 2);
		c2.add(2, new Literal(object1, true));
		c2.add(2, new Literal(object2, true));

		Assert.assertFalse(c1.equals(c2));
	}

	@Test
	public void equalityTestOfRhs2() {
		Constraint c1 = new Constraint(">=", 1);
		c1.add(2, new Literal("papa", true));
		Constraint c2 = new Constraint("<=", 1);
		c2.add(2, new Literal("lala", true));

		Assert.assertFalse(c1.equals(c2)); // returns true, should be false
	}
}
