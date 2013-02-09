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
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.operator.normalize.NormalizeDouble;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * Mutate for the {@link DoubleGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(MutateDoubleDefault.class)
public abstract class MutateDouble implements Mutate<DoubleGenotype> {

	protected final Random random;

	protected final NormalizeDouble normalize;

	/**
	 * Constructs a {@link MutateDouble}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param normalize
	 *            the normalizer
	 */
	@Inject
	public MutateDouble(Rand random, NormalizeDouble normalize) {
		this.random = random;
		this.normalize = normalize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(DoubleGenotype genotype, double p) {
		mutateList(genotype, p);
		normalize.normalize(genotype);
	}

	/**
	 * The mutate internal function.
	 * 
	 * @param vector
	 *            the vector of double values
	 * @param p
	 *            the mutation rate
	 */
	protected abstract void mutateList(DoubleGenotype vector, double p);
}
