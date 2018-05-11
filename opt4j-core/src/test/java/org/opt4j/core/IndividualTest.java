package org.opt4j.core;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual.State;
import org.opt4j.core.genotype.PermutationGenotype;

public class IndividualTest {
	
	private boolean stateChanged = false;
	
	@Test
	public void getPhenotypeTest() {
		Individual individual = new Individual();
		String p = "phenotype";
		individual.setPhenotype(p);
		Assert.assertTrue(p == individual.getPhenotype());
		Assert.assertEquals(State.PHENOTYPED, individual.getState());
		Assert.assertTrue(individual.isDecoded());
	}

	@Test
	public void getGenotypeTest() {
		Individual individual = new Individual();
		Genotype g = new PermutationGenotype<Object>();
		individual.setGenotype(g);
		Assert.assertTrue(g == individual.getGenotype());
		Assert.assertEquals(State.GENOTYPED, individual.getState());
	}

	@Test
	public void getObjectivesTest() {
		Individual individual = new Individual();
		Objectives o = new Objectives();
		individual.setObjectives(o);
		Assert.assertTrue(o == individual.getObjectives());
		Assert.assertEquals(State.EVALUATED, individual.getState());
		Assert.assertTrue(individual.isEvaluated());
	}

	@Test
	public void setStateTest() {
		Individual individual = new Individual();

		IndividualStateListener listener = new IndividualStateListener() {
			@Override
			public void inidividualStateChanged(Individual individual) {
				stateChanged = true;
			}
		};
		individual.setIndividualStatusListeners(Collections.singleton(listener));

		individual.setState(State.DECODING);

		Assert.assertEquals(State.DECODING, individual.getState());
		Assert.assertTrue(stateChanged);
	}
}
