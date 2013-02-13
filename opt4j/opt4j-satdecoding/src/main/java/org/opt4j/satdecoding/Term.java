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
