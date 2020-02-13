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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

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

	protected Solver solver;

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
		DoubleGenotype doubleVector = new DoubleMapGenotype<>(variables, doubleBounds);
		BooleanGenotype booleanVector = new BooleanMapGenotype<>(variables);

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
