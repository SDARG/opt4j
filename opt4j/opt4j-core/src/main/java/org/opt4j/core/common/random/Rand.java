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
package org.opt4j.core.common.random;

import java.util.Random;

import com.google.inject.ImplementedBy;

/**
 * The {@link Rand} class is an abstract class that is utilized as an interface
 * for random classes.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(RandomDefault.class)
public abstract class Rand extends Random {

	/**
	 * Constructs a Rand.
	 */
	public Rand() {
		super();
	}

	/**
	 * Constructs a Rand.
	 * 
	 * @param seed
	 *            the seed
	 */
	public Rand(long seed) {
		super(seed);
	}

	private static final long serialVersionUID = 1L;

}
