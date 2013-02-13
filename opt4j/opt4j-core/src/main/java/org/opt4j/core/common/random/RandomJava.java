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

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link RandomJava} is the default java {@link Random}.
 * 
 * @author helwig, lukasiewycz
 */
@SuppressWarnings("serial")
@Singleton
public class RandomJava extends Rand {

	/**
	 * Constructs a {@link RandomJava} with the specified seed.
	 * 
	 * @param seed
	 *            the seed value (using namespace {@link Random})
	 */
	@Inject
	public RandomJava(@Constant(value = "seed", namespace = Random.class) long seed) {
		super(seed);
	}

}
