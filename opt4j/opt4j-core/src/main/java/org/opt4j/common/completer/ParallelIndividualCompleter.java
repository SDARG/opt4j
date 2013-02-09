/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.common.completer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link ParallelIndividualCompleter} completes {@link Individual}s with multiple threads.
 * 
 * @author lukasiewycz
 * 
 */
public class ParallelIndividualCompleter extends SequentialIndividualCompleter implements OptimizerStateListener {
	protected final ExecutorService executor;

	/**
	 * The {@link Complete} class completes a single {@link Individual}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected class Complete implements Callable<Void> {

		protected final Individual individual;

		protected final Control control;

		/**
		 * Constructs {@link Complete} with an {@link Individual}.
		 * 
		 * @param individual
		 *            the individual to complete
		 * @param control
		 *            the control
		 */
		public Complete(final Individual individual, final Control control) {
			this.individual = individual;
			this.control = control;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Void call() throws TerminationException {
			if (!individual.isEvaluated()) {
				control.checkpoint();
				ParallelIndividualCompleter.this.decode(individual);
				control.checkpoint();
				ParallelIndividualCompleter.this.evaluate(individual);
				control.checkpoint();
			}
			return null;
		}
	}

	/**
	 * Constructs a {@link ParallelIndividualCompleter} with a specified maximal number of concurrent threads.
	 * 
	 * @param control
	 *            the control
	 * @param decoder
	 *            the decoder
	 * @param evaluator
	 *            the evaluator
	 * @param maxThreads
	 *            the maximal number of parallel threads (using namespace {@link ParallelIndividualCompleter})
	 */

	@SuppressWarnings({ "rawtypes" })
	@Inject
	public ParallelIndividualCompleter(Control control, Decoder decoder, Evaluator evaluator,
			@Constant(value = "maxThreads", namespace = ParallelIndividualCompleter.class) int maxThreads) {
		super(control, decoder, evaluator);

		if (maxThreads < 1) {
			throw new IllegalArgumentException("Invalid number of threads: " + maxThreads);
		}
		this.executor = Executors.newFixedThreadPool(maxThreads);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.common.completer.SequentialCompleter#complete(java.lang.Iterable )
	 */
	@Override
	public void complete(Iterable<? extends Individual> iterable) throws TerminationException {

		try {

			List<Future<Void>> returns = new ArrayList<Future<Void>>();

			for (Individual individual : iterable) {
				if (individual.getState() != Individual.State.EVALUATED) {
					returns.add(executor.submit(new Complete(individual, control)));
				}
			}

			for (Future<Void> future : returns) {
				try {
					future.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (ExecutionException ex) {
			executor.shutdownNow();
			if (ex.getCause() instanceof TerminationException) {
				throw (TerminationException) ex.getCause();
			}
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		shutdownExecutorService();
		super.finalize();
	}

	/**
	 * Shutdown the {@link ExecutorService}.
	 */
	protected synchronized void shutdownExecutorService() {
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.OptimizerStateListener#optimizationStarted(org .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.OptimizerStateListener#optimizationStopped(org .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		shutdownExecutorService();
	}
}