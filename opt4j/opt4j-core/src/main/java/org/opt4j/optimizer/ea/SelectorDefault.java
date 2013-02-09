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

package org.opt4j.optimizer.ea;

import org.opt4j.common.archive.Crowding;
import org.opt4j.common.random.Rand;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The default selector is the {@link Nsga2} selector with the
 * {@code tournament} value 0.
 * 
 * @see Nsga2
 * @author lukasiewycz
 * 
 */
@Singleton
public class SelectorDefault extends Nsga2 {

	/**
	 * Constructs a new {@link SelectorDefault}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public SelectorDefault(Rand random) {
		super(random, 0, new Crowding());
	}

}
