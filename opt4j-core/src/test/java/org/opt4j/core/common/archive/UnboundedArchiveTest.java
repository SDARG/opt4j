package org.opt4j.core.common.archive;

import static org.mockito.Mockito.mock;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual;

public class UnboundedArchiveTest {

	@Test
	public void updateWithNondominated() {
		Individual individual = mock(Individual.class);

		UnboundedArchive archive = new UnboundedArchive();
		Assert.assertFalse(archive.iterator().hasNext());
		Assert.assertTrue(archive.updateWithNondominated(Collections.singleton(individual)));
		Assert.assertTrue(archive.iterator().hasNext());
		Assert.assertEquals(archive.iterator().next(), individual);
	}

	@Test
	public void updateWithExisting() {
		Individual individual = mock(Individual.class);

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
