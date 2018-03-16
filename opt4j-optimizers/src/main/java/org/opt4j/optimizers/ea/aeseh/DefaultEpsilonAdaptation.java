package org.opt4j.optimizers.ea.aeseh;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

public class DefaultEpsilonAdaptation implements EpsilonAdaption {

	protected double epsilonSample;
	protected double epsilonSampleDelta;
	protected final double epsilonSampleDeltaMax;
	protected final double epsilonSampleDeltaMin;

	protected double epsilonNeighborhood;
	protected double epsilonNeighborhoodDelta;
	protected final double epsilonNeighborhoodDeltaMax;
	protected final double epsilonNeighborhoodDeltaMin;

	@Inject
	public DefaultEpsilonAdaptation(
			@Constant(value = "epsilonSample", namespace = DefaultEpsilonAdaptation.class) double epsilonSample,
			@Constant(value = "epsilonSampleDelta", namespace = DefaultEpsilonAdaptation.class) double epsilonSampleDelta,
			@Constant(value = "epsilonSampleDeltaMax", namespace = DefaultEpsilonAdaptation.class) double epsilonSampleDeltaMax,
			@Constant(value = "epsilonSampleDeltaMin", namespace = DefaultEpsilonAdaptation.class) double epsilonSampleDeltaMin,
			@Constant(value = "epsilonNeighborhood", namespace = DefaultEpsilonAdaptation.class) double epsilonNeighborhood,
			@Constant(value = "epsilonNeighborhoodDelta", namespace = DefaultEpsilonAdaptation.class) double epsilonNeighborhoodDelta,
			@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = DefaultEpsilonAdaptation.class) double epsilonNeighborhoodDeltaMax,
			@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = DefaultEpsilonAdaptation.class) double epsilonNeighborhoodDeltaMin) {
		if (epsilonSampleDelta < epsilonSampleDeltaMin || epsilonSampleDelta > epsilonSampleDeltaMax) {
			throw new IllegalArgumentException(
					"The start value for the epsilon_sample must lie within the provided bounds");
		}
		if (epsilonNeighborhoodDelta < epsilonNeighborhoodDeltaMin
				|| epsilonNeighborhoodDelta > epsilonNeighborhoodDeltaMax) {
			throw new IllegalArgumentException(
					"The start value for the epsilon_neighborhood must lie within the provided bounds");
		}

		this.epsilonSample = epsilonSample;
		this.epsilonSampleDelta = epsilonSampleDelta;
		this.epsilonSampleDeltaMax = epsilonSampleDeltaMax;
		this.epsilonSampleDeltaMin = epsilonSampleDeltaMin;
		this.epsilonNeighborhood = epsilonNeighborhood;
		this.epsilonNeighborhoodDelta = epsilonNeighborhoodDelta;
		this.epsilonNeighborhoodDeltaMax = epsilonNeighborhoodDeltaMax;
		this.epsilonNeighborhoodDeltaMin = epsilonNeighborhoodDeltaMin;
	}

	@Override
	public double getSamplingEpsilon() {
		return epsilonSample;
	}

	@Override
	public double getNeighborhoodEpsilon() {
		return epsilonNeighborhood;
	}

	@Override
	public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals) {
		if (tooManyEpsilonDominantIndividuals) {
			epsilonSample = epsilonSample + epsilonSampleDelta;
			epsilonSampleDelta = Math.min(epsilonSampleDeltaMax, 2 * epsilonSampleDelta);
		} else {
			epsilonSample = Math.max(0.0, epsilonSample - epsilonSampleDelta);
			epsilonSampleDelta = Math.max(epsilonSampleDeltaMin, epsilonSampleDelta / 2);
		}
	}

	@Override
	public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods) {
		if (tooManyNeighborhoods) {
			epsilonNeighborhood = epsilonNeighborhood + epsilonNeighborhoodDelta;
			epsilonNeighborhoodDelta = Math.min(epsilonNeighborhoodDeltaMax, 2 * epsilonNeighborhoodDelta);
		} else {
			epsilonNeighborhood = Math.max(0.0, epsilonNeighborhood - epsilonNeighborhoodDelta);
			epsilonNeighborhoodDelta = Math.max(epsilonNeighborhoodDeltaMin, epsilonNeighborhoodDelta / 2);
		}
	}
}
