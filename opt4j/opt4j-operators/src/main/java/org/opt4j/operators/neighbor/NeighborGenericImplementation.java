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

import org.opt4j.core.Genotype;
import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the {@link Neighbor} interface.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class NeighborGenericImplementation extends AbstractGenericOperator<Neighbor<Genotype>, Neighbor<?>> implements
		Neighbor<Genotype> {

	protected final Rand random;

	/**
	 * Constructs the {@link NeighborGenericImplementation}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected NeighborGenericImplementation(Rand random) {
		super(NeighborBoolean.class, NeighborDouble.class, NeighborInteger.class, NeighborPermutation.class);
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.neighbor.Neighbor#neighbor(org.opt4j.core.Genotype)
	 */
	@Override
	public void neighbor(Genotype genotype) {
		Neighbor<Genotype> neighbor = getOperator(genotype);
		if (neighbor == null) {
			neighborComposite((CompositeGenotype<?, ?>) genotype);
		} else {
			neighbor.neighbor(genotype);
		}
	}

	protected void neighborComposite(CompositeGenotype<?, ?> genotype) {
		int size = genotype.size();

		final int i = random.nextInt(size);
		int sum = 0;

		Genotype g = null;

		for (Genotype entry : genotype.values()) {
			g = entry;
			sum += g.size();
			if (i < sum) {
				break;
			}
		}

		neighbor(g);
	}

}
