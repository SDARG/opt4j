package org.opt4j.optimizers.ea;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.start.Opt4JModule;

/**
 * Abstract module class for the {@link Coupler}.
 * 
 * @author Fedor Smirnov
 *
 */
@Icon(Icons.SELECTOR)
@Category
@Parent(EvolutionaryAlgorithmModule.class)
public abstract class CouplerModule extends Opt4JModule {

	/**
	 * Binds the given {@link Coupler}.
	 * 
	 * @param coupler
	 *            the {@link Coupler} to bind
	 */
	protected void bindCoupler(Class<? extends Coupler> coupler) {
		bind(Coupler.class).to(coupler).in(SINGLETON);
	}
}
