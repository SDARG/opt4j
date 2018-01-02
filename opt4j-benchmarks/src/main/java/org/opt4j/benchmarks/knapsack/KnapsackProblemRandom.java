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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.opt4j.core.common.random.RandomMersenneTwister;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link KnapsackProblemRandom}, see "E. Zitzler and L. Thiele:
 * Multiobjective evolutionary algorithms: A comparative case study and the
 * strength Pareto approach. IEEE Transactions on Evolutionary Computation, vol.
 * 3, no. 4, pp. 257-271, Nov. 1999.", defines an instance of a
 * {@link KnapsackProblem} that is initialized randomly. It initializes profit
 * and weight values which are randomly created according to Zitzler and Thiele
 * 1999. This means, each value/knapsack pair is uniformly distributed in
 * [10,100].
 * 
 * @author reimann, lukasiewycz
 * 
 */
@Singleton
public class KnapsackProblemRandom implements KnapsackProblem {

	protected final List<Knapsack> knapsacks = new ArrayList<Knapsack>();
	protected final List<Item> items = new ArrayList<Item>();

	/**
	 * Creates a new {@link KnapsackProblemRandom}.
	 * 
	 * @param items
	 *            the number of items
	 * @param knapsacks
	 *            the number of knapsacks
	 * @param seed
	 *            the seed for the initialization
	 */
	@Inject
	public KnapsackProblemRandom(@Constant(value = "items", namespace = KnapsackProblemRandom.class) int items,
			@Constant(value = "knapsacks", namespace = KnapsackProblemRandom.class) int knapsacks,
			@Constant(value = "seed", namespace = KnapsackProblemRandom.class) int seed) {

		for (int i = 0; i < items; i++) {
			this.items.add(new Item(i + 1));
		}

		Random random = new RandomMersenneTwister(seed);

		for (int k = 0; k < knapsacks; k++) {
			Knapsack knapsack = new Knapsack(k + 1);

			double capacity = 0;

			for (Item item : this.items) {
				int profit = random.nextInt(91) + 10;
				int weight = random.nextInt(91) + 10;

				knapsack.setProfit(item, profit);
				knapsack.setWeight(item, weight);

				capacity += 0.5 * weight;
			}
			knapsack.setCapacity(capacity);

			this.knapsacks.add(knapsack);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.knapsack.KnapsackProblem#getKnapsacks()
	 */
	@Override
	public Collection<Knapsack> getKnapsacks() {
		return knapsacks;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.benchmark.knapsack.KnapsackProblem#getItems()
	 */
	@Override
	public Collection<Item> getItems() {
		return items;
	}

}
