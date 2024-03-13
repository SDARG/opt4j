package org.opt4j.core.common.completer;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SequentialIndividualCompleterTest {
	private static String object = "test";
	private static Objectives objectives = new Objectives();

	protected static class MockProblem implements Creator<Genotype>, Decoder<Genotype, Object>, Evaluator<Object> {

		@Override
		public Genotype create() {
			return new BooleanGenotype();
		}

		@Override
		public Object decode(Genotype genotype) {
			return object;
		}

		@Override
		public Objectives evaluate(Object phenotype) {
			objectives.add(new Objective("x"), 1);
			return objectives;
		}
	}

	@Ignore
	protected static class MockProblemModule extends ProblemModule {
		@Override
		protected void config() {
			bindProblem(MockProblem.class, MockProblem.class, MockProblem.class);
		}
	}

	@Test
	public void decode() throws TerminationException {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);
		Individual i1 = factory.create();

		SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

		Assertions.assertTrue(i1.getState().equals(State.GENOTYPED));
		completer.decode(i1);
		Assertions.assertTrue(i1.getState().equals(State.PHENOTYPED));
		Assertions.assertTrue(i1.getPhenotype() == object);
	}

	@Test
	public void decodePhenotyped() throws TerminationException {
		assertThrows(IllegalStateException.class, () -> {
			Injector injector = Guice.createInjector(new MockProblemModule());
			IndividualFactory factory = injector.getInstance(IndividualFactory.class);
			Individual i1 = factory.create();

			SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

			completer.decode(i1);
			completer.decode(i1);
		});
	}

	@Test
	public void evaluate() throws TerminationException {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);
		Individual i1 = factory.create();

		SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

		i1.setPhenotype("my phenotype");
		Assertions.assertTrue(i1.getState().equals(State.PHENOTYPED));
		completer.evaluate(i1);
		Assertions.assertTrue(i1.getState().equals(State.EVALUATED));
		// Assert.assertTrue(i1.getObjectives() == objectives);
	}

	@Test
	public void evaluateEvaluated() throws TerminationException {
		assertThrows(IllegalStateException.class, () -> {
			Injector injector = Guice.createInjector(new MockProblemModule());
			IndividualFactory factory = injector.getInstance(IndividualFactory.class);
			Individual i1 = factory.create();

			SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

			i1.setPhenotype("my phenotype");
			completer.evaluate(i1);
			completer.evaluate(i1);
		});
	}

	@Test
	public void evaluateDifferentObjectivesSize() throws TerminationException {
		assertThrows(AssertionError.class, () -> {
			Injector injector = Guice.createInjector(new MockProblemModule());
			IndividualFactory factory = injector.getInstance(IndividualFactory.class);
			Individual i1 = factory.create();
			Individual i2 = factory.create();

			SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

			i1.setPhenotype("my phenotype");
			i2.setPhenotype("my other phenotype");
			completer.evaluate(i1);
			objectives.add(new Objective("y"), 4);
			completer.evaluate(i2);
		});
	}

	@Test
	public void complete() throws TerminationException {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);
		Individual i1 = factory.create();

		SequentialIndividualCompleter completer = injector.getInstance(SequentialIndividualCompleter.class);

		completer.complete(i1);

		Assertions.assertTrue(i1.isEvaluated());
		Assertions.assertTrue(i1.isEvaluated());
	}

}
