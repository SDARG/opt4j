package org.opt4j.core.common.archive;

import java.util.Collection;
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

public class BoundedArchiveTest {
	@Test(expected = IllegalArgumentException.class)
	public void boundedArchiveIllegalCapacity() {
		BoundedArchive archive = new BoundedArchive(-1) {

			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				Assert.fail();
				return false;
			}
		};
		Assert.fail("should not be creatable: " + archive);
	}

	@Test
	public void setCapacity() {
		BoundedArchive archive = new BoundedArchive(10) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				Assert.fail();
				return false;
			}
		};
		Assert.assertEquals(10, archive.getCapacity());
		archive.setCapacity(20);
		Assert.assertEquals(20, archive.getCapacity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void setCapacityIllegalCapacity() {
		BoundedArchive archive = new BoundedArchive(10) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				Assert.fail();
				return false;
			}
		};
		archive.setCapacity(-1);
	}

	@Test
	public void addCheckedIndividual() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual1 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 1);
		objectivesA0.add(o1, 0);
		individual1.setObjectives(objectivesA0);

		BoundedArchive archive = new BoundedArchive(1) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				individuals.addAll(candidates);
				return true;
			}
		};
		archive.addCheckedIndividual(individual1);
		Assert.assertTrue(archive.contains(individual1));
		Assert.assertEquals(1, archive.size());
	}

	@Test
	public void addCheckedIndividualTwice() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual1 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 1);
		objectivesA0.add(o1, 0);
		individual1.setObjectives(objectivesA0);

		BoundedArchive archive = new BoundedArchive(1) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				individuals.addAll(candidates);
				return true;
			}
		};
		archive.addCheckedIndividual(individual1);
		archive.addCheckedIndividual(individual1);
		Assert.assertTrue(archive.contains(individual1));
		Assert.assertEquals(1, archive.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void addCheckedIndividualDespiteArchiveFull() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual1 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 1);
		objectivesA0.add(o1, 0);
		individual1.setObjectives(objectivesA0);

		Individual individual2 = factory.create();
		Objectives objectivesA1 = new Objectives();
		objectivesA1.add(o0, 0);
		objectivesA1.add(o1, 1);
		individual2.setObjectives(objectivesA1);

		BoundedArchive archive = new BoundedArchive(1) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				individuals.addAll(candidates);
				return true;
			}
		};
		archive.addCheckedIndividual(individual1);
		Assert.assertTrue(archive.contains(individual1));
		Assert.assertEquals(1, archive.size());

		archive.addCheckedIndividual(individual2);
	}

	@Test
	public void addCheckedIndividuals() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual1 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 1);
		objectivesA0.add(o1, 0);
		individual1.setObjectives(objectivesA0);

		BoundedArchive archive = new BoundedArchive(1) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				individuals.addAll(candidates);
				return true;
			}
		};
		archive.addCheckedIndividuals(Collections.singleton(individual1));
		Assert.assertTrue(archive.contains(individual1));
		Assert.assertEquals(1, archive.size());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void addCheckedIndividualsDespiteArchiveFull() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual individual1 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 1);
		objectivesA0.add(o1, 0);
		individual1.setObjectives(objectivesA0);

		Individual individual2 = factory.create();
		Objectives objectivesA1 = new Objectives();
		objectivesA1.add(o0, 0);
		objectivesA1.add(o1, 1);
		individual2.setObjectives(objectivesA1);

		BoundedArchive archive = new BoundedArchive(1) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				individuals.addAll(candidates);
				return true;
			}
		};
		archive.addCheckedIndividuals(Collections.singleton(individual1));
		Assert.assertTrue(archive.contains(individual1));
		Assert.assertEquals(1, archive.size());

		archive.addCheckedIndividuals(Collections.singleton(individual2));
	}
}
