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

package org.opt4j.operator.mutate;

import org.opt4j.common.random.Rand;
import org.opt4j.operator.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link MutateDoubleDefault} is the {@link MutateDoublePolynomial} with
 * {@code eta=20}.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateDoubleDefault extends MutateDoublePolynomial {

	/**
	 * Constructs a {@link MutateDoubleDefault} with a {@link Rand} random
	 * number generator, and a {@link NormalizeDouble} operator.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalizer
	 */
	@Inject
	public MutateDoubleDefault(Rand random, NormalizeDouble normalize) {
		super(random, normalize, 20);
	}

}
