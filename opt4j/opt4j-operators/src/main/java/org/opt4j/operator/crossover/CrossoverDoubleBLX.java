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

package org.opt4j.operator.crossover;

import org.opt4j.common.random.Rand;
import org.opt4j.operator.normalize.NormalizeDouble;
import org.opt4j.start.Constant;

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
