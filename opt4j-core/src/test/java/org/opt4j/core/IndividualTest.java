package org.opt4j.core;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual.State;
import org.opt4j.core.genotype.PermutationGenotype;

public class IndividualTest {
	
	private boolean stateChanged = false;
	
	@Test
	public void getPhenotypeTest() {
		Individual individual = new Individual();
		String p = "phenotype";
		individual.setPhenotype(p);
		Assertions.assertTrue(p == individual.getPhenotype());
		Assertions.assertEquals(State.PHENOTYPED, individual.getState());
		Assertions.assertTrue(individual.isDecoded());
	}

	@Test
	public void getGenotypeTest() {
		Individual individual = new Individual();
		Genotype g = new PermutationGenotype<Object>();
		individual.setGenotype(g);
		Assertions.assertTrue(g == individual.getGenotype());
		Assertions.assertEquals(State.GENOTYPED, individual.getState());
	}

	@Test
	public void getObjectivesTest() {
		Individual individual = new Individual();
		Objectives o = new Objectives();
		individual.setObjectives(o);
		Assertions.assertTrue(o == individual.getObjectives());
		Assertions.assertEquals(State.EVALUATED, individual.getState());
		Assertions.assertTrue(individual.isEvaluated());
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

		Assertions.assertEquals(State.DECODING, individual.getState());
		Assertions.assertTrue(stateChanged);
	}
}
