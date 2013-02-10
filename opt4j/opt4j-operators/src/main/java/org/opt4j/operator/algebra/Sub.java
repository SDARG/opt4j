/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.operator.algebra;

/**
 * The {@link Sub} term subtracts two {@link Term}s.
 * 
 * @author lukasiewycz
 * 
 */
public class Sub implements Term {

	protected final Term minuend;

	protected final Term subtrahend;

	/**
	 * Constructs a {@link Sub} term.
	 * 
	 * @param minuend
	 *            the minuend
	 * @param subtrahend
	 *            the subtrahend
	 */
	public Sub(final Term minuend, final Term subtrahend) {
		super();
		this.minuend = minuend;
		this.subtrahend = subtrahend;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.algebra.Term#calculate(double[])
	 */
	@Override
	public double calculate(double... values) {
		return minuend.calculate(values) - subtrahend.calculate(values);
	}

}
