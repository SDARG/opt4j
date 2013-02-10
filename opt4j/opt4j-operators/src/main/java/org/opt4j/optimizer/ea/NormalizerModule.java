package org.opt4j.optimizer.ea;

import org.opt4j.core.IndividualStateListener;
import org.opt4j.start.Opt4JModule;

/**
 * Adds the Normalizer to the {@link IndividualStateListener}s.
 * 
 * @author reimann
 * 
 */
public class NormalizerModule extends Opt4JModule {

	@Override
	protected void config() {
		addIndividualStateListener(Normalizer.class);
	}

}
