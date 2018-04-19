package org.opt4j.optimizers.ea.aeseh;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link EpsilonAdaptationDefault} adapts the ε values exactly as described
 * in the paper cited in the {@link AeSeHModule}.
 * 
 * @author Fedor Smirnov
 *
 */
public class EpsilonAdaptationDefault implements EpsilonAdaptation {

	protected double epsilonSample;
	protected double epsilonSampleDelta;
	protected final double epsilonSampleDeltaMax;
	protected final double epsilonSampleDeltaMin;

	protected double epsilonNeighborhood;
	protected double epsilonNeighborhoodDelta;
	protected final double epsilonNeighborhoodDeltaMax;
	protected final double epsilonNeighborhoodDeltaMin;

	@Inject
	public EpsilonAdaptationDefault(
			@Constant(value = "epsilonSample", namespace = EpsilonAdaptationDefault.class) double epsilonSample,
			@Constant(value = "epsilonSampleDelta", namespace = EpsilonAdaptationDefault.class) double epsilonSampleDelta,
			@Constant(value = "epsilonSampleDeltaMax", namespace = EpsilonAdaptationDefault.class) double epsilonSampleDeltaMax,
			@Constant(value = "epsilonSampleDeltaMin", namespace = EpsilonAdaptationDefault.class) double epsilonSampleDeltaMin,
			@Constant(value = "epsilonNeighborhood", namespace = EpsilonAdaptationDefault.class) double epsilonNeighborhood,
			@Constant(value = "epsilonNeighborhoodDelta", namespace = EpsilonAdaptationDefault.class) double epsilonNeighborhoodDelta,
			@Constant(value = "epsilonNeighborhoodDeltaMax", namespace = EpsilonAdaptationDefault.class) double epsilonNeighborhoodDeltaMax,
			@Constant(value = "epsilonNeighborhoodDeltaMin", namespace = EpsilonAdaptationDefault.class) double epsilonNeighborhoodDeltaMin) {
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
