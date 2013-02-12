package org.opt4j.tutorial;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;

public class HelloWorldEvaluator implements Evaluator<String> {

	Objective objective = new Objective("objective", Sign.MAX);

	@Override
	public Objectives evaluate(String phenotype) {
		int value = 0;
		for (int i = 0; i < phenotype.length(); i++) {
			value += (phenotype.charAt(i) == "HELLO WORLD".charAt(i)) ? 1 : 0;
		}

		Objectives objectives = new Objectives();
		objectives.add(objective, value);
		return objectives;
	}
}
