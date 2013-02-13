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

package org.opt4j.operators.algebra;

/**
 * The {@link Mult} term multiplies multiple {@link Term}s.
 * 
 * @author lukasiewycz
 * 
 */
public class Mult implements Term {

	protected final Term[] terms;

	/**
	 * Constructs an {@link Mult} term.
	 * 
	 * @param terms
	 *            the terms to be multiplied
	 */
	public Mult(final Term... terms) {
		super();
		this.terms = terms;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.algebra.Term#calculate(double[])
	 */
	@Override
	public double calculate(double... values) {
		double mul = 1.0;
		for (Term term : terms) {
			mul *= term.calculate(values);
		}
		return mul;
	}

}