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
 * A {@link Literal} is a data structure that represents a variable and its
 * phase.
 * 
 * @author lukasiewycz
 * 
 */
public class Literal {
	private final Object variable;

	private final boolean phase;

	/**
	 * Constructs a {@link Literal} with a variable and their phase.
	 * 
	 * @param variable
	 *            the variable
	 * @param phase
	 *            the phase ({@code true} means not negated)
	 */
	public Literal(Object variable, boolean phase) {
		this.variable = variable;
		this.phase = phase;
	}

	/**
	 * Returns the variable of this {@link Literal}.
	 * 
	 * @return the variable of this literal
	 */
	public Object variable() {
		return variable;
	}

	/**
	 * Returns the phase of this {@link Literal}.
	 * 
	 * @return {@code true} if the literal is not negated, {@code false} if the
	 *         literal is negated
	 */
	public boolean phase() {
		return phase;
	}

	/**
	 * Returns the negated {@link Literal}.
	 * 
	 * @return the negated literal
	 */
	public Literal negate() {
		return new Literal(variable, !phase);
	}

	/**
	 * Returns a hash code value for this {@link Literal}.
	 * 
	 * @return a hash code value for this literal
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (phase ? 1231 : 1237);
		result = PRIME * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	/**
	 * Compares the specified {@link Object} with this for equality. Returns
	 * {@code true} if the objects are equal and {@code false} if they are not
	 * equal, respectively.
	 * 
	 * @param obj
	 *            the reference object with which to compare
	 * @return {@code true} if the objects are equal and {@code false} if they
	 *         are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Literal other = (Literal) obj;
		if (phase != other.phase) {
			return false;
		}
		if (variable == null) {
			if (other.variable != null) {
				return false;
			}
		} else if (!variable.equals(other.variable)) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a {@link String} representation of this {@link Literal}.
	 * 
	 * @return a string representation of this literal
	 */
	@Override
	public String toString() {
		return ((phase) ? "" : "~") + variable;
	}

}
