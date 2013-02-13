/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

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
