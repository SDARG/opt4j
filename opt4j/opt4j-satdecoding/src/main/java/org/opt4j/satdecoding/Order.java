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


package org.opt4j.satdecoding;

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
