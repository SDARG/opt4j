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
package org.opt4j.benchmark.knapsack;

import java.util.Collection;

/**
 * The {@link KnapsackProblem} defines the basic problem, i.e., the collection
 * of {@link Item} and {@link Knapsack} elements, respectively.
 * 
 * @author lukasiewycz
 * 
 */
public interface KnapsackProblem {

	/**
	 * Returns the items.
	 * 
	 * @return the items
	 */
	public Collection<Item> getItems();

	/**
	 * Returns the knapsacks.
	 * 
	 * @return the knapsacks.
	 */
	public Collection<Knapsack> getKnapsacks();

}
