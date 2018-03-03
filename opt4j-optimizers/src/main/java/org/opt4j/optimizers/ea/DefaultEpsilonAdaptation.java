package org.opt4j.optimizers.ea;

public class DefaultEpsilonAdaptation implements EpsilonAdaption {

	protected double epsilon_sample;
	protected double epsilon_sample_delta;
	protected final double epsilon_sample_delta_max;
	protected final double epsilon_sample_delta_min;

	protected double epsilon_neighborhood;
	protected double epsilon_neighborhood_delta;
	protected final double epsilon_neighborhood_delta_max;
	protected final double epsilon_neighborhood_delta_min;

	public DefaultEpsilonAdaptation(double epsilon_sample, double epsilon_sample_delta, double epsilon_sample_delta_max,
			double epsilon_sample_delta_min, double epsilon_neighborhood, double epsilon_neighborhood_delta,
			double epsilon_neighborhood_delta_max, double epsilon_neighborhood_delta_min) {
		if (epsilon_sample_delta < epsilon_sample_delta_min || epsilon_sample_delta > epsilon_sample_delta_max){
			throw new IllegalArgumentException("The start value for the epsilon_sample must lie within the provided bounds");
		}
		if (epsilon_neighborhood_delta < epsilon_neighborhood_delta_min || epsilon_neighborhood_delta > epsilon_neighborhood_delta_max){
			throw new IllegalArgumentException("The start value for the epsilon_neighborhood must lie within the provided bounds");
		}
		
		this.epsilon_sample = epsilon_sample;
		this.epsilon_sample_delta = epsilon_sample_delta;
		this.epsilon_sample_delta_max = epsilon_sample_delta_max;
		this.epsilon_sample_delta_min = epsilon_sample_delta_min;
		this.epsilon_neighborhood = epsilon_neighborhood;
		this.epsilon_neighborhood_delta = epsilon_neighborhood_delta;
		this.epsilon_neighborhood_delta_max = epsilon_neighborhood_delta_max;
		this.epsilon_neighborhood_delta_min = epsilon_neighborhood_delta_min;
	}

	@Override
	public double getSamplingEpsilon() {
		return epsilon_sample;
	}

	@Override
	public double getNeighborhoodEpsilon() {
		return epsilon_neighborhood;
	}

	@Override
	public void adaptSamplingEpsilon(boolean tooManyEpsilonDominantIndividuals) {
		if (tooManyEpsilonDominantIndividuals){
			epsilon_sample = epsilon_sample + epsilon_sample_delta;
			epsilon_sample_delta = Math.min(epsilon_sample_delta_max, 2 * epsilon_sample_delta);
		}else{
			epsilon_sample = Math.max(0.0, epsilon_sample - epsilon_sample_delta);
			epsilon_sample_delta = Math.max(epsilon_sample_delta_min, epsilon_sample_delta / 2);
		}
	}

	@Override
	public void adaptNeighborhoodEpsilon(boolean tooManyNeighborhoods) {
		if(tooManyNeighborhoods){
			epsilon_neighborhood = epsilon_neighborhood + epsilon_neighborhood_delta;
			epsilon_neighborhood_delta = Math.min(epsilon_neighborhood_delta_max, 2 * epsilon_neighborhood_delta);
		}else{
			epsilon_neighborhood = Math.max(0.0, epsilon_neighborhood - epsilon_neighborhood_delta);
			epsilon_neighborhood_delta = Math.max(epsilon_neighborhood_delta_min, epsilon_neighborhood_delta / 2);
		}
	}
}
