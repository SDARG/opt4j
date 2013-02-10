package org.opt4j.tutorial;

import org.opt4j.config.annotations.Parent;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.start.Constant;
import org.opt4j.viewer.VisualizationModule;

@Parent(TutorialModule.class)
public class SalesmanModule extends ProblemModule {

	@Constant(value = "size")
	protected int size = 100;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void config() {
		bindProblem(SalesmanCreator.class, SalesmanDecoder.class, SalesmanEvaluator.class);
		VisualizationModule.addIndividualMouseListener(binder(), SalesmanWidgetService.class);
	}
}
