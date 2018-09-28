package org.opt4j.core.common.archive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.archive.CrowdingArchiveTest.MockProblemModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AbstractArchiveTest {

	class TestArchive extends AbstractArchive {
		protected Collection<Individual> candidates = null;

		@Override
		protected boolean updateWithNondominated(Collection<Individual> candidates) {
			this.candidates = candidates;
			addAll(candidates);
			return true;
		}

	};

	@Test
	public void update() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		TestArchive archive = new TestArchive();

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 1);
		objectives0.add(o1, 0);
		i0.setObjectives(objectives0);

		Assert.assertTrue(archive.update(i0));
		Assert.assertTrue(archive.contains(i0));
	}

	/**
	 * Tests {@link AbstractArchive#removeDominatedCandidates(List)} with two
	 * nondominated individuals.
	 */
	@Test
	public void removeDominatedCandidatesTest() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		TestArchive archive = new TestArchive();

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 1);
		objectives0.add(o1, 0);
		i0.setObjectives(objectives0);

		Individual i1 = factory.create();
		Objectives objectives1 = new Objectives();
		objectives1.add(o0, 0);
		objectives1.add(o1, 1);
		i1.setObjectives(objectives1);

		List<Individual> list = new ArrayList<>();
		list.add(i0);
		list.add(i1);
		archive.removeDominatedCandidates(list);

		Assert.assertEquals(2, list.size());
		Assert.assertTrue(list.contains(i0));
		Assert.assertTrue(list.contains(i1));
	}

	/**
	 * Tests {@link AbstractArchive#removeDominatedCandidates(List)} with two
	 * individuals which dominate themselves.
	 */
	@Test
	public void removeDominatedCandidatesTest2() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		TestArchive archive = new TestArchive();

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 0);
		objectives0.add(o1, 0);
		i0.setObjectives(objectives0);

		Individual i1 = factory.create();
		Objectives objectives1 = new Objectives();
		objectives1.add(o0, 1);
		objectives1.add(o1, 1);
		i1.setObjectives(objectives1);

		List<Individual> list = new ArrayList<>();
		list.add(i0);
		list.add(i1);
		archive.removeDominatedCandidates(list);

		Assert.assertEquals(1, list.size());
		Assert.assertTrue(list.contains(i0));

		list.clear();
		list.add(i1);
		list.add(i0);
		archive.removeDominatedCandidates(list);

		Assert.assertEquals(1, list.size());
		Assert.assertTrue(list.contains(i0));
	}

	/**
	 * Tests {@link AbstractArchive#removeArchiveDominated(List)} with two
	 * nondominated individuals.
	 */
	@Test
	public void removeArchiveDominatedTest() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual iArchived0 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 2);
		objectivesA0.add(o1, 3);
		iArchived0.setObjectives(objectivesA0);

		Individual iArchived1 = factory.create();
		Objectives objectivesA1 = new Objectives();
		objectivesA1.add(o0, 3);
		objectivesA1.add(o1, 2);
		iArchived1.setObjectives(objectivesA1);

		TestArchive archive = new TestArchive();
		archive.addAll(iArchived0, iArchived1);

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 1);
		objectives0.add(o1, 0);
		i0.setObjectives(objectives0);

		Individual i1 = factory.create();
		Objectives objectives1 = new Objectives();
		objectives1.add(o0, 0);
		objectives1.add(o1, 1);
		i1.setObjectives(objectives1);

		List<Individual> list = new ArrayList<>();
		list.add(i0);
		list.add(i1);
		archive.removeArchiveDominated(list);

		Assert.assertEquals(2, list.size());
		Assert.assertTrue(list.contains(i0));
		Assert.assertTrue(list.contains(i1));
		Assert.assertTrue(archive.isEmpty());
	}

	/**
	 * Tests {@link AbstractArchive#removeArchiveDominated(List)} with a
	 * dominated individual.
	 */
	@Test
	public void removeArchiveDominatedTest2() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual iArchived0 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 2);
		objectivesA0.add(o1, 3);
		iArchived0.setObjectives(objectivesA0);

		Individual iArchived1 = factory.create();
		Objectives objectivesA1 = new Objectives();
		objectivesA1.add(o0, 3);
		objectivesA1.add(o1, 2);
		iArchived1.setObjectives(objectivesA1);

		TestArchive archive = new TestArchive();
		archive.addAll(iArchived0, iArchived1);

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 4);
		objectives0.add(o1, 4);
		i0.setObjectives(objectives0);

		List<Individual> list = new ArrayList<>();
		list.add(i0);
		archive.removeArchiveDominated(list);

		Assert.assertTrue(list.isEmpty());
		Assert.assertEquals(2, archive.size());
	}

	/**
	 * Tests {@link AbstractArchive#removeArchiveDominated(List)} with a
	 * candidate which has the same objectives as an individual in the archive.
	 * To avoid unnecessary archive updates, the candidate is expected to be
	 * discarded here.
	 */
	@Test
	public void removeArchiveDominatedTest3() {
		Injector injector = Guice.createInjector(new MockProblemModule());
		IndividualFactory factory = injector.getInstance(IndividualFactory.class);

		Objective o0 = new Objective("o0");
		Objective o1 = new Objective("o1");

		Individual iArchived0 = factory.create();
		Objectives objectivesA0 = new Objectives();
		objectivesA0.add(o0, 0);
		objectivesA0.add(o1, 0);
		iArchived0.setObjectives(objectivesA0);

		TestArchive archive = new TestArchive();
		archive.add(iArchived0);

		Individual i0 = factory.create();
		Objectives objectives0 = new Objectives();
		objectives0.add(o0, 0);
		objectives0.add(o1, 0);
		i0.setObjectives(objectives0);

		List<Individual> list = new ArrayList<>();
		list.add(i0);
		archive.removeArchiveDominated(list);

		Assert.assertEquals(0, list.size());
		Assert.assertFalse(list.contains(i0));
		Assert.assertEquals(1, archive.size());
		Assert.assertTrue(archive.contains(iArchived0));
	}

}
