/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/


package org.opt4j.sat;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.satdecoding.Constraint;
import org.opt4j.satdecoding.Literal;
import org.opt4j.satdecoding.Constraint.Operator;

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
