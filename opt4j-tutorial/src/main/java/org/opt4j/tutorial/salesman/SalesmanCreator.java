package org.opt4j.tutorial.salesman;

import java.util.Collections;

import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.tutorial.salesman.SalesmanProblem.City;

import com.google.inject.Inject;

public class SalesmanCreator implements Creator<PermutationGenotype<City>> {

	protected final SalesmanProblem problem;

	@Inject
	public SalesmanCreator(SalesmanProblem problem) {
		this.problem = problem;
	}

	@Override
	public PermutationGenotype<City> create() {
		PermutationGenotype<City> genotype = new PermutationGenotype<>();
		for (City city : problem.getCities()) {
			genotype.add(city);
		}

		Collections.shuffle(genotype);

		return genotype;
	}
}
