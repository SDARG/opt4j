import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.opt4j.core.Individual;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.common.archive.ArchiveModule;
import org.opt4j.core.common.archive.ArchiveModule.Type;
import org.opt4j.core.optimizer.Archive;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class ArchiveOptimalityTest {
	private static final int NUMBER_OF_INDIVUDUALS = 20000;

	protected final Objective o1 = new Objective("counter");
	protected final Objective o2 = new Objective("-counter");
	protected final Objective o3 = new Objective("rand");

	private Individual first;
	private Individual last;
	private Individual randMin;

	@Test
	public void defaultArchive() {
		archiveModuleTester(new ArchiveModule());
	}

	@Test
	public void adaptiveGridArchive() {
		ArchiveModule adaptiveGrid = new ArchiveModule();
		adaptiveGrid.setType(Type.ADAPTIVE_GRID);
		archiveModuleTester(adaptiveGrid);
	}

	@Test
	public void crowdingArchive() {
		ArchiveModule crowding = new ArchiveModule();
		crowding.setType(Type.CROWDING);
		archiveModuleTester(crowding);
	}

	@Test
	public void unboundedArchive() {
		ArchiveModule unbounded = new ArchiveModule();
		unbounded.setType(Type.UNBOUNDED);
		archiveModuleTester(unbounded);
	}

	protected void archiveModuleTester(ArchiveModule archiveModule) {
		Injector injector = Guice.createInjector(archiveModule);
		Archive archive = injector.getInstance(Archive.class);
		System.out.println("Testing " + archive.getClass());

		fillArchive(injector, archive);

		System.out.println("archive size after adding " + NUMBER_OF_INDIVUDUALS + " individuals: " + archive.size());

		Assert.assertTrue(archive.contains(first));
		Assert.assertTrue(archive.contains(last));
		Assert.assertTrue(archive.contains(randMin));

		testOptimality(archive);
	}

	protected void testOptimality(Archive archive) {
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

	protected void fillArchive(Injector injector, Archive archive) {
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
