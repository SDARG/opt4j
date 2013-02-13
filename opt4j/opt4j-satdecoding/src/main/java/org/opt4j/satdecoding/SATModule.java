/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.satdecoding;

import org.opt4j.core.start.Opt4JModule;

/**
 * Module class for a SAT-{@link Solver}.
 * 
 * @author lukasiewycz
 * 
 */
public abstract class SATModule extends Opt4JModule {

	/**
	 * Binds the specific solver as singleton.
	 * 
	 * @param solver
	 *            the solver class to be bound
	 */
	protected void bindSolver(Class<? extends Solver> solver) {
		bind(Solver.class).to(solver).in(SINGLETON);
	}

	/**
	 * Binds the specific solver as singleton.
	 * 
	 * @param solver
	 *            the solver class to be bound
	 * @param instances
	 *            the number of instances for pooling
	 */
	protected void bindSolver(Class<? extends Solver> solver, int instances) {
		if (instances == 1) {
			bindSolver(solver);
		} else {
			bindSolver(PooledSolver.class);
			bind(Solver.class).annotatedWith(constant("solver", PooledSolver.class)).to(solver);
			bindConstant("instances", PooledSolver.class).to(instances);
		}
	}

}
