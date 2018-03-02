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

	public enum MoeaType{
		NSGA2, AeSeH
	}
	
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
	
	protected MoeaType moeaType = MoeaType.NSGA2;
	
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
	
	public MoeaType getMoeaType() {
		return moeaType;
	}

	public void setMoeaType(MoeaType moeaType) {
		this.moeaType = moeaType;
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
		if(moeaType.equals(MoeaType.AeSeH)){
			bind(Selector.class).to(AeSeHSelector.class);
			bind(Coupler.class).to(AeSeHCoupler.class);
		}
	}
}
