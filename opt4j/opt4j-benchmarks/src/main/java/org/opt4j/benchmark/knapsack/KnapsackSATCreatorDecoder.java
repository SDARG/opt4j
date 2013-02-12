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
import java.util.Set;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Genotype;
import org.opt4j.sat.AbstractSATDecoder;
import org.opt4j.sat.Constraint;
import org.opt4j.sat.Constraint.Operator;
import org.opt4j.sat.Literal;
import org.opt4j.sat.Model;
import org.opt4j.sat.SATManager;

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
