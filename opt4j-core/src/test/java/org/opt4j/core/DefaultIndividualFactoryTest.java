package org.opt4j.core;

import org.opt4j.core.genotype.PermutationGenotype;
import org.opt4j.core.problem.Creator;

import com.google.inject.Provider;

public class DefaultIndividualFactoryTest extends AbstractIndividualFactoryTest {
	Individual individual = new Individual();
	PermutationGenotype<Object> genotype = new PermutationGenotype<Object>();

	@Override
	public AbstractIndividualFactory<Individual> getFactory() {
		return new DefaultIndividualFactory(new Provider<Individual>() {
			@Override
			public Individual get() {
				return individual;
			}
		}, new Creator<Genotype>() {
			@Override
			public Genotype create() {
				return genotype;
			}
		});
	}

}
