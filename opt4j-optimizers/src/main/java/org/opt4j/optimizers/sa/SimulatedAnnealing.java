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

package org.opt4j.optimizers.sa;

import java.util.Random;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.Iteration;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.operators.copy.Copy;
import org.opt4j.operators.neighbor.Neighbor;

import com.google.inject.Inject;

/**
 * The {@link SimulatedAnnealing} is a standard implementation of the
 * optimization heuristic. This heuristic optimizes a single objective
 * (multi-objective problems are optimized by the sum of all objectives).
 * 
 * @author lukasiewycz
 * 
 */
public class SimulatedAnnealing implements IterativeOptimizer {

	protected final Random random;

	protected final Neighbor<Genotype> neighbor;

	protected final Copy<Genotype> copy;

	protected final CoolingSchedule coolingSchedule;

	private final IndividualFactory individualFactory;

	private final IndividualCompleter completer;

	private final Population population;

	private final Archive archive;

	private final Iteration iteration;

	private double fold;

	private Individual old;

	/**
	 * Constructs a new {@link SimulatedAnnealing}.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual factory
	 * @param completer
	 *            the completer
	 * @param random
	 *            the random number generator
	 * @param neighbor
	 *            the neighbor operator
	 * @param copy
	 *            the copy operator
	 * @param iteration
	 *            the iteration counter
	 * @param coolingSchedule
	 *            the cooling schedule
	 */
	@Inject
	public SimulatedAnnealing(Population population, Archive archive, IndividualFactory individualFactory,
			IndividualCompleter completer, Rand random, Neighbor<Genotype> neighbor, Copy<Genotype> copy,
			CoolingSchedule coolingSchedule, Iteration iteration) {
		this.random = random;
		this.neighbor = neighbor;
		this.copy = copy;
		this.coolingSchedule = coolingSchedule;
		this.individualFactory = individualFactory;
		this.population = population;
		this.completer = completer;
		this.archive = archive;
		this.iteration = iteration;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#initialize()
	 */
	@Override
	public void initialize() throws TerminationException {
		// nothing to be done
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#next()
	 */
	@Override
	public void next() throws TerminationException {

		if (population.isEmpty()) {
			// the first iteration

			old = individualFactory.create();

			population.add(old);
			completer.complete(population);

			fold = f(old);
		} else {
			// all iterations > 1

			Genotype g = copy.copy(old.getGenotype());
			neighbor.neighbor(g);

			Individual y = individualFactory.create(g);

			completer.complete(y);
			archive.update(y);

			double fy = f(y);
			// boolean value that indicates a switch of the individuals
			boolean sw = false;

			if (fy <= fold) {
				sw = true;
			} else {
				double a = (fold - fy) / coolingSchedule.getTemperature(iteration.value(), iteration.max());
				double e = Math.exp(a);
				if (random.nextDouble() < e) {
					sw = true;
				}
			}

			if (sw) {
				population.remove(old);
				population.add(y);
				fold = fy;
				old = y;
			}

		}
	}

	/**
	 * Calculates the sum of the {@link Objectives} of one {@link Individual}.
	 * 
	 * @param individual
	 *            the individual
	 * @return the sum of the objective values
	 */
	protected double f(Individual individual) {
		Objectives objectives = individual.getObjectives();

		double sum = 0;

		for (double d : objectives.array()) {
			sum += d;
		}

		return sum;
	}

}
