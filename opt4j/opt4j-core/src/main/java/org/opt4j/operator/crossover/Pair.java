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

package org.opt4j.operator.crossover;

/**
 * The {@link Pair} groups two objects of the same type in a given order.
 * 
 * @author glass
 * @param <A>
 *            the type of the paired objects
 */
public class Pair<A> {

	protected final A first;

	protected final A second;

	/**
	 * Constructs a {@link Pair} with a first and a second element.
	 * 
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 */
	public Pair(A first, A second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * Returns the first element.
	 * 
	 * @return the first element
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * Returns the second element.
	 * 
	 * @return the second element
	 */
	public A getSecond() {
		return second;
	}
}
