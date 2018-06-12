package org.opt4j.optimizers.ea.aeseh;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Name;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Coupler;
import org.opt4j.optimizers.ea.CouplerModule;

/**
 * Binds the {@link EpsilonNeighborhoodCoupler} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
@Name("Epsilon Neighborhood")
@Citation(authors = "Hernán Aguirre, Akira Oyama, and Kiyoshi Tanaka", title = "Adaptive ε-sampling and ε-hood for evolutionary many-objective optimization.", journal = "Evolutionary Multi-Criterion Optimization (EMO)", pageFirst = 322, pageLast = 336, year = 2013, month = UNKNOWN)
public class EpsilonNeighborhoodCouplerModule extends CouplerModule {

	@Info("The reference value for the number of neighborhoods. The epsilon_h value is adapted to create a similar number of neighborhoods.")
	@Constant(value = "neighborhoodNumber", namespace = EpsilonNeighborhoodCoupler.class)
	protected int plannedNeighborhoodNumber = 5;
	@Info("The start value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhood", namespace = EpsilonNeighborhoodCoupler.class)
	protected double epsilonNeighborhood = 0.0;
	@Info("The start value used for the adaptation of the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDelta", namespace = EpsilonNeighborhoodCoupler.class)
	protected double epsilonNeighborhoodDelta = 0.005;
	@Info("The maximal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = EpsilonNeighborhoodCoupler.class)
	protected double epsilonNeighborhoodDeltaMax = 0.005;
	@Info("The minimal value used for the epsilon value which is applied during the neighborhood creation.")
	@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = EpsilonNeighborhoodCoupler.class)
	protected double epsilonNeighborhoodDeltaMin = 0.0001;

	@Override
	protected void config() {
		bindCoupler(EpsilonNeighborhoodCoupler.class);
	}

	public int getPlannedNeighborhoodNumber() {
		return plannedNeighborhoodNumber;
	}

	public void setPlannedNeighborhoodNumber(int plannedNeighborhoodNumber) {
		this.plannedNeighborhoodNumber = plannedNeighborhoodNumber;
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

}
