package org.opt4j.core.common.completer;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.common.completer.IndividualCompleterModule.Type;
import org.opt4j.core.common.completer.SequentialIndividualCompleterTest.MockProblemModule;
import org.opt4j.core.optimizer.IndividualCompleter;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class IndividualCompleterModuleTest {
	@Test
	public void getType() {
		IndividualCompleterModule module = new IndividualCompleterModule();
		module.setType(Type.PARALLEL);
		Assert.assertEquals(Type.PARALLEL, module.getType());
	}

	@Test
	public void configSequential() {
		IndividualCompleterModule module = new IndividualCompleterModule();
		module.setType(Type.SEQUENTIAL);

		Injector injector = Guice.createInjector(new MockProblemModule(), module);
		IndividualCompleter completer = injector.getInstance(IndividualCompleter.class);

		Assert.assertEquals(SequentialIndividualCompleter.class, completer.getClass());
	}

	@Test
	public void configParallel() {
		IndividualCompleterModule module = new IndividualCompleterModule();
		module.setType(Type.PARALLEL);

		Injector injector = Guice.createInjector(new MockProblemModule(), module);
		IndividualCompleter completer = injector.getInstance(IndividualCompleter.class);

		Assert.assertEquals(ParallelIndividualCompleter.class, completer.getClass());
	}
}
