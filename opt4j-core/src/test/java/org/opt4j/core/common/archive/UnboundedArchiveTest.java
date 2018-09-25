package org.opt4j.core.common.archive;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.archive.CrowdingArchiveTest.MockProblemModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class UnboundedArchiveTest {

	@Test
	public void updateWithNondominated() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 0);
		objectivesA0.add(o1, 0);
		individual.setObjectives(objectivesA0);

		UnboundedArchive archive = new UnboundedArchive();
		Assert.assertFalse(archive.iterator().hasNext());
		Assert.assertTrue(archive.updateWithNondominated(Collections.singleton(individual)));
		Assert.assertTrue(archive.iterator().hasNext());
		Assert.assertEquals(archive.iterator().next(), individual);
	}

	@Test
	public void updateWithExisting() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 0);
		objectivesA0.add(o1, 0);
		individual.setObjectives(objectivesA0);

		UnboundedArchive archive = new UnboundedArchive();
		archive.updateWithNondominated(Collections.singleton(individual));
		Assert.assertFalse(archive.updateWithNondominated(Collections.singleton(individual)));
	}

	@Test
	public void updateWithNone() {
		UnboundedArchive archive = new UnboundedArchive();
		Assert.assertFalse(archive.updateWithNondominated(Collections.<Individual> emptySet()));
	}
}
