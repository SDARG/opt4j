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

import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.Genotype;
import org.opt4j.core.common.random.Rand;
import org.opt4j.satdecoding.AbstractSATDecoder;
import org.opt4j.satdecoding.Constraint;
import org.opt4j.satdecoding.Literal;
import org.opt4j.satdecoding.Model;
import org.opt4j.satdecoding.SATManager;
import org.opt4j.satdecoding.Constraint.Operator;

import com.google.inject.Inject;

/**
 * Use SAT decoding to solve knapsack problem.
 * 
 * @author reimann
 * 
 */
public class KnapsackSATCreatorDecoder extends AbstractSATDecoder<Genotype, ItemSelection> {

	private final KnapsackProblem problem;

	/**
	 * Creates a new {@link KnapsackSATCreatorDecoder}.
	 * 
	 * @param manager
	 *            the sat manager
	 * @param problem
	 *            the problem instance
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public KnapsackSATCreatorDecoder(SATManager manager, KnapsackProblem problem, Rand random) {
		super(manager, random);
		this.problem = problem;
	}

	@Override
	public Set<Constraint> createConstraints() {
		Set<Constraint> constraints = new HashSet<Constraint>();

		for (Knapsack knapsack : problem.getKnapsacks()) {
			Constraint constraint = new Constraint(Operator.LE, (int) Math.floor(knapsack.getCapacity()));
			for (Item item : problem.getItems()) {
				constraint.add(knapsack.getWeight(item), new Literal(item, true));
			}
			constraints.add(constraint);
		}

		return constraints;
	}

	@Override
	public ItemSelection convertModel(Model model) {
		ItemSelection itemSelection = new ItemSelection();
		for (Item item : problem.getItems()) {
			if (model.get(item)) {
				itemSelection.add(item);
			}
		}
		return itemSelection;
	}

}
