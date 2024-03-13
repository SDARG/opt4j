package org.opt4j.core.common.archive;

import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opt4j.core.Individual;

public class UnboundedArchiveTest {

	@Test
	public void updateWithNondominated() {
		Individual individual = mock(Individual.class);

		UnboundedArchive archive = new UnboundedArchive();
		Assertions.assertFalse(archive.iterator().hasNext());
		Assertions.assertTrue(archive.updateWithNondominated(Collections.singleton(individual)));
		Assertions.assertTrue(archive.iterator().hasNext());
		Assertions.assertEquals(archive.iterator().next(), individual);
	}

	@Test
	public void updateWithExisting() {
		Individual individual = mock(Individual.class);

		UnboundedArchive archive = new UnboundedArchive();
		archive.updateWithNondominated(Collections.singleton(individual));
		Assertions.assertFalse(archive.updateWithNondominated(Collections.singleton(individual)));
	}

	@Test
	public void updateWithNone() {
		UnboundedArchive archive = new UnboundedArchive();
		Assertions.assertFalse(archive.updateWithNondominated(Collections.<Individual> emptySet()));
	}
}
