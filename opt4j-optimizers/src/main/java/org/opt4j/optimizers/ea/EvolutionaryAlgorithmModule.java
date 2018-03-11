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

import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

/**
 * The {@link EvolutionaryAlgorithmModule} configures the
 * {@link EvolutionaryAlgorithm}.
 * 
 * @author lukasiewycz
 * 
 */

@Info("Multi-Objective Evolutionary Algorithm that performs a Crossover and Mutate for variation and uses a Selector for the environmental selection.")
public class EvolutionaryAlgorithmModule extends OptimizerModule {

	@Info("The number of generations.")
	@Order(0)
	@MaxIterations
	protected int generations = 1000;

	@Constant(value = "alpha", namespace = EvolutionaryAlgorithm.class)
	@Info("Alpha - The size of the population.")
	@Order(1)
	protected int populationSize = 100;

	@Constant(value = "mu", namespace = EvolutionaryAlgorithm.class)
	@Info("Mu - The number of parents per generation.")
	@Order(2)
	protected int parentsPerGeneration = 25;

	@Constant(value = "lambda", namespace = EvolutionaryAlgorithm.class)
	@Info("Lambda The number of offsprings per generation.")
	@Order(3)
	protected int offspringsPerGeneration = 25;

	@Info("Performs a crossover operation with this given rate.")
	@Order(4)
	@Constant(value = "rate", namespace = ConstantCrossoverRate.class)
	protected double crossoverRate = 0.95;

	@Ignore
	protected CrossoverRateType crossoverRateType = CrossoverRateType.CONSTANT;

	/**
	 * The {@link CrossoverRateType} allows to choose between different types of
	 * crossover rates.
	 * 
	 * @author glass
	 * 
	 */
	public enum CrossoverRateType {
		/**
		 * Use a constant crossover rate.
		 */
		CONSTANT;
	}

	/**
	 * Returns the population size {@code alpha}.
	 * 
	 * @return the population size
	 */
	public int getPopulationSize() {
		return populationSize;
	}

	/**
	 * Sets the population size {@code alpha}.
	 * 
	 * @param alpha
	 *            the population size to set
	 */
	public void setPopulationSize(int alpha) {
		this.populationSize = alpha;
	}

	/**
	 * Returns the number of generations.
	 * 
	 * @return the number of generations
	 */
	public int getGenerations() {
		return generations;
	}

	/**
	 * Sets the number of generations.
	 * 
	 * @see #getGenerations
	 * @param generations
	 *            the number of generations
	 */
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	/**
	 * Returns the number of children {@code lambda}.
	 * 
	 * @return the number of children
	 */
	public int getOffspringsPerGeneration() {
		return offspringsPerGeneration;
	}

	/**
	 * Sets the number of children {@code lambda}.
	 * 
	 * @param lambda
	 *            the number of children
	 */
	public void setOffspringsPerGeneration(int lambda) {
		this.offspringsPerGeneration = lambda;
	}

	/**
	 * Returns the number of parents {@code mu}.
	 * 
	 * @return the number of parents
	 */
	public int getParentsPerGeneration() {
		return parentsPerGeneration;
	}

	/**
	 * Sets the number of parents {@code mu}.
	 * 
	 * @param mu
	 *            the number of parents
	 */
	public void setParentsPerGeneration(int mu) {
		this.parentsPerGeneration = mu;
	}

	/**
	 * Returns the type of crossover rate that is used.
	 * 
	 * @return the crossoverRateType
	 */
	public CrossoverRateType getCrossoverRateType() {
		return crossoverRateType;
	}

	/**
	 * Sets the type of crossover rate to use.
	 * 
	 * @param crossoverRateType
	 *            the crossoverRateType to set
	 */
	public void setCrossoverRateType(CrossoverRateType crossoverRateType) {
		this.crossoverRateType = crossoverRateType;
	}

	/**
	 * Returns the used crossover rate.
	 * 
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * Sets the crossover rate.
	 * 
	 * @param crossoverRate
	 *            the crossoverRate to set
	 */
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindIterativeOptimizer(EvolutionaryAlgorithm.class);
		bind(CrossoverRate.class).to(ConstantCrossoverRate.class).in(SINGLETON);
	}
}
