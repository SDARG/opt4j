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
import org.opt4j.core.genotype.IntegerGenotype;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link CrossoverIntegerXPoint} is the {@link CrossoverListXPoint} for the
 * {@link IntegerGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class CrossoverIntegerXPoint extends CrossoverListXPoint<IntegerGenotype> implements CrossoverInteger {

	/**
	 * Constructs a {@link CrossoverIntegerXPoint}.
	 * 
	 * @param x
	 *            the number of crossover points
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverIntegerXPoint(@Constant(value = "x", namespace = CrossoverIntegerXPoint.class) int x, Rand random) {
		super(x, random);
	}

}
