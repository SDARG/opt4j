package org.opt4j.tutorial.minones;

import org.opt4j.config.annotations.Parent;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.tutorial.TutorialModule;

@Parent(TutorialModule.class)
public class MinOnesModule extends ProblemModule {

	public void config() {
		bindProblem(MinOnesDecoder.class, MinOnesDecoder.class, MinOnesEvaluator.class);
	}

}
