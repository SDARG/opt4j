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


package org.opt4j.core;

import static org.opt4j.core.Objective.Sign.MIN;

/**
 * <p>
 * The {@link Objective} is the identifier for a single objective in the
 * {@link Objectives}. It is specified by the following properties:
 * </p>
 * <ul>
 * <li>Name</li>
 * <li>Minimization or Maximization</li>
 * </ul>
 * <p>
 * Each {@link org.opt4j.core.problem.Evaluator} sets a specific amount of
 * {@link Objective}-{@link Value} pairs. Moreover, each
 * {@link org.opt4j.core.problem.Evaluator} has to set the same
 * {@link Objective}s for all phenotypes.
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
	 * Constructs an {@link Objective} with a given name, sign=MIN, and
	 * rank=RANK_OBJECTIVE(0).
	 * 
	 * @param name
	 *            the name
	 */
	public Objective(String name) {
		this(name, MIN);
	}

	/**
	 * Constructs an {@link Objective} with a given name, sign, and
	 * rank=RANK_OBJECTIVE(0).
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Objective other = (Objective) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (sign != other.sign)
			return false;
		return true;
	}

}
