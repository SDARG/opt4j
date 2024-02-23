package org.opt4j.core.common.archive;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;

public class BoundedArchiveTest {
	@Test
	public void boundedArchiveIllegalCapacity() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			BoundedArchive archive = new BoundedArchive(-1) {

				@Override
				protected boolean updateWithNondominated(Collection<Individual> candidates) {
					Assertions.fail();
					return false;
				}
			};
			Assertions.fail("should not be creatable: " + archive);
		});
	}

	@Test
	public void setCapacity() {
		BoundedArchive archive = new BoundedArchive(10) {
			@Override
			protected boolean updateWithNondominated(Collection<Individual> candidates) {
				Assertions.fail();
				return false;
			}
		};
		Assertions.assertEquals(10, archive.getCapacity());
		archive.setCapacity(20);
		Assertions.assertEquals(20, archive.getCapacity());
	}

	@Test
	public void setCapacityIllegalCapacity() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			BoundedArchive archive = new BoundedArchive(10) {
				@Override
				protected boolean updateWithNondominated(Collection<Individual> candidates) {
					Assertions.fail();
					return false;
				}
			};
			archive.setCapacity(-1);
		});
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
		Assertions.assertTrue(archive.contains(individual1));
		Assertions.assertEquals(1, archive.size());
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
		Assertions.assertTrue(archive.contains(individual1));
		Assertions.assertEquals(1, archive.size());
	}

	@Test
	public void addCheckedIndividualDespiteArchiveFull() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
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
			Assertions.assertTrue(archive.contains(individual1));
			Assertions.assertEquals(1, archive.size());

			archive.addCheckedIndividual(individual2);
		});
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
		Assertions.assertTrue(archive.contains(individual1));
		Assertions.assertEquals(1, archive.size());
	}

	@Test
	public void addCheckedIndividualsDespiteArchiveFull() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
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
			Assertions.assertTrue(archive.contains(individual1));
			Assertions.assertEquals(1, archive.size());

			archive.addCheckedIndividuals(Collections.singleton(individual2));
		});
	}
}
