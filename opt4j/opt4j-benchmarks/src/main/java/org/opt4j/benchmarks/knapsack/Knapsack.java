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
package org.opt4j.benchmarks.knapsack;

import java.util.HashMap;
import java.util.Map;

/**
 * A knapsack has a given capacity.
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class Knapsack {

	protected final int id;

	protected Map<Item, Integer> profit = new HashMap<Item, Integer>();
	protected Map<Item, Integer> weight = new HashMap<Item, Integer>();
	protected double capacity = 0;

	/**
	 * Construct a knapsack
	 * 
	 * @param id
	 *            the id of the knapsack
	 */
	public Knapsack(int id) {
		super();
		this.id = id;
	}

	/**
	 * Sets the capacity of this knapsack.
	 * 
	 * @param capacity
	 *            the capacity
	 */
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	/**
	 * Returns the capacity of this knapsack.
	 * 
	 * @return the capacity
	 */
	public double getCapacity() {
		return capacity;
	}

	/**
	 * Set the profit of an item.
	 * 
	 * @param item
	 *            the item
	 * @param value
	 *            the profit
	 */
	public void setProfit(Item item, int value) {
		profit.put(item, value);
	}

	/**
	 * Get the profit of an item.
	 * 
	 * @param item
	 *            the item
	 * @return the profit
	 */
	public int getProfit(Item item) {
		return profit.get(item);
	}

	/**
	 * Set the weight of an item.
	 * 
	 * @param item
	 *            the item
	 * @param value
	 *            the weight
	 */
	public void setWeight(Item item, int value) {
		weight.put(item, value);
	}

	/**
	 * Get the weight of an item.
	 * 
	 * @param item
	 *            the item
	 * @return the weight
	 */
	public int getWeight(Item item) {
		return weight.get(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Knapsack" + id;
	}

}
