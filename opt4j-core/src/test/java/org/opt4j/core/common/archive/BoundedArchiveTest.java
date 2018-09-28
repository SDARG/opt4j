package org.opt4j.core.common.archive;

import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual;

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
		Individual individual1 = mock(Individual.class);

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
		Individual individual1 = mock(Individual.class);

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
		Individual individual1 = mock(Individual.class);
		Individual individual2 = mock(Individual.class);

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
		Individual individual1 = mock(Individual.class);

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
		Individual individual1 = mock(Individual.class);
		Individual individual2 = mock(Individual.class);

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
