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

package org.opt4j.operator.algebra;

/**
 * The {@link Var} returns a double value.
 * 
 * @author lukasiewycz
 * 
 */
public class Var implements Term {

	protected double value;

	/**
	 * Constructs a {@link Var} with the initial value 0.
	 */
	public Var() {
		this(0);
	}

	/**
	 * Constructs a {@link Var} with a given {@code value}.
	 * 
	 * @param value
	 *            the value
	 */
	public Var(double value) {
		super();
		this.value = value;
	}

	/**
	 * Returns the value.
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.algebra.Term#calculate(double[])
	 */
	@Override
	public double calculate(double... values) {
		return value;
	}

}
