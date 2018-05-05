package org.opt4j.optimizers.ea;

/**
 * Binds the {@link CouplerUnique} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
public class CouplerUniqueModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerUnique.class);
	}
}
