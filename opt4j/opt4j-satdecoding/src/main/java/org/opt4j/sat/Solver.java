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

package org.opt4j.sat;

import com.google.inject.ImplementedBy;

/**
 * The {@link Solver} is an interface for SAT/PB solvers.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(DefaultSolver.class)
public interface Solver {

	/**
	 * Adds a {@link Constraint} to the {@link Solver}.
	 * 
	 * @param constraint
	 *            the added constraint
	 */
	public void addConstraint(Constraint constraint);

	/**
	 * Solve the problem defined by the constraints with a given order
	 * (branching strategy).
	 * 
	 * @param order
	 *            the branching strategy
	 * @return a model that represents a feasible solution
	 * @throws ContradictionException
	 *             if no feasible solution exists
	 * @throws TimeoutException
	 *             if a feasible solution cannot be found within a given amount
	 *             of time
	 */
	public Model solve(Order order) throws TimeoutException, ContradictionException;

}
