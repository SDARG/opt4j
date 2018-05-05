package org.opt4j.optimizers.ea;

/**
 * Binds the {@link CouplerRandom} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
public class CouplerRandomModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerRandom.class);
	}
}
