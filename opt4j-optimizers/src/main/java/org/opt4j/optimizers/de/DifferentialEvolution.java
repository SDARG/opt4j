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


package org.opt4j.optimizers.de;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.optimizer.IncompatibilityException;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.algebra.Add;
import org.opt4j.operators.algebra.Algebra;
import org.opt4j.operators.algebra.Index;
import org.opt4j.operators.algebra.Mult;
import org.opt4j.operators.algebra.Sub;
import org.opt4j.operators.algebra.Term;
import org.opt4j.operators.algebra.Var;
import org.opt4j.operators.crossover.Crossover;
import org.opt4j.operators.crossover.Pair;
import org.opt4j.optimizers.ea.Selector;

import com.google.inject.Inject;

/**
 * The {@link DifferentialEvolution}.
 * 
 * @author lukasiewycz
 * 
 */
public class DifferentialEvolution implements IterativeOptimizer {

	protected final double scalingFactor;

	protected final int alpha;

	protected final Algebra<Genotype> algebra;

	protected final Crossover<Genotype> crossover;

	protected final Selector selector;

	protected final Random random;

	private final IndividualFactory individualFactory;

	private final Population population;

	private final IndividualCompleter completer;

	private Term term;

	/**
	 * Constructs a {@link DifferentialEvolution}.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual factory
	 * @param completer
	 *            the completer
	 * @param algebra
	 *            the algebra operator
	 * @param selector
	 *            the selector
	 * @param random
	 *            the random number generator
	 * @param crossover
	 *            the crossover operator
	 * @param alpha
	 *            the population size
	 * @param scalingFactor
	 *            the scaling factor F
	 */
	@Inject
	public DifferentialEvolution(
			Population population,
			IndividualFactory individualFactory,
			IndividualCompleter completer,
			Algebra<Genotype> algebra,
			Selector selector,
			Rand random,
			Crossover<Genotype> crossover,
			@Constant(value = "alpha", namespace = DifferentialEvolution.class) int alpha,
			@Constant(value = "scalingFactor", namespace = DifferentialEvolution.class) double scalingFactor) {
		this.algebra = algebra;
		this.selector = selector;
		this.random = random;
		this.crossover = crossover;
		this.alpha = alpha;
		this.scalingFactor = scalingFactor;
		this.individualFactory = individualFactory;
		this.population = population;
		this.completer = completer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#initialize()
	 */
	@Override
	public void initialize() {
		Index i0 = new Index(0);
		Index i1 = new Index(1);
		Index i2 = new Index(2);
		Var c = new Var(scalingFactor);
		term = new Add(i0, new Mult(c, new Sub(i1, i2)));
		selector.init(2 * alpha);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#next()
	 */
	@Override
	public void next() throws TerminationException {
		if (population.isEmpty()) {
			firstIterationNext();
		} else {
			followingIterationsNext();
		}
	}

	private void firstIterationNext() {
		while (population.size() < alpha) {
			Individual individual = individualFactory.create();

			Genotype genotype = individual.getGenotype();
			if (!(genotype instanceof DoubleGenotype)) {
				throw new IncompatibilityException(
						"DifferentialEvolution is restricted to "
								+ DoubleGenotype.class
								+ ", current Genotype is: "
								+ genotype.getClass());
			}

			population.add(individual);
		}
	}

	private void followingIterationsNext() throws TerminationException {
		/*
		 * Map from each parent to its offspring
		 */
		Map<Individual, Individual> relation = new HashMap<Individual, Individual>();

		/*
		 * Create an offspring for each parent individual in the population and
		 * add the offspring to the population and evaluate their objectives
		 */
		List<Individual> list = new ArrayList<Individual>(population);
		for (Individual parent : population) {
			Individual offspring = createOffspring(parent, list, term);
			relation.put(parent, offspring);
		}
		population.addAll(relation.values());
		completer.complete(population);

		/*
		 * Remove those offspring individuals from the population that are
		 * dominated by their corresponding parent
		 */
		for (Entry<Individual, Individual> entry : relation.entrySet()) {
			Individual parent = entry.getKey();
			Individual offspring = entry.getValue();

			if (parent.getObjectives().weaklyDominates(
					offspring.getObjectives())) {
				population.remove(offspring);
			}
		}

		/*
		 * Truncate the population size to alpha
		 */
		Collection<Individual> lames = selector.getLames(population.size()
				- alpha, population);
		population.removeAll(lames);
	}

	protected Individual createOffspring(Individual parent,
			List<Individual> individuals, Term term) {
		Triple triple = getTriple(parent, individuals);

		Genotype g0 = triple.getFirst().getGenotype();
		Genotype g1 = triple.getSecond().getGenotype();
		Genotype g2 = triple.getThird().getGenotype();

		Genotype result = algebra.algebra(term, g0, g1, g2);
		Pair<Genotype> g = crossover.crossover(result, parent.getGenotype());

		Individual i;
		if (random.nextBoolean()) {
			i = individualFactory.create(g.getFirst());
		} else {
			i = individualFactory.create(g.getSecond());
		}
		return i;
	}

	/**
	 * The {@link Triple} is a container for three individuals.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected static class Triple {
		protected final Individual first;

		protected final Individual second;

		protected final Individual third;

		public Triple(final Individual first, final Individual second,
				final Individual third) {
			super();
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public Individual getFirst() {
			return first;
		}

		public Individual getSecond() {
			return second;
		}

		public Individual getThird() {
			return third;
		}
	}

	/**
	 * Returns three different {@link Individual}s from the {@code individuals}
	 * list. Each {@link Individual} is not equal to the parent.
	 * 
	 * @param parent
	 *            the parent Individual
	 * @param individuals
	 *            the population
	 * @return the three individuals as a Triple
	 */
	protected Triple getTriple(Individual parent, List<Individual> individuals) {
		individuals.remove(parent);
		Individual ind0 = individuals
				.remove(random.nextInt(individuals.size()));
		Individual ind1 = individuals
				.remove(random.nextInt(individuals.size()));
		Individual ind2 = individuals
				.remove(random.nextInt(individuals.size()));

		Triple triple = new Triple(ind0, ind1, ind2);

		individuals.add(parent);
		individuals.add(ind0);
		individuals.add(ind1);
		individuals.add(ind2);

		return triple;

	}

}
