package org.opt4j.core.common.archive;

import org.junit.Test;
import org.opt4j.core.common.archive.ArchiveModule.Type;

/**
 * The {@link ArchiveModuleTest} tests the {@link ArchiveModule} using the
 * {@link AbstractArchiveOptimalityTester}.
 * <p>
 * The {@link PopulationArchive} remains untested as it violates the optimality
 * criterion.
 * 
 * @author Felix Reimann
 *
 */
public class ArchiveModuleTest extends AbstractArchiveOptimalityTester {

	/**
	 * Tests the {@link DefaultArchive}.
	 */
	@Test
	public void defaultArchive() {
		archiveOptimalityTest(new ArchiveModule());
	}

	/**
	 * Tests the {@link AdaptiveGridArchive}.
	 */
	@Test
	public void adaptiveGridArchive() {
		ArchiveModule adaptiveGrid = new ArchiveModule();
		adaptiveGrid.setType(Type.ADAPTIVE_GRID);
		archiveOptimalityTest(adaptiveGrid);
	}

	/**
	 * Tests the {@link CrowdingArchive}.
	 */
	@Test
	public void crowdingArchive() {
		ArchiveModule crowding = new ArchiveModule();
		crowding.setType(Type.CROWDING);
		archiveOptimalityTest(crowding);
	}

	/**
	 * Tests the {@link UnboundedArchive}.
	 */
	@Test
	public void unboundedArchive() {
		ArchiveModule unbounded = new ArchiveModule();
		unbounded.setType(Type.UNBOUNDED);
		archiveOptimalityTest(unbounded);
	}
}
