/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
package org.opt4j.benchmark.knapsack;

import java.util.HashSet;

/**
 * The set of selected items.
 * 
 * @author reimann
 * 
 */
public class ItemSelection extends HashSet<Item> {
	private static final long serialVersionUID = 1L;

	private boolean feasible = false;

	/**
	 * The {@link ItemSelection} is feasible if no knapsack is overloaded.
	 * 
	 * @return true if the selection is feasible
	 */
	public boolean isFeasible() {
		return feasible;
	}

	/**
	 * Sets the feasibility of the {@link ItemSelection}.
	 * 
	 * @param feasible
	 *            true if the selection is feasible, false otherwise
	 */
	public void setFeasible(boolean feasible) {
		this.feasible = feasible;
	}
}
