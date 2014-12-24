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

import org.opt4j.core.common.random.Rand;
import org.opt4j.operators.normalize.NormalizeDouble;

/**
 * The {@link CrossoverDoubleElementwise} can be used to derive
 * {@link CrossoverDouble} classOperators that can work element-wise on the
 * double vectors.
 * 
 * @author glass
 * 
 */
public abstract class CrossoverDoubleElementwise extends CrossoverDouble {

	/**
	 * Constructs a new {@link CrossoverDoubleElementwise}.
	 * 
	 * @param normalize
	 *            the normalize operator
	 * @param random
	 *            the random number generator
	 */
	public CrossoverDoubleElementwise(NormalizeDouble normalize, Rand random) {
		super(normalize, random);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.CrossoverDouble#crossover(java.util.List,
	 * java.util.List, java.util.List, java.util.List)
	 */
	@Override
	protected void crossover(List<Double> p1, List<Double> p2, List<Double> o1, List<Double> o2) {
		int size = p1.size();

		for (int i = 0; i < size; i++) {
			Pair<Double> values = crossover(p1.get(i), p2.get(i));
			o1.add(values.getFirst());
			o2.add(values.getSecond());
		}
	}

	/**
	 * Performs a crossover with two double values.
	 * 
	 * @param x
	 *            the first value
	 * @param y
	 *            the second value
	 * @return the resulting values
	 */
	public abstract Pair<Double> crossover(double x, double y);

}
