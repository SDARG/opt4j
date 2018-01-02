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

import static java.lang.Math.max;
import static org.opt4j.core.Objective.Sign.MAX;
import static org.opt4j.core.Objective.Sign.MIN;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.Priority;

import com.google.inject.Inject;

/**
 * The {@link KnapsackOverloadEvaluator} evaluates a given {@link ItemSelection}
 * using one {@link Objective} to sum up all knapsack overloads and one
 * {@link Objective} for the profit of each knapsack.
 * 
 * @author reimann, lukasiewycz
 * 
 */
@Priority(-10)
public class KnapsackOverloadEvaluator implements Evaluator<ItemSelection> {
	protected Objective objectiveOverload = new Objective("overload", MIN);
	protected final Map<Knapsack, Objective> profitObjectives = new HashMap<Knapsack, Objective>();
	protected KnapsackProblem problem;

	/**
	 * Creates a new {@link KnapsackOverloadEvaluator}.
	 * 
	 * @param problem
	 *            the problem instance
	 */
	@Inject
	public KnapsackOverloadEvaluator(KnapsackProblem problem) {
		this.problem = problem;
		int i = 0;
		for (Knapsack knapsack : problem.getKnapsacks()) {
			profitObjectives.put(knapsack, new Objective("profit" + i++, MAX));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(ItemSelection itemSelection) {
		// evaluate overlead
		double overload = 0;
		for (Knapsack knapsack : problem.getKnapsacks()) {
			overload += getOverload(knapsack, itemSelection);
		}

		Objectives objectives = new Objectives();
		objectives.add(objectiveOverload, overload);
		itemSelection.setFeasible(overload == 0);
		return objectives;
	}

	/**
	 * Computes how much the given item exceed the defined capacity of the
	 * knapsack.
	 * 
	 * @param knapsack
	 *            the knapsack
	 * @param items
	 *            the items
	 * @return the overload
	 */
	protected double getOverload(Knapsack knapsack, Collection<Item> items) {
		double weight = 0;
		for (Item i : items) {
			weight += knapsack.getWeight(i);
		}
		return max(0, weight - knapsack.getCapacity());
	}
}
