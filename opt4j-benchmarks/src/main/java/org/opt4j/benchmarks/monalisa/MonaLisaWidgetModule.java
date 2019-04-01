package org.opt4j.benchmarks.monalisa;

import org.opt4j.viewer.VisualizationModule;

public class MonaLisaWidgetModule extends VisualizationModule {

	public void config() {
		addIndividualMouseListener(MonaLisaWidgetService.class);
	}

}
