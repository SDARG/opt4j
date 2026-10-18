package org.opt4j.tutorial.gatekeeper;

import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.tutorial.minones.MinOnesResult;

public class ExpensiveEvaluator implements Evaluator<MinOnesResult> {
    @Override
    public Objectives evaluate(MinOnesResult minOnesResult) {
        // Simulate a computationally expensive task
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int value = 0;
        for (Boolean v : minOnesResult) {
            if (v != null && v) value++;
        }

        Objectives objectives = new Objectives();
        objectives.add("ones", Sign.MIN, value);
        return objectives;
    }
}
