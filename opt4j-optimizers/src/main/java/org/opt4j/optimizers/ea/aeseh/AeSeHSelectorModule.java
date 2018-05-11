package org.opt4j.optimizers.ea.aeseh;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Name;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Selector;
import org.opt4j.optimizers.ea.SelectorModule;

/**
 * Binds the {@link AeSeHSelector} as {@link Selector}
 * 
 * 
 * @author Fedor Smirnov
 *
 */
@Name("Epsilon Sampling")
public class AeSeHSelectorModule extends SelectorModule {

	@Info("The start value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSample", namespace = ESamplingSurvivorGeneration.class)
	protected double epsilonSample = 0.0;
	@Info("The start value used for the adaption of the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDelta", namespace = ESamplingSurvivorGeneration.class)
	protected double epsilonSampleDelta = 0.005;
	@Info("The maximal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMax", namespace = ESamplingSurvivorGeneration.class)
	protected double epsilonSampleDeltaMax = 0.005;
	@Info("The minimal value used for the epsilon value which is applied during the survivor selection.")
	@Constant(value = "epsilonSampleDeltaMin", namespace = ESamplingSurvivorGeneration.class)
	protected double epsilonSampleDeltaMin = 0.0001;

	@Override
	protected void config() {
		bindSelector(AeSeHSelector.class);
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

}
