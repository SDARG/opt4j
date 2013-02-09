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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.opt4j.common.random.RandomMersenneTwister;
import org.opt4j.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link KnapsackProblemRandom} defines an instance of a {@link KnapsackProblem} that is initialized randomly. It
 * initializes profit and weight values which are randomly created according to Zitzler and Thiele 1999. This means,
 * each value/knapsack pair is uniformly distributed in [10,100].
 * 
 * @see "E. Zitzler and L. Thiele: Multiobjective evolutionary algorithms: A comparative case study and the strength
 *      Pareto approach. IEEE Transactions on Evolutionary Computation, vol. 3, no. 4, pp. 257-271, Nov. 1999."
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
