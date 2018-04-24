package org.opt4j.core.common.archive;

import org.junit.Test;
import org.opt4j.core.common.archive.ArchiveModule.Type;

/**
 * The {@link ArchiveModuleTest} tests the {@link ArchiveModule} using the {@link AbstractArchiveOptimalityTester}.
 * 
 * The {@link PopulationArchive} remains untested as it violates the optimality criterion.
 * 
 * @author Felix Reimann
 *
 */
public class ArchiveModuleTest extends AbstractArchiveOptimalityTester {

	@Test
	public void defaultArchive() {
		archiveOptimalityTest(new ArchiveModule());
	}

	@Test
	public void adaptiveGridArchive() {
		ArchiveModule adaptiveGrid = new ArchiveModule();
		adaptiveGrid.setType(Type.ADAPTIVE_GRID);
		archiveOptimalityTest(adaptiveGrid);
	}

	@Test
	public void crowdingArchive() {
		ArchiveModule crowding = new ArchiveModule();
		crowding.setType(Type.CROWDING);
		archiveOptimalityTest(crowding);
	}

	@Test
	public void unboundedArchive() {
		ArchiveModule unbounded = new ArchiveModule();
		unbounded.setType(Type.UNBOUNDED);
		archiveOptimalityTest(unbounded);
	}
}
