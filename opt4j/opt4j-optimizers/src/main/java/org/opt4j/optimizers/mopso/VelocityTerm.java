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
 

package org.opt4j.optimizers.mopso;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.operators.algebra.Add;
import org.opt4j.operators.algebra.Index;
import org.opt4j.operators.algebra.Mult;
import org.opt4j.operators.algebra.Sub;
import org.opt4j.operators.algebra.Term;
import org.opt4j.operators.algebra.Var;

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
