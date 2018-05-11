package org.opt4j.optimizers.ea;

import org.opt4j.core.config.annotations.Name;

/**
 * Binds the {@link CouplerDefault} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
@Name("Default")
public class CouplerDefaultModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerDefault.class);
	}
}
