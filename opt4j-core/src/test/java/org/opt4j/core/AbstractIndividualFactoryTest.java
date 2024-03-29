package org.opt4j.core;

import org.junit.jupiter.api.Assertions;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.opt4j.core.genotype.PermutationGenotype;

public abstract class AbstractIndividualFactoryTest {

	protected IndividualStateListener listener = new IndividualStateListener() {
		@Override
		public void inidividualStateChanged(Individual individual) {
			// nothing to be done
		}
	};

	protected void init(AbstractIndividualFactory<Individual> factory) {
		factory.injectListeners(Collections.singleton(listener));
	}

	public abstract AbstractIndividualFactory<Individual> getFactory();

	@Test
	public void injectListenersTest() {
		AbstractIndividualFactory<Individual> factory = getFactory();
		init(factory);
		Assertions.assertTrue(factory.individualStateListeners.contains(listener));
	}

	@Test
	public void addIndividualStateListenerTest() {
		AbstractIndividualFactory<Individual> factory = getFactory();

		IndividualStateListener listener2 = new IndividualStateListener() {
			@Override
			public void inidividualStateChanged(Individual individual) {

			}
		};

		factory.addIndividualStateListener(listener2);
		Assertions.assertTrue(factory.individualStateListeners.contains(listener2));
	}

	@Test
	public void createTest() {
		AbstractIndividualFactory<Individual> factory = getFactory();
		init(factory);

		Individual individual = factory.create();
		Assertions.assertTrue(individual.individualStateListeners.contains(listener));
		Assertions.assertNotNull(individual.genotype);
	}

	@Test
	public void createWithGenotypeTest() {
		AbstractIndividualFactory<Individual> factory = getFactory();
		init(factory);

		Genotype genotype = new PermutationGenotype<Object>();
		Individual individual = factory.create(genotype);
		Assertions.assertTrue(individual.individualStateListeners.contains(listener));
		Assertions.assertTrue(individual.genotype == genotype);
	}

	@Test
	public void removeIndividualStateListenerTest() {
		AbstractIndividualFactory<Individual> factory = getFactory();

		IndividualStateListener listener2 = new IndividualStateListener() {
			@Override
			public void inidividualStateChanged(Individual individual) {

			}
		};

		factory.addIndividualStateListener(listener2);
		Assertions.assertTrue(factory.individualStateListeners.contains(listener2));
		factory.removeIndividualStateListener(listener2);
		Assertions.assertFalse(factory.individualStateListeners.contains(listener2));
	}
}
