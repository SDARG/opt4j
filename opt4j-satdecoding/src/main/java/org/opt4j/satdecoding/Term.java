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
 * The {@link Term} is an element of the linear {@link Constraint}. The
 * {@link Term} is an integer coefficient times a {@link Literal}.
 * 
 * @author lukasiewycz
 * 
 */
public class Term {

	protected final int coeff;
	protected final Literal lit;

	/**
	 * Constructs a {@link Term}.
	 * 
	 * @param coeff
	 *            the integer coefficient
	 * @param lit
	 *            the literal must not be {@code null}
	 */
	public Term(int coeff, Literal lit) {
		this.coeff = coeff;
		this.lit = lit;
		if (lit == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Returns the coefficient.
	 * 
	 * @return the coefficient
	 */
	public int getCoefficient() {
		return coeff;
	}

	/**
	 * Returns the literal.
	 * 
	 * @return the literal
	 */
	public Literal getLiteral() {
		return lit;
	}

	/**
	 * Constructs a copy to this term.
	 * 
	 * @return a copy of this term
	 */
	public Term copy() {
		return new Term(coeff, lit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return coeff + " " + lit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coeff;
		result = prime * result + ((lit == null) ? 0 : lit.hashCode());
		return result;
	}

	/**
	 * A {@link Term} equals another {@link Term} if they have equal
	 * {@link Literal}s and coefficients. This method checks, if this
	 * {@link Term} equals the given {@code other} {@link Object}.
	 * 
	 * @param other
	 *            the other object
	 * @return {@code true} if both are equal; false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (getClass() != other.getClass()) {
			return false;
		}
		Term otherTerm = (Term) other;
		if (coeff != otherTerm.coeff) {
			return false;
		}
		if (lit == null) {
			if (otherTerm.lit != null) {
				return false;
			}
		} else if (!lit.equals(otherTerm.lit)) {
			return false;
		}
		return true;
	}
}
