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


package org.opt4j.satdecoding;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The {@link PooledSolver} is a decorator that enables pooling of
 * {@link Solver} instances and, therefore, a parallel execution of the
 * {@link Solver}s.
 * 
 * @author lukasiewycz
 * 
 */
public class PooledSolver implements Solver {

	protected final Set<Solver> solvers = new HashSet<Solver>();

	protected final BlockingQueue<Solver> queue = new LinkedBlockingQueue<Solver>();

	/**
	 * Constructs a {@link PooledSolver}.
	 * 
	 * @param solverProvider
	 *            a solver provider
	 * @param instances
	 *            the number of pooled instances
	 */
	@Inject
	public PooledSolver(@Constant(value = "solver", namespace = PooledSolver.class) Provider<Solver> solverProvider,
			@Constant(value = "instances", namespace = PooledSolver.class) int instances) {
		for (int i = 0; i < instances; i++) {
			Solver solver = solverProvider.get();
			solvers.add(solver);
			queue.add(solver);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.Solver#addConstraint(org.opt4j.sat.Constraint)
	 */
	@Override
	public void addConstraint(Constraint constraint) {
		for (Solver solver : solvers) {
			solver.addConstraint(constraint);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.sat.Solver#solve(org.opt4j.sat.Order)
	 */
	@Override
	public Model solve(Order order) throws TimeoutException {
		Solver solver = null;
		Model model = null;
		try {
			solver = queue.take();
			model = solver.solve(order);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (solver != null) {
				queue.offer(solver);
			}
		}
		return model;
	}

}
