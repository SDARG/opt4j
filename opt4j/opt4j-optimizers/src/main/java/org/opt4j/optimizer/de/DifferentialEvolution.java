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

package org.opt4j.optimizer.de;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.operator.algebra.Add;
import org.opt4j.operator.algebra.Algebra;
import org.opt4j.operator.algebra.Index;
import org.opt4j.operator.algebra.Mult;
import org.opt4j.operator.algebra.Sub;
import org.opt4j.operator.algebra.Term;
import org.opt4j.operator.algebra.Var;
import org.opt4j.operator.crossover.Crossover;
import org.opt4j.operator.crossover.Pair;
import org.opt4j.optimizer.ea.Selector;
import org.opt4j.start.Constant;

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
	public DifferentialEvolution(Population population, IndividualFactory individualFactory,
			IndividualCompleter completer, Algebra<Genotype> algebra, Selector selector, Rand random,
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

	@Override
	public void initialize() {
		Index i0 = new Index(0);
		Index i1 = new Index(1);
		Index i2 = new Index(2);
		Var c = new Var(scalingFactor);

		term = new Add(i0, new Mult(c, new Sub(i1, i2)));

		selector.init(2 * alpha);
		while (population.size() < alpha) {
			population.add(individualFactory.create());
		}
	}

	private Term term;

	@Override
	public void next() throws TerminationException {
		/*
		 * Map from each parent to its offspring
		 */
		Map<Individual, Individual> relation = new HashMap<Individual, Individual>();

		/*
		 * Create an offspring for each parent individual in the population and add the offspring to the population and
		 * evaluate their objectives
		 */
		List<Individual> list = new ArrayList<Individual>(population);
		for (Individual parent : population) {
			Individual offspring = createOffspring(parent, list, term);
			relation.put(parent, offspring);
		}
		population.addAll(relation.values());
		completer.complete(population);

		/*
		 * Remove those offspring individuals from the population that are dominated by their corresponding parent
		 */
		for (Entry<Individual, Individual> entry : relation.entrySet()) {
			Individual parent = entry.getKey();
			Individual offspring = entry.getValue();

			if (parent.getObjectives().weaklyDominates(offspring.getObjectives())) {
				population.remove(offspring);
			}
		}

		/*
		 * Truncate the population size to alpha
		 */
		Collection<Individual> lames = selector.getLames(population.size() - alpha, population);
		population.removeAll(lames);
	}

	protected Individual createOffspring(Individual parent, List<Individual> individuals, Term term) {
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

		public Triple(final Individual first, final Individual second, final Individual third) {
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
	 * Returns three different {@link Individual}s from the {@code individuals} list. Each {@link Individual} is not
	 * equal to the parent.
	 * 
	 * @param parent
	 *            the parent Individual
	 * @param individuals
	 *            the population
	 * @return the three individuals as a Triple
	 */
	protected Triple getTriple(Individual parent, List<Individual> individuals) {
		individuals.remove(parent);
		Individual ind0 = individuals.remove(random.nextInt(individuals.size()));
		Individual ind1 = individuals.remove(random.nextInt(individuals.size()));
		Individual ind2 = individuals.remove(random.nextInt(individuals.size()));

		Triple triple = new Triple(ind0, ind1, ind2);

		individuals.add(parent);
		individuals.add(ind0);
		individuals.add(ind1);
		individuals.add(ind2);

		return triple;

	}

}
