package org.opt4j.optimizers.ea;

/**
 * Binds the {@link CouplerDefault} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
public class CouplerDefaultModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerDefault.class);
	}
}
