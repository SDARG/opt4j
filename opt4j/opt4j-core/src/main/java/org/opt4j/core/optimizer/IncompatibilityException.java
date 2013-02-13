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
package org.opt4j.core.optimizer;

/**
 * The {@link IncompatibilityException}. This exception is thrown if a problem,
 * optimizer, operator, etc. are not compatible.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class IncompatibilityException extends RuntimeException {

	/**
	 * Constructs a {@link IncompatibilityException}.
	 * 
	 */
	public IncompatibilityException() {
		super();
	}

	/**
	 * Constructs a {@link IncompatibilityException}.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public IncompatibilityException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a {@link IncompatibilityException}.
	 * 
	 * @param message
	 *            the message
	 */
	public IncompatibilityException(String message) {
		super(message);
	}

	/**
	 * Constructs a {@link IncompatibilityException}.
	 * 
	 * @param cause
	 *            the cause
	 */
	public IncompatibilityException(Throwable cause) {
		super(cause);
	}

}
