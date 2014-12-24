/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

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
