package org.opt4j.core.common.archive;

import java.util.Random;

import org.junit.Assert;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Opt4JModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * The {@link AbstractArchiveOptimalityTester} tests an Archive configured with
 * the given {@link Opt4JModule} against different optimality criteria.
 * <p>
 * This class can be reused for additional {@link Archive}s to test.
 * 
 * @author Felix Reimann
 *
 */
public class AbstractArchiveOptimalityTester {
	private static final int NUMBER_OF_INDIVUDUALS = 20000;

	protected final Objective o1 = new Objective("counter");
	protected final Objective o2 = new Objective("-counter");
	protected final Objective o3 = new Objective("rand");

	private Individual first;
	private Individual last;
	private Individual randMin;

	/**
	 * Test a given {@link Opt4JModule}.
	 * 
	 * @param archiveModule
	 *            the archive module under test
	 */
	protected void archiveOptimalityTest(Opt4JModule archiveModule) {
		Injector injector = Guice.createInjector(archiveModule);
		Archive archive = injector.getInstance(Archive.class);

		fillArchive(injector, archive);

		Assert.assertTrue(archive.contains(first));
		Assert.assertTrue(archive.contains(last));
		Assert.assertTrue(archive.contains(randMin));

		testOptimalityOfAllIndividuals(archive);
	}

	/**
	 * This tests fail if an individual is found in the {@link Archive} which is
	 * dominated by other {@link Individual}s in the {@link Archive}.
	 * 
	 * @param archive
	 *            the archive under test
	 */
	private void testOptimalityOfAllIndividuals(Archive archive) {
		while (!archive.isEmpty()) {
			Individual i = archive.iterator().next();
			archive.remove(i);

			for (Individual j : archive) {
				Objectives oi = i.getObjectives();
				Objectives oj = j.getObjectives();
				Assert.assertFalse(oi.get(o1).getDouble() <= oj.get(o1).getDouble()
						&& oi.get(o2).getDouble() <= oj.get(o2).getDouble()
						&& oi.get(o3).getDouble() <= oj.get(o3).getDouble());

				Assert.assertFalse(oi.get(o1).getDouble() >= oj.get(o1).getDouble()
						&& oi.get(o2).getDouble() >= oj.get(o2).getDouble()
						&& oi.get(o3).getDouble() >= oj.get(o3).getDouble());
			}
		}
	}

	/**
	 * Initializes the {@link Archive} with NUMBER_OF_INDIVIDUALS different
	 * {@link Individual}s.
	 * <p>
	 * Therefore. three {@link Objective}s are added:
	 * <ul>
	 * <li>the first dimension is incremented</li>
	 * <li>the second dimension is decremented</li>
	 * <li>the third dimension is set randomly with a static seed.</li>
	 * </ul>
	 * 
	 * @param injector
	 *            the injector
	 * @param archive
	 *            the archive under test
	 */
	private void fillArchive(Injector injector, Archive archive) {
		Random r = new Random(100);
		Provider<Individual> individuals = injector.getProvider(Individual.class);

		first = null;
		last = null;
		randMin = null;
		int smallestRand = Integer.MAX_VALUE;
		for (int i = 0; i < NUMBER_OF_INDIVUDUALS; i++) {
			Individual individual = individuals.get();
			Objectives o = new Objectives();
			o.add(o1, i);
			o.add(o2, -i);
			int randomValue = r.nextInt(Integer.MAX_VALUE);
			o.add(o3, randomValue);
			individual.setObjectives(o);
			archive.update(individual);

			if (i == 0) {
				first = individual;
			}
			if (i == NUMBER_OF_INDIVUDUALS - 1) {
				last = individual;
			}
			if (randomValue < smallestRand) {
				randMin = individual;
				smallestRand = randomValue;
			}
		}
	}

}
