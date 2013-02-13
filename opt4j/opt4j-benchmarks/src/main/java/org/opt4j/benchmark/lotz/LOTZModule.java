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

package org.opt4j.benchmark.lotz;

import org.opt4j.config.annotations.Info;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.start.Constant;

/**
 * <p>
 * The {@link LOTZModule} for the "Leading Ones Trailing Zeros" optimization
 * problem. This is a 2-dimensional optimization problem:
 * </p>
 * 
 * <p>
 * <ol>
 * <li>maximize the consecutive ones from the beginning of a binary string</li>
 * <li>maximize the consecutive ones from the end of a binary string</li>
 * </ol>
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@Info("The 2-dimensional \"Leading Ones Trailing Zeros\" optimization problem.")
public class LOTZModule extends ProblemModule {

	@Info("The length of the binary string.")
	@Constant(value = "size", namespace = LOTZCreator.class)
	protected int size = 30;

	/**
	 * Returns the size of the binary string.
	 * 
	 * @return the size of the binary string
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size of the binary string.
	 * 
	 * @param size
	 *            the size of the binary string to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindProblem(LOTZCreator.class, LOTZDecoder.class, LOTZEvaluator.class);
	}

}
