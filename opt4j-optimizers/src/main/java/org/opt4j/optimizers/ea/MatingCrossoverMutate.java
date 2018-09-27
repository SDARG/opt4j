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

package org.opt4j.optimizers.ea;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.common.random.Rand;
import org.opt4j.operators.copy.Copy;
import org.opt4j.operators.crossover.Crossover;
import org.opt4j.operators.crossover.Pair;
import org.opt4j.operators.mutate.Mutate;
import org.opt4j.operators.mutate.MutationRate;

import com.google.inject.Inject;

/**
 * The {@link MatingCrossoverMutate} creates offspring from a given set of
 * parents by using {@link Crossover} and {@code Mutate}.
 * 
 * @author glass, lukasiewycz
 * 
 */
public class MatingCrossoverMutate implements Mating {

	protected final Crossover<Genotype> crossover;
	protected final Mutate<Genotype> mutate;
	protected final Copy<Genotype> copy;
	protected final Coupler coupler;
	protected final CrossoverRate crossoverRate;
	protected final MutationRate mutationRate;
	protected final Random random;
	protected final IndividualFactory individualFactory;

	/**
	 * Constructs a {@link MatingCrossoverMutate} with a given {@link Crossover}
	 * , {@link Mutate}, {@link Copy}, {@link Coupler}, {@link CrossoverRate},
	 * {@link Rand}, and {@link IndividualFactory}.
	 * 
	 * @param crossover
	 *            the crossover operator
	 * @param mutate
	 *            the mutate operator
	 * @param copy
	 *            the copy operator
	 * @param coupler
	 *            the coupler
	 * @param crossoverRate
	 *            the used crossover rate
	 * @param mutationRate
	 *            the mutation rate
	 * @param random
	 *            the random number generator
	 * @param individualFactory
	 *            the individual factory
	 */
	@Inject
	public MatingCrossoverMutate(Crossover<Genotype> crossover, Mutate<Genotype> mutate, Copy<Genotype> copy,
			Coupler coupler, CrossoverRate crossoverRate, MutationRate mutationRate, Rand random,
			IndividualFactory individualFactory) {
		super();
		this.crossover = crossover;
		this.mutate = mutate;
		this.copy = copy;
		this.coupler = coupler;
		this.crossoverRate = crossoverRate;
		this.mutationRate = mutationRate;
		this.random = random;
		this.individualFactory = individualFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Mating#getOffspring(int,
	 * org.opt4j.core.Individual[])
	 */
	@Override
	public Collection<Individual> getOffspring(int size, Individual... parents) {
		return getOffspringInternal(size, Arrays.asList(parents));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.ea.Mating#getOffspring(int,
	 * java.util.Collection)
	 */
	@Override
	public Collection<Individual> getOffspring(int size, Collection<Individual> parents) {
		return getOffspringInternal(size, parents);
	}

	/**
	 * Creates offspring from a given set of parents.
	 * 
	 * The {@link Coupler} is used to create pairs of parents, which are mated
	 * using the {@link Mutate} and, depending on the {@link CrossoverRate}, the
	 * {@link Crossover} operator.
	 * 
	 * @param size
	 *            the number of individuals to create
	 * @param parents
	 *            the parents
	 * @return the offspring
	 */
	protected Collection<Individual> getOffspringInternal(int size, Collection<Individual> parents) {
		Collection<Individual> offspring = new ArrayList<>();
		Collection<Pair<Individual>> couples = coupler.getCouples((int) Math.ceil(((double) size / 2)),
				new ArrayList<>(parents));

		for (Pair<Individual> couple : couples) {
			boolean crossover = random.nextDouble() <= crossoverRate.get();
			Individual parent1 = couple.getFirst();
			Individual parent2 = couple.getSecond();
			Pair<Individual> i = mate(parent1, parent2, crossover);
			Individual i1 = i.getFirst();
			Individual i2 = i.getSecond();

			offspring.add(i1);
			if (offspring.size() < size) {
				offspring.add(i2);
			}
		}

		return offspring;
	}

	/**
	 * Performs the actual {@link Coupler} process of two parents.
	 * 
	 * @param parent1
	 *            parent one
	 * @param parent2
	 *            parent two
	 * @param doCrossover
	 *            indicates whether the coupler shall take place
	 * @return the two offspring individuals
	 */
	protected Pair<Individual> mate(Individual parent1, Individual parent2, boolean doCrossover) {
		Genotype p1 = parent1.getGenotype();
		Genotype p2 = parent2.getGenotype();
		Genotype o1;
		Genotype o2;

		if (doCrossover) {
			Pair<Genotype> offspring = crossover.crossover(p1, p2);
			o1 = offspring.getFirst();
			o2 = offspring.getSecond();
		} else {
			o1 = copy.copy(p1);
			o2 = copy.copy(p2);
		}

		mutate.mutate(o1, mutationRate.get());
		mutate.mutate(o2, mutationRate.get());

		Individual i1 = individualFactory.create(o1);
		Individual i2 = individualFactory.create(o2);

		Pair<Individual> individuals = new Pair<>(i1, i2);
		return individuals;
	}

}
