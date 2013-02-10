package org.opt4j.tutorial;

import java.util.Collections;

import org.opt4j.core.problem.Creator;
import org.opt4j.genotype.PermutationGenotype;
import org.opt4j.tutorial.SalesmanProblem.City;

import com.google.inject.Inject;

public class SalesmanCreator implements Creator<PermutationGenotype<City>> {

	protected final SalesmanProblem problem;

	@Inject
	public SalesmanCreator(SalesmanProblem problem) {
		this.problem = problem;
	}

	public PermutationGenotype<City> create() {
		PermutationGenotype<City> genotype = new PermutationGenotype<City>();
		for (City city : problem.getCities()) {
			genotype.add(city);
		}

		Collections.shuffle(genotype);

		return genotype;
	}
}
