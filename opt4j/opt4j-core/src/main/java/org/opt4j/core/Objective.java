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

package org.opt4j.core;

import static org.opt4j.core.Objective.Sign.MIN;

/**
 * <p>
 * The {@link Objective} is the identifier for a single objective in the {@link Objectives}. It is specified by the
 * following properties:
 * </p>
 * <ul>
 * <li>Name</li>
 * <li>Minimization or Maximization</li>
 * </ul>
 * <p>
 * Each {@link org.opt4j.core.problem.Evaluator} sets a specific amount of {@link Objective}-{@link Value} pairs.
 * Moreover, each {@link org.opt4j.core.problem.Evaluator} has to set the same {@link Objective}s for all
 * {@link Phenotype}s.
 * </p>
 * 
 * @see Objectives
 * @author lukasiewycz
 * 
 */
public class Objective implements Comparable<Objective> {

	/**
	 * The sign of the {@link Objective}.
	 */
	public enum Sign {
		/**
		 * Minimize the {@link Objective}.
		 */
		MIN,
		/**
		 * Maximize the {@link Objective}.
		 */
		MAX;
	}

	/**
	 * Identifier for infeasible results ({@code null}).
	 */
	public static final Value<?> INFEASIBLE = null;

	protected final Sign sign;

	protected final String name;

	/**
	 * Constructs an {@link Objective} with a given name, sign=MIN, and rank=RANK_OBJECTIVE(0).
	 * 
	 * @param name
	 *            the name
	 */
	public Objective(String name) {
		this(name, MIN);
	}

	/**
	 * Constructs an {@link Objective} with a given name, sign, and rank=RANK_OBJECTIVE(0).
	 * 
	 * @param name
	 *            the name
	 * @param sign
	 *            the sign of the objective
	 */
	public Objective(String name, Sign sign) {
		this.name = name;
		this.sign = sign;
	}

	/**
	 * Returns the sign.
	 * 
	 * @return the sign
	 */
	public Sign getSign() {
		return sign;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Objective other) {
		if (this.equals(other)) {
			return 0;
		} else if (other == null) {
			return 1;
		} else {
			return this.getName().compareTo(other.getName());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName() + "(" + getSign() + ")";
	}

}
