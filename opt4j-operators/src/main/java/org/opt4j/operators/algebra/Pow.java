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


package org.opt4j.operators.algebra;

/**
 * The {@link Pow} performs an exponentiation of two {@link Term}s.
 * 
 * @author lukasiewycz
 * 
 */
public class Pow implements Term {

	protected final Term base;

	protected final Term exponent;

	/**
	 * Constructs a {@link Pow} term.
	 * 
	 * @param base
	 *            the base
	 * @param exponent
	 *            the exponent
	 */
	public Pow(final Term base, final Term exponent) {
		super();
		this.base = base;
		this.exponent = exponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.algebra.Term#calculate(double[])
	 */
	@Override
	public double calculate(double... values) {
		return Math.pow(base.calculate(values), exponent.calculate(values));
	}

}
