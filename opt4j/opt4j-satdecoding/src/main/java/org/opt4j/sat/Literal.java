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
