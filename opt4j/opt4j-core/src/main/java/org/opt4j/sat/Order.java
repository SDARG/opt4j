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

package org.opt4j.sat;

/**
 * The abstract {@link Order} is the base class for a decision strategy.
 * 
 * Each time a variable or {@link Literal} is involved in a conflict, the offset
 * value {@code varInc} is added to its activity. Moreover, each time a conflict
 * occurs the offset value {@code varInc} is multiplied with the scaling value
 * {@code varDecay}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class Order {

	protected double varInc = 0;

	protected double varDecay = 1;

	/**
	 * Constructs an {@link Order}.
	 */
	public Order() {
		super();
	}

	/**
	 * Returns the scaling factor for the offset value.
	 * 
	 * @see #setVarDecay
	 * @return the scaling factor for the offset value
	 */
	public double getVarDecay() {
		return varDecay;
	}

	/**
	 * Sets the scaling factor for the offset value.
	 * 
	 * @see #getVarDecay
	 * @param varDecay
	 *            the scaling factor for the offset value to set
	 */
	public void setVarDecay(double varDecay) {
		this.varDecay = varDecay;
	}

	/**
	 * Returns the initial offset value.
	 * 
	 * @see #setVarInc
	 * @return the initial offset value
	 */
	public double getVarInc() {
		return varInc;
	}

	/**
	 * Sets the initial offset value.
	 * 
	 * @see #getVarInc
	 * @param varInc
	 *            the initial offset value to set
	 */
	public void setVarInc(double varInc) {
		this.varInc = varInc;
	}

}
