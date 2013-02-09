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

package org.opt4j.optimizer.ea;

import org.opt4j.config.annotations.Ignore;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.start.Constant;

/**
 * The {@link EvolutionaryAlgorithmModule} configures the {@link EvolutionaryAlgorithm}.
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
	@Info("The size of the population.")
	@Order(1)
	protected int alpha = 100;

	@Constant(value = "mu", namespace = EvolutionaryAlgorithm.class)
	@Info("The number of parents per generation.")
	@Order(2)
	protected int mu = 25;

	@Constant(value = "lambda", namespace = EvolutionaryAlgorithm.class)
	@Info("The number of offspring per generation.")
	@Order(3)
	protected int lambda = 25;

	@Info("Performs a crossover operation with this given rate.")
	@Order(4)
	@Constant(value = "rate", namespace = ConstantCrossoverRate.class)
	protected double crossoverRate = 0.95;

	@Ignore
	protected CrossoverRateType crossoverRateType = CrossoverRateType.CONSTANT;

	/**
	 * The {@link CrossoverRateType} allows to choose between different types of crossover rates.
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
	 * @see #setAlpha
	 * @return the population size
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * Sets the population size {@code alpha}.
	 * 
	 * @see #getAlpha
	 * @param alpha
	 *            the population size to set
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the number of generations.
	 * 
	 * @see #setGenerations
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
	 * @see #setLambda
	 * @return the number of children
	 */
	public int getLambda() {
		return lambda;
	}

	/**
	 * Sets the number of children {@code lambda}.
	 * 
	 * @see #getLambda
	 * @param lambda
	 *            the number of children
	 */
	public void setLambda(int lambda) {
		this.lambda = lambda;
	}

	/**
	 * Returns the number of parents {@code mu}.
	 * 
	 * @see #setMu
	 * @return the number of parents
	 */
	public int getMu() {
		return mu;
	}

	/**
	 * Sets the number of parents {@code mu}.
	 * 
	 * @see #getMu
	 * @param mu
	 *            the number of parents
	 */
	public void setMu(int mu) {
		this.mu = mu;
	}

	/**
	 * Returns the type of crossover rate that is used.
	 * 
	 * @see #setCrossoverRateType
	 * @return the crossoverRateType
	 */
	public CrossoverRateType getCrossoverRateType() {
		return crossoverRateType;
	}

	/**
	 * Sets the type of crossover rate to use.
	 * 
	 * @see #getCrossoverRateType
	 * @param crossoverRateType
	 *            the crossoverRateType to set
	 */
	public void setCrossoverRateType(CrossoverRateType crossoverRateType) {
		this.crossoverRateType = crossoverRateType;
	}

	/**
	 * Returns the used crossover rate.
	 * 
	 * @see #setCrossoverRate
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * Sets the crossover rate.
	 * 
	 * @see #getCrossoverRate
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
