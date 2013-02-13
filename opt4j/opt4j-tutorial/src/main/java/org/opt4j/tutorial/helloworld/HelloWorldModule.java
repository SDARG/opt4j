package org.opt4j.tutorial.helloworld;

import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.tutorial.TutorialModule;

@Parent(TutorialModule.class)
public class HelloWorldModule extends ProblemModule {

	@Override
	protected void config() {
		bindProblem(HelloWorldCreator.class, HelloWorldDecoder.class, HelloWorldEvaluator.class);
	}

}
