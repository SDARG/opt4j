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
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.operator.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link NeighborDouble} operator for the {@link DoubleGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class NeighborDouble implements Neighbor<DoubleGenotype> {

	protected final Random random;

	protected final NormalizeDouble normalize;

	/**
	 * Constructs a {@link NeighborDouble}.
	 * 
	 * @param normalize
	 *            the operator for normalization
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public NeighborDouble(final NormalizeDouble normalize, Rand random) {
		this.normalize = normalize;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(DoubleGenotype genotype) {
		int size = genotype.size();

		int i = random.nextInt(size);

		double value = genotype.get(i) + random.nextDouble() * 0.1 - 0.05;
		genotype.set(i, value);

		normalize.normalize(genotype);
	}

}
