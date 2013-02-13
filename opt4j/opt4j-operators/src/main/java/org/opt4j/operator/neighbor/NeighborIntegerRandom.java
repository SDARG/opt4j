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

package org.opt4j.operator.neighbor;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.genotype.IntegerGenotype;

import com.google.inject.Inject;

/**
 * The {@link NeighborIntegerRandom} selects on element of an
 * {@link IntegerGenotype} and changes it. The neighbor is created randomly
 * between the lower and upper bounds.
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborIntegerRandom implements NeighborInteger {

	protected final Random random;

	/**
	 * Constructs a {@link NeighborIntegerRandom}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborIntegerRandom(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(IntegerGenotype genotype) {
		int size = genotype.size();

		final int i = random.nextInt(size);

		int value = genotype.get(i);
		int ub = genotype.getUpperBound(i);
		int lb = genotype.getLowerBound(i);
		int diff = ub - lb;

		if (diff > 0) {
			int r = (diff == 1) ? 0 : random.nextInt(diff);
			int n = r + lb;

			if (n >= value) {
				n++;
			}
			genotype.set(i, n);
		}

	}
}
