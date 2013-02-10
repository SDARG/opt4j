package org.opt4j.tutorial;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.PhenotypeWrapper;

public class HelloWorldEvaluator implements Evaluator<PhenotypeWrapper<String>> {

	Objective objective = new Objective("objective", Sign.MAX);

	String target = "HELLO WORLD";

	@Override
	public Objectives evaluate(PhenotypeWrapper<String> phenotype) {
		String s = phenotype.get();

		int value = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == target.charAt(i)) {
				value++;
			}
		}

		Objectives objectives = new Objectives();
		objectives.add(objective, value);
		return objectives;
	}

}
