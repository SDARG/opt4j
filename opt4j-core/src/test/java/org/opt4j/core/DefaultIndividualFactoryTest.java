package org.opt4j.core;

import org.opt4j.core.genotype.PermutationGenotype;

public class DefaultIndividualFactoryTest extends AbstractIndividualFactoryTest {
	protected Individual individual = new Individual();
	protected PermutationGenotype<Object> genotype = new PermutationGenotype<>();

	@Override
	public AbstractIndividualFactory<Individual> getFactory() {
		return new DefaultIndividualFactory(() -> individual, () -> genotype);
	}

}
