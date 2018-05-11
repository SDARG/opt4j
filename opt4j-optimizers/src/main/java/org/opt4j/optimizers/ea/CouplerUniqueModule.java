package org.opt4j.optimizers.ea;

import org.opt4j.core.config.annotations.Name;

/**
 * Binds the {@link CouplerUnique} as the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
@Name("Unique")
public class CouplerUniqueModule extends CouplerModule {

	@Override
	protected void config() {
		bindCoupler(CouplerUnique.class);
	}
}
