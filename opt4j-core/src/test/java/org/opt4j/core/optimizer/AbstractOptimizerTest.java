package org.opt4j.core.optimizer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.opt4j.core.Individual;

public class AbstractOptimizerTest {

	protected boolean started = false;
	protected boolean archiveHasRun = false;
	protected boolean completerHasRun = false;

	class MockOptimizer extends AbstractOptimizer {
		protected TestMethod t;

		public MockOptimizer(Population population, Archive archive, IndividualCompleter completer, Control control,
				Iteration iteration) {
			super(population, archive, completer, control, iteration);
		}

		public void setOptimizeTest(TestMethod t) {
			this.t = t;
		}

		@Override
		public void optimize() throws StopException, TerminationException {
			t.test();
		}
	}

	private interface TestMethod {
		public void test() throws TerminationException, StopException;
	}

	private class MockIndividual extends Individual {

	}

	@Test
	public void getIteration() {
		MockOptimizer optimizer = new MockOptimizer(null, null, null, null, new Iteration(2));
		Assertions.assertEquals(0, optimizer.getIteration());
	}

	@Test
	public void isRunning() {
		MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		Assertions.assertFalse(optimizer.isRunning());
	}

	@Test
	public void startOptimization() throws TerminationException {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		Assertions.assertFalse(optimizer.isRunning());
		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				Assertions.assertTrue(optimizer.isRunning());
			}
		});
		optimizer.startOptimization();
		Assertions.assertFalse(optimizer.isRunning());
	}

	@Test
	public void startOptimizationTerminating() throws TerminationException {
		final Control c = new Control();
		MockOptimizer optimizer = new MockOptimizer(null, null, null, c, null);
		Assertions.assertFalse(optimizer.isRunning());
		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				c.doTerminate();
				c.checkpoint();
				Assertions.fail();
			}
		});
		optimizer.startOptimization();
		Assertions.assertFalse(optimizer.isRunning());
	}

	@Test
	public void startOptimizationWithListener() throws TerminationException {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		started = false;
		final OptimizerStateListener l = new OptimizerStateListener() {

			@Override
			public void optimizationStarted(Optimizer optimizer) {
				Assertions.assertFalse(started);
				started = true;
			}

			@Override
			public void optimizationStopped(Optimizer optimizer) {
				// nothing to be done
			}

		};
		optimizer.addOptimizerStateListener(l);

		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				// nothing to be done
			}
		});
		optimizer.startOptimization();
		Assertions.assertTrue(started);
	}

	@Test
	public void nextIteration() throws TerminationException {
		final Control c = new Control();
		Population p = new Population();
		final Individual i1 = new MockIndividual();
		p.add(i1);
		Archive a = new Archive() {

			@Override
			public boolean update(Set<? extends Individual> individuals) {
				archiveHasRun = true;
				Assertions.assertTrue(individuals.contains(i1));
				return true;
			}
		};
		IndividualCompleter completer = new IndividualCompleter() {

			@Override
			public void complete(Individual... individuals) throws TerminationException {
				complete(Arrays.asList(individuals));
			}

			@Override
			public void complete(Iterable<? extends Individual> iterable) throws TerminationException {
				completerHasRun = true;
				Assertions.assertTrue(iterable.iterator().hasNext());
				Assertions.assertSame(i1, iterable.iterator().next());
			}
		};
		final MockOptimizer optimizer = new MockOptimizer(p, a, completer, c, new Iteration(10));
		Assertions.assertFalse(optimizer.isRunning());
		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				c.doStop();
				c.checkpoint();
				optimizer.nextIteration();
			}
		});
		optimizer.startOptimization();
		Assertions.assertFalse(optimizer.isRunning());
		Assertions.assertTrue(archiveHasRun);
		Assertions.assertTrue(completerHasRun);
	}

	@Test
	public void nextIterationWithListeners() throws TerminationException {
		final Control c = new Control();
		Population p = new Population();
		final Individual i1 = new MockIndividual();
		p.add(i1);
		Archive a = new Archive() {
			@Override
			public boolean update(Set<? extends Individual> individuals) {
				return true;
			}
		};
		IndividualCompleter completer = new IndividualCompleter() {

			@Override
			public void complete(Individual... individuals) throws TerminationException {
				// nothing to be done
			}

			@Override
			public void complete(Iterable<? extends Individual> iterable) throws TerminationException {
				// nothing to be done
			}
		};
		final MockOptimizer optimizer = new MockOptimizer(p, a, completer, c, new Iteration(10));

		started = false;
		OptimizerIterationListener l1 = (int iteration) -> {
			Assertions.assertFalse(started);
			Assertions.assertEquals(1, iteration);
			started = true;
		};
		optimizer.addOptimizerIterationListener(l1);

		Assertions.assertFalse(optimizer.isRunning());
		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				c.doStop();
				c.checkpoint();
				optimizer.nextIteration();
			}
		});
		optimizer.startOptimization();
		Assertions.assertTrue(started);
	}

	@Test
	public void startOptimizationStopped() throws TerminationException {
		final Control c = new Control();
		final MockOptimizer optimizer = new MockOptimizer(null, new Archive() {
			@Override
			public boolean update(Set<? extends Individual> individuals) {
				return false;
			}
		}, new IndividualCompleter() {
			@Override
			public void complete(Individual... individuals) throws TerminationException {
				// nothing to be done
			}

			@Override
			public void complete(Iterable<? extends Individual> iterable) throws TerminationException {
				// nothing to be done
			}
		}, c, new Iteration(4));
		Assertions.assertFalse(optimizer.isRunning());
		optimizer.setOptimizeTest(new TestMethod() {
			@Override
			public void test() throws TerminationException, StopException {
				c.doStop();
				c.checkpoint();
				optimizer.nextIteration();
				Assertions.fail();
			}
		});
		optimizer.startOptimization();
		Assertions.assertFalse(optimizer.isRunning());
	}

	@Test
	public void addOptimizerIterationListener() {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		OptimizerIterationListener l = (int iteration) -> {
			// nothing to be done
		};
		optimizer.addOptimizerIterationListener(l);

		Assertions.assertEquals(1, optimizer.iterationListeners.size());
		Assertions.assertSame(l, optimizer.iterationListeners.iterator().next());
	}

	@Test
	public void addOptimizerStateListener() {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		OptimizerStateListener l = new OptimizerStateListener() {
			@Override
			public void optimizationStarted(Optimizer optimizer) {
				// nothing to be done
			}

			@Override
			public void optimizationStopped(Optimizer optimizer) {
				// nothing to be done
			}

		};
		optimizer.addOptimizerStateListener(l);

		Assertions.assertEquals(1, optimizer.stateListeners.size());
		Assertions.assertSame(l, optimizer.stateListeners.iterator().next());
	}

	@Test
	public void removeOptimizerIterationListener() {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		OptimizerIterationListener l = (int iteration) -> {
			// nothing to be done
		};
		optimizer.addOptimizerIterationListener(l);
		optimizer.removeOptimizerIterationListener(l);
		Assertions.assertTrue(optimizer.iterationListeners.isEmpty());
	}

	@Test
	public void removeOptimizerStateListener() {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		OptimizerStateListener l = new OptimizerStateListener() {
			@Override
			public void optimizationStarted(Optimizer optimizer) {
				// nothing to be done
			}

			@Override
			public void optimizationStopped(Optimizer optimizer) {
				// nothing to be done
			}

		};
		optimizer.addOptimizerStateListener(l);

		optimizer.removeOptimizerStateListener(l);
		Assertions.assertTrue(optimizer.stateListeners.isEmpty());
	}

	@Test
	public void injectListeners() {
		final MockOptimizer optimizer = new MockOptimizer(null, null, null, null, null);
		OptimizerIterationListener l1 = (int iteration) -> {
			// nothing to be done
		};
		OptimizerStateListener l2 = new OptimizerStateListener() {
			@Override
			public void optimizationStarted(Optimizer optimizer) {
				// nothing to be done
			}

			@Override
			public void optimizationStopped(Optimizer optimizer) {
				// nothing to be done
			}

		};
		optimizer.injectListeners(Collections.singleton(l2), Collections.singleton(l1));

		Assertions.assertSame(l1, optimizer.iterationListeners.iterator().next());
		Assertions.assertSame(l2, optimizer.stateListeners.iterator().next());
	}
}
