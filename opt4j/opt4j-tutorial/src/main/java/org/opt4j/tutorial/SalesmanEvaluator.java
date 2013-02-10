package org.opt4j.tutorial;

import java.util.Arrays;
import java.util.List;

import org.opt4j.core.Objective;
import org.opt4j.core.Objective.Sign;
import org.opt4j.core.Objectives;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.tutorial.SalesmanProblem.City;

public class SalesmanEvaluator implements Evaluator<SalesmanRoute> {

	Objective distance = new Objective("distance", Sign.MIN);

	@Override
	public Objectives evaluate(SalesmanRoute salesmanRoute) {
		double dist = 0;
		for (int i = 0; i < salesmanRoute.size(); i++) {
			City one = salesmanRoute.get(i);
			City two = salesmanRoute.get((i + 1) % salesmanRoute.size());
			dist += getEuclideanDistance(one, two);
		}

		Objectives objectives = new Objectives();
		objectives.add(distance, dist);
		return objectives;
	}

	private double getEuclideanDistance(City one, City two) {
		final double x = one.getX() - two.getX();
		final double y = one.getY() - two.getY();
		return Math.sqrt(x * x + y * y);
	}

	public List<Objective> getObjectives() {
		return Arrays.asList(distance);
	}

}
