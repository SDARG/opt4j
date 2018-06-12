package org.opt4j.optimizers.ea.aeseh;

/**
 * The {@link AdaptiveEpsilon} contains the information about an ε-value and the
 * information that is used by the {@link EpsilonAdaptation} to adapt the
 * ε-value.
 * 
 * @author Fedor Smirnov
 *
 */
public class AdaptiveEpsilon {

	protected double epsilon;
	protected double epsilonDelta;
	protected final double epsilonDeltaMax;
	protected final double epsilonDeltaMin;

	public AdaptiveEpsilon(double epsilon, double epsilonDelta, double epsilonDeltaMax, double epsilonDeltaMin) {
		if (epsilonDelta < epsilonDeltaMin || epsilonDelta > epsilonDeltaMax) {
			throw new IllegalArgumentException("The ε-delta value exceeds the given bounds");
		}
		this.epsilon = epsilon;
		this.epsilonDelta = epsilonDelta;
		this.epsilonDeltaMax = epsilonDeltaMax;
		this.epsilonDeltaMin = epsilonDeltaMin;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

	public double getEpsilonDelta() {
		return epsilonDelta;
	}

	public void setEpsilonDelta(double epsilonDelta) {
		double delta = Math.max(epsilonDeltaMin, epsilonDelta);
		delta = Math.min(epsilonDeltaMax, delta);
		this.epsilonDelta = delta;
	}

	public double getEpsilonDeltaMax() {
		return epsilonDeltaMax;
	}

	public double getEpsilonDeltaMin() {
		return epsilonDeltaMin;
	}
}
