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
