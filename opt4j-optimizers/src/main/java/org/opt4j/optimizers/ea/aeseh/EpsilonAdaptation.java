package org.opt4j.optimizers.ea.aeseh;

import com.google.inject.ImplementedBy;

/**
 * The {@link EpsilonAdaptation} manages the adaptation of the ε-value stored in
 * the {@link AdaptiveEpsilon}.
 * 
 * @author Fedor Smirnov
 *
 */
@ImplementedBy(EpsilonAdaptationDelta.class)
public interface EpsilonAdaptation {

	/**
	 * Adjusts the ε-value according to the given {@link AdaptiveEpsilon}.
	 * 
	 * @param adaptiveEpsilon
	 *            the {@link AdaptiveEpsilon} contains the ε-value and the
	 *            information about its adaptation
	 * @param epsilonTooBig
	 *            {@code true} if the current epsilon is too big
	 */
	public void adaptEpsilon(AdaptiveEpsilon adaptiveEpsilon, boolean epsilonTooBig);
}
