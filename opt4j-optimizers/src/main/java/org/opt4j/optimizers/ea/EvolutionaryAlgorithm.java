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

package org.opt4j.optimizers.ea;

import java.util.Collection;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.crossover.Crossover;
import org.opt4j.operators.mutate.Mutate;

import com.google.inject.Inject;

/**
 * The {@link EvolutionaryAlgorithm} is an implementation of an Evolutionary
 * Algorithm based on the operators {@link Crossover} and {@link Mutate}. It
 * uses a {@link Selector} for the mating and environmental selection.
 * 
 * @author lukasiewycz, glass
 * 
 */
public class EvolutionaryAlgorithm implements IterativeOptimizer {

	protected final int alpha;

	protected final int lambda;

	protected final int mu;

	protected final Selector selector;

	protected final Mating mating;

	private final IndividualFactory individualFactory;

	private final Population population;

	private final IndividualCompleter completer;

	/**
	 * Constructs an {@link EvolutionaryAlgorithm} with a {@link Population}, an
	 * {@link IndividualFactory}, a {@link IndividualCompleter}, a
	 * {@link Selector}, a {@link Mating}, the number of generations, the
	 * population size, the number of parents, the number of offspring, and a
	 * random number generator.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual factory
	 * @param completer
	 *            the completer
	 * @param selector
	 *            the selector
	 * @param mating
	 *            the mating
	 * @param alpha
	 *            the population size
	 * @param mu
	 *            the number of parents
	 * @param lambda
	 *            the number of offspring
	 */
	@Inject
	public EvolutionaryAlgorithm(
			Population population,
			IndividualFactory individualFactory,
			IndividualCompleter completer,
			Selector selector,
			Mating mating,
			@Constant(value = "alpha", namespace = EvolutionaryAlgorithm.class) int alpha,
			@Constant(value = "mu", namespace = EvolutionaryAlgorithm.class) int mu,
			@Constant(value = "lambda", namespace = EvolutionaryAlgorithm.class) int lambda) {
		this.selector = selector;
		this.mating = mating;
		this.alpha = alpha;
		this.mu = mu;
		this.lambda = lambda;
		this.individualFactory = individualFactory;
		this.population = population;
		this.completer = completer;

		if (alpha <= 0) {
			throw new IllegalArgumentException("Invalid alpha: " + alpha);
		}

		if (mu <= 0) {
			throw new IllegalArgumentException("Invalid mu: " + mu);
		}

		if (lambda <= 0) {
			throw new IllegalArgumentException("Invalid lambda: " + lambda);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#initialize()
	 */
	@Override
	public void initialize() {
		selector.init(alpha + lambda);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#next()
	 */
	@Override
	public void next() throws TerminationException {
		// remove lames
		if (population.size() > alpha) {
			Collection<Individual> lames = selector.getLames(population.size()
					- alpha, population);
			population.removeAll(lames);
		}
		// set the number of new offspring individuals per generation
		int offspringCount = lambda;

		// 1) fill the population until it reaches the size alpha
		while (population.size() < alpha && offspringCount > 0) {
			population.add(individualFactory.create());
			offspringCount--;
		}

		// 2) generate offspring by mating
		if (offspringCount > 0) {
			if (offspringCount < lambda) { // evaluate new individuals first
				completer.complete(population);
			}

			Collection<Individual> parents = selector
					.getParents(mu, population);
			Collection<Individual> offspring = mating.getOffspring(
					offspringCount, parents);
			population.addAll(offspring);
		}
	}
}
