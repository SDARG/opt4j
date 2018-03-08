package org.opt4j.optimizers.ea;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

/**
 * Module to bind the AeSeH evolutionary algorithm as optimizer. This algorithm
 * was first introduced in the paper:
 * 
 * Aguirre, Hernán, Akira Oyama, and Kiyoshi Tanaka. "Adaptive ε-sampling and
 * ε-hood for evolutionary many-objective optimization." International
 * Conference on Evolutionary Multi-Criterion Optimization. Springer, Berlin,
 * Heidelberg, 2013.
 * 
 * Please consider citing the paper if you use this class for a scientific
 * publication.
 * 
 * @author Fedor Smirnov
 *
 */

@Info("Multi-objective evolutionary algorithm where the survival selection and the creation of neighborhoods is based on epsilon-dominance. The selection of parents is done within the created neighborhoods.")
public class AeSeHModule extends OptimizerModule {

	@Info("The number of generations.")
	@Order(0)
	@MaxIterations
	protected int generations = 1000;

	@Constant(value = "alpha", namespace = EvolutionaryAlgorithm.class)
	@Info("Alph - The size of the population.")
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

	@Info("The start value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSample", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonSample = 0.0;

	@Info("The start value used for the adaption of the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDelta", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonSampleDelta = 0.005;

	@Info("The maximal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMax", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonSampleDeltaMax = 0.005;

	@Info("The minimal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMin", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonSampleDeltaMin = 0.0001;

	@Info("The start value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhood", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonNeighborhood = 0.0;

	@Info("The start value used for the adaptation of the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDelta", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonNeighborhoodDelta = 0.005;

	@Info("The maximal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonNeighborhoodDeltaMax = 0.005;

	@Info("The minimal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = DefaultEpsilonAdaptation.class)
	protected double epsilonNeighborhoodDeltaMin = 0.0001;

	@Info("The reference value for the number of neighborhoods. The epsilon_h value is adapted to create a similar number of neighborhoods.")
	@Constant(value = "neighborhoodNumber", namespace = AeSeHCoupler.class)
	protected int neighborhoodNumber = 5;

	public int getNeighborhoodNumber() {
		return neighborhoodNumber;
	}

	public void setNeighborhoodNumber(int neighborhoodNumber) {
		this.neighborhoodNumber = neighborhoodNumber;
	}

	public double getEpsilonSample() {
		return epsilonSample;
	}

	public void setEpsilonSample(double epsilonSample) {
		this.epsilonSample = epsilonSample;
	}

	public double getEpsilonSampleDelta() {
		return epsilonSampleDelta;
	}

	public void setEpsilonSampleDelta(double epsilonSampleDelta) {
		this.epsilonSampleDelta = epsilonSampleDelta;
	}

	public double getEpsilonSampleDeltaMax() {
		return epsilonSampleDeltaMax;
	}

	public void setEpsilonSampleDeltaMax(double epsilonSampleDeltaMax) {
		this.epsilonSampleDeltaMax = epsilonSampleDeltaMax;
	}

	public double getEpsilonSampleDeltaMin() {
		return epsilonSampleDeltaMin;
	}

	public void setEpsilonSampleDeltaMin(double epsilonSampleDeltaMin) {
		this.epsilonSampleDeltaMin = epsilonSampleDeltaMin;
	}

	public double getEpsilonNeighborhood() {
		return epsilonNeighborhood;
	}

	public void setEpsilonNeighborhood(double epsilonNeighborhood) {
		this.epsilonNeighborhood = epsilonNeighborhood;
	}

	public double getEpsilonNeighborhoodDelta() {
		return epsilonNeighborhoodDelta;
	}

	public void setEpsilonNeighborhoodDelta(double epsilonNeighborhoodDelta) {
		this.epsilonNeighborhoodDelta = epsilonNeighborhoodDelta;
	}

	public double getEpsilonNeighborhoodDeltaMax() {
		return epsilonNeighborhoodDeltaMax;
	}

	public void setEpsilonNeighborhoodDeltaMax(double epsilonNeighborhoodDeltaMax) {
		this.epsilonNeighborhoodDeltaMax = epsilonNeighborhoodDeltaMax;
	}

	public double getEpsilonNeighborhoodDeltaMin() {
		return epsilonNeighborhoodDeltaMin;
	}

	public void setEpsilonNeighborhoodDeltaMin(double epsilonNeighborhoodDeltaMin) {
		this.epsilonNeighborhoodDeltaMin = epsilonNeighborhoodDeltaMin;
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

	@Override
	protected void config() {
		bindIterativeOptimizer(EvolutionaryAlgorithm.class);
		bind(CrossoverRate.class).to(ConstantCrossoverRate.class).in(SINGLETON);
		bind(Selector.class).to(AeSeHSelector.class);
		bind(Coupler.class).to(AeSeHCoupler.class);
	}
}
