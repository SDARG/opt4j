package org.opt4j.common.archive;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.opt4j.config.annotations.Ignore;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.Phenotype;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.genotype.BooleanGenotype;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class CrowdingArchiveTest {

	protected final static int SIZE = 200;

	Set<Individual> set = new HashSet<Individual>();
	CrowdingArchive crowdingArchive = null;

	protected static class MockProblem implements Creator<Genotype>, Decoder<Genotype, Phenotype>, Evaluator<Phenotype> {
		@Override
		public Genotype create() {
			return new BooleanGenotype();
		}

		@Override
		public Phenotype decode(Genotype genotype) {
			return null;
		}

		@Override
		public Objectives evaluate(Phenotype phenotype) {
			return new Objectives();
		}
	}

	@Ignore
	protected static class MockProblemModule extends ProblemModule {
		@Override
		protected void config() {
			bindProblem(MockProblem.class, MockProblem.class, MockProblem.class);
		}
	}

	@Before
	public void setUp() throws Exception {

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		for (int i = 0; i < SIZE; i++) {
			Individual individual = factory.create();
			Objectives objectives = new Objectives();
			objectives.add(o0, i);
			objectives.add(o1, SIZE - i);
			individual.setObjectives(objectives);
			set.add(individual);
		}
	}

	@Test
	public void testCrowdingArchiveConsistency() {
		CrowdingArchive c0 = new CrowdingArchive(100);
		c0.update(set);

		Set<Individual> i0 = new HashSet<Individual>(c0);

		c0.clear();
		c0.update(set);

		Set<Individual> i1 = new HashSet<Individual>(c0);

		Assert.assertArrayEquals(i0.toArray(), i1.toArray());

	}

}
