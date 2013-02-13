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

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link Iteration} object is used as iteration counter for the
 * optimization.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class Iteration {

	protected int value = 0;
	protected final int maxIterations;

	/**
	 * Constructs a {@link Iteration} object.
	 * 
	 * @param maxIterations
	 *            the maximal number of iterations
	 */
	@Inject
	public Iteration(@MaxIterations int maxIterations) {
		this.maxIterations = maxIterations;
	}

	/**
	 * Returns the current iteration value.
	 * 
	 * @return the current iteration value
	 */
	public int value() {
		return value;
	}

	/**
	 * Increases the iteration value by 1.
	 */
	public void next() {
		value++;
	}

	/**
	 * Returns the maximal number of iterations.
	 * 
	 * @return the maximal number of iterations
	 */
	public int max() {
		return maxIterations;
	}

}
