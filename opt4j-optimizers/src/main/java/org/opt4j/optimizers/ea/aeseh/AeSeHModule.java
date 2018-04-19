package org.opt4j.optimizers.ea.aeseh;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Coupler;
import org.opt4j.optimizers.ea.Selector;

/**
 * Module to bind the AeSeH evolutionary algorithm as optimizer. 
 * 
 * @author Fedor Smirnov
 */
@Info("Multi-objective evolutionary algorithm where the survival selection and the creation of neighborhoods is based on epsilon-dominance. The selection of parents is done within the created neighborhoods.")
@Citation(authors = "Hernán Aguirre, Akira Oyama, and Kiyoshi Tanaka", title = "Adaptive ε-sampling and ε-hood for evolutionary many-objective optimization.", journal = "Evolutionary Multi-Criterion Optimization (EMO)", pageFirst = 322, pageLast = 336, year = 2013, month = UNKNOWN)
public class AeSeHModule extends OptimizerModule {

	@Info("The start value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSample", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonSample = 0.0;

	@Info("The start value used for the adaption of the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDelta", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonSampleDelta = 0.005;

	@Info("The maximal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMax", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonSampleDeltaMax = 0.005;

	@Info("The minimal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMin", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonSampleDeltaMin = 0.0001;

	@Info("The start value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhood", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonNeighborhood = 0.0;

	@Info("The start value used for the adaptation of the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDelta", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonNeighborhoodDelta = 0.005;

	@Info("The maximal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = EpsilonAdaptationDefault.class)
	protected double epsilonNeighborhoodDeltaMax = 0.005;

	@Info("The minimal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = EpsilonAdaptationDefault.class)
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

	@Override
	protected void config() {
		bind(Selector.class).to(AeSeHSelector.class);
		bind(Coupler.class).to(AeSeHCoupler.class);
	}
}
