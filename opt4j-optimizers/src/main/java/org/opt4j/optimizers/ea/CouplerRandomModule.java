package org.opt4j.optimizers.ea;

import org.opt4j.core.config.annotations.Name;

/**
 * Binds the {@link CouplerRandom} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
@Name("Random")
public class CouplerRandomModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerRandom.class);
	}
}
