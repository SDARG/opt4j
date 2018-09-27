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

package org.opt4j.optimizers.mopso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.archive.Crowding;
import org.opt4j.core.common.archive.CrowdingArchive;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.optimizer.IncompatibilityException;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.algebra.Add;
import org.opt4j.operators.algebra.AlgebraDouble;
import org.opt4j.operators.algebra.Index;
import org.opt4j.operators.algebra.Term;
import org.opt4j.operators.mutate.MutationRate;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link MOPSO} is an implementation of a multi-objective particle swarm
 * optimizer, see "Improving PSO-based Multi-Objective Optimization using
 * Crowding, Mutation and e-Dominance, M. Reyes Sierra and C. A. Coello Coello,
 * In Proceedings of Evolutionary Multi-Criterion Optimization, 2005". This
 * implementation is based on the OMOPSO. Thus, this MOPSO is restricted to
 * problems that are based on the {@link DoubleGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class MOPSO implements IterativeOptimizer {

	protected final int size;

	protected final Archive leaders;

	protected final ParticleFactory particleFactory;

	protected final AlgebraDouble algebra;

	protected final Random random;

	protected final VelocityTerm velocityTerm;

	protected final Term positionTerm;

	protected final MutateDoubleUniform uniform;

	protected final MutateDoubleUniform nonUniform;

	protected final MutationRate mutationRate;

	private final Population population;

	private final IndividualCompleter completer;

	/**
	 * Constructs a {@link MOPSO}.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual (particle) factory
	 * @param completer
	 *            the completer
	 * @param random
	 *            the random number generator
	 * @param uniform
	 *            the uniform mutation
	 * @param nonUniform
	 *            the non-uniform mutation
	 * @param mutationRate
	 *            the mutation rate
	 * @param size
	 *            the number of particles
	 * @param archiveSize
	 *            the size of the archive for the global leaders
	 */
	@Inject
	public MOPSO(Population population, IndividualFactory individualFactory, IndividualCompleter completer, Rand random,
			MutateDoubleUniform uniform, MutateDoubleNonUniform nonUniform, MutationRate mutationRate,
			@Constant(value = "size", namespace = MOPSO.class) int size,
			@Constant(value = "archiveSize", namespace = MOPSO.class) int archiveSize) {
		this.particleFactory = (ParticleFactory) individualFactory;
		this.leaders = new CrowdingArchive(archiveSize);
		this.random = random;
		this.uniform = uniform;
		this.nonUniform = nonUniform;
		this.mutationRate = mutationRate;
		this.population = population;
		this.completer = completer;

		this.size = size;

		this.algebra = new AlgebraDouble(new NormalizeDouble() {
			@Override
			public void normalize(DoubleGenotype genotype) {
				// do nothing
			}
		});

		Index x = new Index(0);
		Index v = new Index(1);
		positionTerm = new Add(x, v);
		velocityTerm = new VelocityTerm(random);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#initialize()
	 */
	@Override
	public void initialize() {
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
			int id = 0;
			while (population.size() < size) {
				Particle particle = particleFactory.create();
				particle.setId(id++);
				Genotype genotype = particle.getGenotype();
				if (!(genotype instanceof DoubleGenotype)) {
					throw new IncompatibilityException("MOPSO is restricted to " + DoubleGenotype.class
							+ ", current Genotype is: " + genotype.getClass());
				}
				population.add(particle);
			}
		} else {
			// all iterations > 1
			// determine the leaders
			updateLeaders(leaders, population);

			// determine one leader for each particle
			Map<Particle, Particle> lead = getLeaders(leaders, population);
			// determine the next position of each particle
			Map<Particle, Particle> next = move(population, lead);

			population.addAll(next.values());
			completer.complete(population);

			// update the personal best of each particle
			updatePersonalBest(next);

			// remove the old positions
			population.removeAll(next.keySet());
		}
	}

	/**
	 * Determine the new positions for the {@link Particle}s in the
	 * {@link Population}.
	 * 
	 * @param population
	 *            the population
	 * @param leaders
	 *            the map for the global leaders
	 * @return the map of the old to the new particle
	 */
	protected Map<Particle, Particle> move(Population population, Map<Particle, Particle> leaders) {
		Map<Particle, Particle> map = new HashMap<>();

		for (Individual individual : population) {

			Particle particle = (Particle) individual;

			DoubleGenotype position = (DoubleGenotype) particle.getGenotype();
			DoubleGenotype velocity = (DoubleGenotype) particle.getVelocity();
			DoubleGenotype best = (DoubleGenotype) particle.getBest();
			int id = particle.getId();

			DoubleGenotype leader = (DoubleGenotype) leaders.get(particle).getGenotype();

			velocityTerm.randomize();

			DoubleGenotype nextVelocity = algebra.algebra(velocityTerm, position, velocity, best, leader);

			DoubleGenotype nextPosition = algebra.algebra(positionTerm, position, nextVelocity);

			for (int k = 0; k < nextPosition.size(); k++) {
				double value = nextPosition.get(k);
				double lb = nextPosition.getLowerBound(k);
				double ub = nextPosition.getUpperBound(k);
				if (value < lb) {
					nextPosition.set(k, lb);
					nextVelocity.set(k, -nextVelocity.get(k));
				} else if (value > ub) {
					nextPosition.set(k, ub);
					nextVelocity.set(k, -nextVelocity.get(k));
				}
			}

			if (id % 3 == 0) {
				uniform.mutate(nextPosition, mutationRate.get());
			} else if (id % 3 == 1) {
				nonUniform.mutate(nextPosition, mutationRate.get());
			} // else do nothing

			Particle p = particleFactory.create(id, nextPosition, nextVelocity);

			map.put(particle, p);
		}

		return map;
	}

	/**
	 * Update the global leaders {@link Archive}.
	 * 
	 * @param leaders
	 *            the archive
	 * @param population
	 *            the population
	 */
	protected void updateLeaders(Archive leaders, Population population) {
		leaders.update(population);
	}

	/**
	 * Update the personal best of each {@link Particle}.
	 * 
	 * @param next
	 *            the old and new positions
	 */
	protected void updatePersonalBest(Map<Particle, Particle> next) {
		for (Entry<Particle, Particle> entry : next.entrySet()) {

			Particle old = entry.getKey();
			Particle current = entry.getValue();

			if (dominates(old, current)) {
				current.setBest(old.getBest(), old.getBestObjectives());
			} else {
				current.setBest(current.getGenotype(), current.getObjectives());
			}
		}
	}

	/**
	 * Returns {@code true} if the old particle position dominates the new one.
	 * 
	 * @param old
	 *            the old particle
	 * @param current
	 *            the new particle
	 * @return {@code true} if the old particle position dominates the new one
	 */
	protected boolean dominates(Particle old, Particle current) {
		if (old.getBest() == null) {
			return false;
		}

		Objectives oldObjectives = old.getBestObjectives();
		Objectives currentObjectives = current.getObjectives();
		return oldObjectives.dominates(currentObjectives);
	}

	/**
	 * Assigns each {@link Particle} a leader.
	 * 
	 * @param leaders
	 *            the archive of available leader
	 * @param population
	 *            the population
	 * @return the map of each particle to its leader
	 */
	protected Map<Particle, Particle> getLeaders(Archive leaders, Population population) {
		Map<Particle, Particle> map = new HashMap<>();

		Crowding crowding = new Crowding();
		Map<Individual, Double> values = crowding.getDensityValues(leaders);

		List<Individual> l = new ArrayList<>(leaders);
		List<Individual> best = new ArrayList<>();

		for (int i = 0; i < population.size(); i++) {
			Individual i1 = l.get(random.nextInt(l.size()));
			Individual i2 = l.get(random.nextInt(l.size()));

			if (values.get(i1) >= values.get(i2)) {
				best.add(i1);
			} else {
				best.add(i2);
			}
		}

		int i = 0;

		for (Individual individual : population) {
			Particle particle = (Particle) individual;
			Particle leader = (Particle) best.get(i++);
			map.put(particle, leader);
		}

		return map;
	}

}
