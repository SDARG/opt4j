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

package org.opt4j.optimizer.mopso;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.operator.algebra.Add;
import org.opt4j.operator.algebra.Index;
import org.opt4j.operator.algebra.Mult;
import org.opt4j.operator.algebra.Sub;
import org.opt4j.operator.algebra.Term;
import org.opt4j.operator.algebra.Var;

/**
 * The {@link VelocityTerm} is used to determine the new velocity of a
 * {@link Particle}.
 * 
 * @author lukasiewycz
 * 
 */
public class VelocityTerm implements Term {

	protected final Term term;
	protected final Random random;

	protected final Var W = new Var();
	protected final Var C1 = new Var();
	protected final Var C2 = new Var();
	protected final Var r1 = new Var();
	protected final Var r2 = new Var();

	/**
	 * Constructs a {@link VelocityTerm}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public VelocityTerm(Rand random) {
		this.random = random;

		Index x = new Index(0);
		Index v = new Index(1);
		Index xbest = new Index(2);
		Index xleader = new Index(3);

		Term m1 = new Mult(W, v);
		Term m2 = new Mult(C1, r1, new Sub(xbest, x));
		Term m3 = new Mult(C2, r2, new Sub(xleader, x));

		term = new Add(m1, m2, m3);

		randomize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.algebra.Term#calculate(double[])
	 */
	@Override
	public double calculate(double... values) {
		return term.calculate(values);
	}

	/**
	 * Randomizes the constants of the term.
	 */
	public void randomize() {
		W.setValue(random.nextDouble() * 0.4 + 0.1);
		C1.setValue(random.nextDouble() * 0.5 + 1.5);
		C2.setValue(random.nextDouble() * 0.5 + 1.5);
		r1.setValue(random.nextDouble());
		r2.setValue(random.nextDouble());
	}

}
