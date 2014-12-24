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
 

package org.opt4j.satdecoding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.Genotype;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

/**
 * The {@link AbstractSATDecoder} can be used for the construction of custom SAT
 * {@link Decoder}s.
 * <p>
 * The {@link Creator} part takes the problem dependent {@link Constraint}s and
 * creates a {@link SATGenotype} according to the variables which is the input
 * for the specified {@link Optimizer}.
 * <p>
 * The {@link Decoder} takes a {@link SATGenotype}, converts it to an
 * {@link Order} of the variables, and solves the problem in the {@link Order}
 * using a SAT {@link Solver} . The resulting {@link Model}, which respects the
 * {@link Constraint}s, must then be converted to the problem specific
 * phenotype.
 * 
 * @author lukasiewycz
 * 
 * @param <G>
 *            The genotype (use {@link Genotype} if you do not override the
 *            decode method)
 * @param <P>
 *            The phenotype
 */
public abstract class AbstractSATDecoder<G extends Genotype, P extends Object> implements Decoder<G, P>, Creator<G> {

	private final List<Constraint> constraints = new ArrayList<Constraint>();
	private final List<Object> variables = new ArrayList<Object>();

	protected Map<Object, Double> lowerBounds;

	protected Map<Object, Double> upperBounds;

	protected final Random random;

	protected final SATManager manager;

	protected boolean isInit = false;

	/**
	 * Constructs an {@link AbstractSATDecoder} with the given
	 * {@link SATManager}.
	 * 
	 * @param manager
	 *            the specified sat manager
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public AbstractSATDecoder(SATManager manager, Rand random) {
		this.random = random;
		this.manager = manager;
	}

	/**
	 * Initializes the {@link Constraint}s and variables.
	 */
	protected synchronized void init() {

		if (!isInit) {
			try {
				Solver solver = manager.getSolver();

				Set<Constraint> constraints = createConstraints();

				Set<Object> variables = new HashSet<Object>();

				for (Constraint constraint : constraints) {
					solver.addConstraint(constraint);
					this.constraints.add(constraint);

					for (Literal literal : constraint.getLiterals()) {
						variables.add(literal.variable());
					}
				}

				Set<Object> ignores = ignoreVariables(variables);

				variables.removeAll(ignores);
				this.variables.addAll(variables);

				lowerBounds = getLowerBounds(variables);
				upperBounds = getUpperBounds(variables);

			} catch (Throwable e) {
				System.err.println("Failed initialization of " + getClass().getName() + " with " + e);
				throw new RuntimeException(e);
			} finally {
				isInit = true;
			}

		}
	}

	/**
	 * Creates all {@link Constraint}s.
	 * <p>
	 * Override this method to return the problem specific {@link Constraint}s
	 * that have to be respected by the resulting {@link Model}.
	 * 
	 * @return the set of all constraints of the given problem
	 * 
	 */
	public abstract Set<Constraint> createConstraints();

	/**
	 * Creates a random initialization of the priorities and phases for the
	 * variables. Override this method for a custom initialization.
	 * 
	 * @param variables
	 *            all variables
	 * @param lowerBounds
	 *            the lower bounds for the priority for the variables
	 * @param upperBounds
	 *            the upper bounds for the priority for the variables
	 * @param priorities
	 *            the priorities of the variables
	 * @param phases
	 *            the phases of the variables
	 */
	public void randomize(Collection<Object> variables, Map<Object, Double> lowerBounds,
			Map<Object, Double> upperBounds, Map<Object, Double> priorities, Map<Object, Boolean> phases) {
		for (Object variable : variables) {
			Double lb = lowerBounds.get(variable);
			Double ub = upperBounds.get(variable);
			if (lb == null) {
				lb = 0.0;
			}
			if (ub == null) {
				ub = 1.0;
			}

			double priority = lb + (ub - lb) * random.nextDouble();

			priorities.put(variable, priority);
			phases.put(variable, random.nextBoolean());
		}
	}

	/**
	 * Set the variables to be ignored (not part of the search process and
	 * genotype). Override this method if variables are ignored.
	 * 
	 * @param variables
	 *            all variables of the problem
	 * @return the variables to be ignored
	 */
	public Set<Object> ignoreVariables(Set<Object> variables) {
		return new HashSet<Object>();
	}

	/**
	 * Returns the lower bounds for the variables.
	 * 
	 * @param variables
	 *            all variables of the problem
	 * @return the lower bounds
	 */
	public Map<Object, Double> getLowerBounds(Set<Object> variables) {
		return new HashMap<Object, Double>();
	}

	/**
	 * Returns the upper bounds for the variables.
	 * 
	 * @param variables
	 *            all variables of the problem
	 * @return the upper bounds
	 */
	public Map<Object, Double> getUpperBounds(Set<Object> variables) {
		return new HashMap<Object, Double>();
	}

	/**
	 * Converts a {@link Model} into a phenotype. Override this with your custom
	 * method.
	 * 
	 * @param model
	 *            the found model
	 * @return the converted phenotype
	 */
	public abstract P convertModel(Model model);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Creator#create()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public G create() {
		return (G) createSATGenotype();
	}

	/**
	 * Returns a random SAT genotype of the constrained problem.
	 * 
	 * @return a random SAT genotype
	 */
	protected Genotype createSATGenotype() {
		if (!isInit) {
			init();
		}

		Map<Object, Double> priorities = new HashMap<Object, Double>();
		Map<Object, Boolean> phases = new HashMap<Object, Boolean>();

		randomize(variables, lowerBounds, upperBounds, priorities, phases);

		return manager.createSATGenotype(variables, lowerBounds, upperBounds, priorities, phases);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Decoder#decode(org.opt4j.core.Genotype)
	 */
	@Override
	public P decode(G genotype) {
		Model model = decodeSATGenotype(genotype);
		assert model != null;

		return convertModel(model);
	}

	/**
	 * Decodes the {@link Genotype} to a phenotype by using a SAT/PB solver.
	 * 
	 * @param genotype
	 *            the genotype
	 * @return the phenotype
	 */
	protected Model decodeSATGenotype(Genotype genotype) {
		if (!isInit) {
			init();
		}

		return manager.decodeSATGenotype(variables, genotype);
	}

	/**
	 * Returns the constraints.
	 * 
	 * @return the constraints
	 */
	public List<Constraint> getConstraints() {
		if (!isInit) {
			init();
		}
		return constraints;
	}

	/**
	 * Returns the variables.
	 * 
	 * @return the variables
	 */
	public List<Object> getVariables() {
		if (!isInit) {
			init();
		}
		return variables;
	}

}
