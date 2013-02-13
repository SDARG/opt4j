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
 * Thrown if a contradiction is recognized.
 * 
 * @author lukasiewycz
 * 
 */
public class ContradictionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link ContradictionException}.
	 */
	public ContradictionException() {
		super();
	}

	/**
	 * Constructs a {@link ContradictionException} with a message.
	 * 
	 * @param message
	 *            the error message
	 */
	public ContradictionException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@link ContradictionException}.
	 */
	public ContradictionException(String message, Throwable exception) {
		super(message, exception);
	}

	/**
	 * Constructs a {@link ContradictionException}.
	 */
	public ContradictionException(Throwable exception) {
		super(exception);
	}
}