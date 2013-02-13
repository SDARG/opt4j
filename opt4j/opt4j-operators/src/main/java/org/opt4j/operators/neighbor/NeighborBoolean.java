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

package org.opt4j.operators.neighbor;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.operators.diversity.DiversityBoolean;

import com.google.inject.Inject;

/**
 * The {@link DiversityBoolean} operator for the {@link BooleanGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborBoolean implements Neighbor<BooleanGenotype> {

	protected final Random random;

	/**
	 * Constructs a {@link Neighbor} operator for the {@link BooleanGenotype}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborBoolean(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(BooleanGenotype genotype) {
		int size = genotype.size();

		int i = random.nextInt(size);

		genotype.set(i, !genotype.get(i));
	}

}
