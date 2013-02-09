/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.sat;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.opt4j.start.Constant;

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
