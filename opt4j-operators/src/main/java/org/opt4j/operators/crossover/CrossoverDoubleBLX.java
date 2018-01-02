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

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.Inject;

/**
 * The {@link CrossoverDoubleBLX} is an implementation of the blend crossover
 * operator proposed by Eshelman and Schaffer, 1993.
 * 
 * <p>
 * The BLX crossover is applied element-wise. For two double values {@code y}
 * and {@code x} (assuming {@code y > x} one offspring value is created in the
 * uniform interval {@code [x-(y-x)*alpha;y+(y-x)*alpha]}.
 * </p>
 * 
 * <p>
 * If {@code alpha} is set to {@code 0}, the operator creates a random solution
 * between {@code x} and {@code y}. Values greater zero allow offspring that is
 * apart from the interval between {@code x} and {@code y}. The authors report
 * best performance with {@code alpha = 0.5} which is then called the BLX-0.5
 * operator.
 * </p>
 * 
 * @author glass, lukasiewycz
 * 
 */
public class CrossoverDoubleBLX extends CrossoverDoubleElementwise {

	protected final double alpha;

	/**
	 * Constructs a {@link CrossoverDoubleBLX} with an alpha value, an
	 * {@link NormalizeDouble} operator, and a random number generator.
	 * 
	 * @param alpha
	 *            the alpha value
	 * @param normalize
	 *            the normalize operator
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverDoubleBLX(@Constant(value = "alpha", namespace = CrossoverDoubleBLX.class) double alpha,
			NormalizeDouble normalize, Rand random) {
		super(normalize, random);
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.CrossoverDoubleElementwise#crossover(double,
	 * double)
	 */
	@Override
	public Pair<Double> crossover(double x, double y) {
		double lo = Math.min(x, y);
		double hi = Math.max(x, y);

		double o1 = next(lo, hi);
		double o2 = next(lo, hi);

		return new Pair<Double>(o1, o2);
	}

	/**
	 * Calculates a random value in the interval
	 * {@code [lo-(hi-lo)*alpha;hi+(hi-lo)*alpha]}.
	 * 
	 * @param lo
	 *            the smaller double value
	 * @param hi
	 *            the bigger double value
	 * @return a random value in the interval
	 */
	protected double next(double lo, double hi) {
		double u = random.nextDouble();
		double gamma = (1 + 2 * alpha) * u - alpha;

		double o = lo + gamma * (hi - lo);
		return o;
	}

}
