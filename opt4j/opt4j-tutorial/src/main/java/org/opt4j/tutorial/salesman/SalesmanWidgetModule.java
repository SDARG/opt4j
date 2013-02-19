package org.opt4j.tutorial.salesman;

import org.opt4j.core.config.annotations.Parent;
import org.opt4j.tutorial.TutorialModule;
import org.opt4j.viewer.VisualizationModule;

@Parent(TutorialModule.class)
public class SalesmanWidgetModule extends VisualizationModule {

	public void config() {
		addIndividualMouseListener(SalesmanWidgetService.class);
	}
}
