package org.opt4j.core.common.completer;


import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.common.completer.IndividualCompleterModule.Type;
import org.opt4j.core.common.completer.SequentialIndividualCompleterTest.MockProblemModule;
import org.opt4j.core.optimizer.TerminationException;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ParallelIndividualCompleterTest {

	@Test
	public void invalidThreadCount() {
		assertThrows(IllegalArgumentException.class, () -> {
			IndividualCompleterModule module = new IndividualCompleterModule();
			module.setThreads(0);
			module.setType(Type.PARALLEL);
			Injector injector = Guice.createInjector(new MockProblemModule(), module);

			injector.getInstance(ParallelIndividualCompleter.class);
		});
	}

	@Test
	public void optimizationStopped() {
		IndividualCompleterModule module = new IndividualCompleterModule();
		module.setThreads(4);
		module.setType(Type.PARALLEL);
		Injector injector = Guice.createInjector(new MockProblemModule(), module);

		ParallelIndividualCompleter completer = injector.getInstance(ParallelIndividualCompleter.class);

		completer.optimizationStopped(null);
		Assertions.assertTrue(completer.executor.isShutdown());
		Assertions.assertTrue(completer.executor.isShutdown());
	}

	@Test
	public void complete() throws TerminationException {
		IndividualCompleterModule module = new IndividualCompleterModule();
		module.setThreads(4);
		module.setType(Type.PARALLEL);
		Injector injector = Guice.createInjector(new MockProblemModule(), module);

		IndividualFactory factory = injector.getInstance(IndividualFactory.class);
		Individual i1 = factory.create();
		Individual i2 = factory.create();

		ParallelIndividualCompleter completer = injector.getInstance(ParallelIndividualCompleter.class);

		completer.complete(i1, i2);
		Assertions.assertTrue(i1.isEvaluated());
		Assertions.assertTrue(i2.isEvaluated());

		completer.complete(i1);
	}
}
