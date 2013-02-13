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
import org.opt4j.genotype.PermutationGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * Neighbor for the {@link PermutationGenotype}. One element is moved to a
 * different position.
 * </p>
 * 
 * <p>
 * Given a permutation {@code 1 2 3 4 5 6 7 8}, this might result in
 * {@code 1 2 6 3 4 5 7 8}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborPermutationInsert implements NeighborPermutation {

	protected final Random random;

	/**
	 * Constructs a {@link NeighborPermutationInsert} operator for the
	 * {@link PermutationGenotype}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborPermutationInsert(Rand random) {
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void neighbor(PermutationGenotype<?> genotype) {
		PermutationGenotype<Object> permutation = (PermutationGenotype<Object>) genotype;

		int size = permutation.size();

		if (size > 1) {
			final int i = random.nextInt(size);

			Object object = permutation.remove(i);
			final int j = random.nextInt(permutation.size());

			permutation.add(j, object);
		}
	}

}
