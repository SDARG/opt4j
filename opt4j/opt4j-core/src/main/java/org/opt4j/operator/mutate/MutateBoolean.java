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

package org.opt4j.operator.mutate;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.BooleanGenotype;

import com.google.inject.Inject;

/**
 * Mutate for the {@link BooleanGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class MutateBoolean implements Mutate<BooleanGenotype> {

	protected final Random random;

	/**
	 * Constructs a new {@link MutateBoolean} with the given mutation rate.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public MutateBoolean(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(BooleanGenotype genotype, double p) {
		int size = genotype.size();

		for (int i = 0; i < size; i++) {
			if (random.nextDouble() < p) {
				genotype.set(i, !genotype.get(i));
			}
		}

	}

}
