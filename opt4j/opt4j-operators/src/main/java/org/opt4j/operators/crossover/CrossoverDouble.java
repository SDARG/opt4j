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
