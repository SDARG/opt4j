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

package org.opt4j.operators.crossover;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.PermutationGenotype;

import com.google.inject.Inject;

/**
 * Crossover for the {@link PermutationGenotype}. The default operator is the
 * {@link CrossoverPermutationOnePoint} with the {@code rotation=false}.
 * 
 * @author lukasiewycz
 * 
 */
public class CrossoverPermutationDefault extends CrossoverPermutationOnePoint {

	/**
	 * Constructs a default {@link CrossoverPermutation}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverPermutationDefault(Rand random) {
		super(random, false);
	}

}
