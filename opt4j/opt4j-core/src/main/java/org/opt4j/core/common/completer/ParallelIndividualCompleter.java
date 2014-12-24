/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/


package org.opt4j.core.common.completer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link ParallelIndividualCompleter} completes {@link Individual}s with
 * multiple threads.
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
	 * Constructs a {@link ParallelIndividualCompleter} with a specified maximal
	 * number of concurrent threads.
	 * 
	 * @param control
	 *            the control
	 * @param decoder
	 *            the decoder
	 * @param evaluator
	 *            the evaluator
	 * @param maxThreads
	 *            the maximal number of parallel threads (using namespace
	 *            {@link ParallelIndividualCompleter})
	 */
	@Inject
	public ParallelIndividualCompleter(Control control, Decoder<Genotype, Object> decoder, Evaluator<Object> evaluator,
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
	 * @see
	 * org.opt4j.common.completer.SequentialCompleter#complete(java.lang.Iterable
	 * )
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
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStarted(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStopped(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		shutdownExecutorService();
	}
}