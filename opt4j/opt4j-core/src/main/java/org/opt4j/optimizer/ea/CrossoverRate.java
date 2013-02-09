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

package org.opt4j.optimizer.ea;

/**
 * The {@link CrossoverRate} is an interface for the crossover rate which is the
 * probability of two individuals undergoing crossover.
 * 
 * @author glass
 * 
 */
public interface CrossoverRate {

	/**
	 * Returns the crossover rate as a double value.
	 * 
	 * @return the crossover rate
	 */
	public double get();

	/**
	 * Sets the crossover rate to a double value.
	 * 
	 * @param value
	 *            the new crossover rate
	 */
	public void set(double value);

}
