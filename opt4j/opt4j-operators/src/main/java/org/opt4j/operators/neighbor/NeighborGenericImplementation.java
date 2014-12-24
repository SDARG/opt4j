/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

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
