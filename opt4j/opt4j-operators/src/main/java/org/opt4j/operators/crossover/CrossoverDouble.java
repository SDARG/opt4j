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

import java.util.List;
import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.DoubleGenotype;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

/**
 * Crossover for the {@link DoubleGenotype}.
 * 
 * @author lukasiewycz, glass
 * 
 */
@ImplementedBy(CrossoverDoubleDefault.class)
public abstract class CrossoverDouble implements Crossover<DoubleGenotype> {

	protected final Random random;

	protected final NormalizeDouble normalize;

	/**
	 * Constructs a new crossover for the {@link DoubleGenotype}.
	 * 
	 * @param normalize
	 *            a normalize operator
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverDouble(NormalizeDouble normalize, Rand random) {
		this.normalize = normalize;
		this.random = random;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.Crossover#crossover(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public Pair<DoubleGenotype> crossover(DoubleGenotype p1, DoubleGenotype p2) {
		DoubleGenotype o1 = p1.newInstance();
		DoubleGenotype o2 = p2.newInstance();

		crossover(p1, p2, o1, o2);

		normalize.normalize(o1);
		normalize.normalize(o2);

		Pair<DoubleGenotype> offspring = new Pair<DoubleGenotype>(o1, o2);
		return offspring;
	}

	/**
	 * Performs a crossover of two parent {@link org.opt4j.core.Genotype}s that
	 * consist of double vectors.
	 * 
	 * @param p1
	 *            the first parent
	 * @param p2
	 *            the second parent
	 * @param o1
	 *            the first offspring
	 * @param o2
	 *            the second offspring
	 */
	protected abstract void crossover(List<Double> p1, List<Double> p2, List<Double> o1, List<Double> o2);

}
