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

package org.opt4j.satdecoding;

import java.util.List;
import java.util.Map;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.BooleanMapGenotype;
import org.opt4j.core.genotype.DoubleBounds;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.genotype.DoubleMapGenotype;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link MixedSATManager} encodes the decision strategy into two vectors:
 * One binary vector for the phase and one double vector for the priority.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class MixedSATManager implements SATManager {

	protected final Solver solver;

	/**
	 * Constructs a {@link MixedSATManager}.
	 * 
	 * @param solver
	 *            the solver
	 */
	@Inject
	public MixedSATManager(Solver solver) {
		this.solver = solver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.SATManager#createSATGenotype(java.util.List,
	 * java.util.Map, java.util.Map)
	 */
	@Override
	public Genotype createSATGenotype(List<Object> variables, Map<Object, Double> lowerBounds,
			Map<Object, Double> upperBounds, Map<Object, Double> priorities, Map<Object, Boolean> phases) {

		int size = variables.size();

		double[] lower = new double[size];
		double[] upper = new double[size];

		for (int i = 0; i < size; i++) {
			Object variable = variables.get(i);
			Double lb = lowerBounds.get(variable);
			Double ub = upperBounds.get(variable);

			if (lb == null) {
				lb = 0.0;
			}
			if (ub == null) {
				ub = 1.0;
			}
			lower[i] = lb;
			upper[i] = ub;
		}

		DoubleBounds doubleBounds = new DoubleBounds(lower, upper);
		DoubleGenotype doubleVector = new DoubleMapGenotype<Object>(variables, doubleBounds);
		BooleanGenotype booleanVector = new BooleanMapGenotype<Object>(variables);

		SATGenotype satGenotype = new SATGenotype(booleanVector, doubleVector);

		for (Object variable : variables) {
			double priority = priorities.get(variable);
			boolean phase = phases.get(variable);

			doubleVector.add(priority);
			booleanVector.add(phase);
		}

		return satGenotype;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.SATManager#decodeSATGenotype(java.util.List,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public Model decodeSATGenotype(List<Object> variables, Genotype genotype) {
		SATGenotype satGenotype = (SATGenotype) genotype;

		BooleanGenotype booleanVector = satGenotype.getBooleanVector();
		DoubleGenotype doubleVector = satGenotype.getDoubleVector();

		VarOrder varorder = new VarOrder();

		for (int i = 0; i < variables.size(); i++) {
			Object var = variables.get(i);
			varorder.setActivity(var, doubleVector.get(i));
			varorder.setPhase(var, booleanVector.get(i));
		}
		varorder.setVarInc(1.0 / (2.0 * variables.size()));
		varorder.setVarDecay(1.0 / 0.95);

		Model model = null;
		try {
			model = solver.solve(varorder);
			if (model == null) {
				throw new ContradictionException("no satisfying solution left");
			}
		} catch (TimeoutException e) {
			System.err.println("timeout");
		}
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.SATManager#getSolver()
	 */
	@Override
	public Solver getSolver() {
		return solver;
	}

}
