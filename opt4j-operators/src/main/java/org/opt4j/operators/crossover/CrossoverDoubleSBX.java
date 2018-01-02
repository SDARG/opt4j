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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.start.Constant;
import org.opt4j.operators.normalize.NormalizeDouble;

import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;

/**
 * The {@link CrossoverDoubleSBX} is an implementation of the simulated binary
 * crossover operator proposed by Deb and Kumar, 1995.
 * 
 * <p>
 * The SBX crossover is applied bitwise. In the original work, two offspring are
 * created using
 * </p>
 * <p>
 * {@code z_1 = 0.5 * [(1 + beta)x + (1 - beta)y]}<br>
 * {@code z_2 = 0.5 * [(1 - beta)x + (1 + beta)y]}.
 * </p>
 * <p>
 * In this implementation, one offspring is created while the actual bit is set
 * to {@code z_1} with a probability of {@code 0.5} and to {@code z_2} with a
 * probability of {@code 0.5}, respectively.<br>
 * The value {@code beta} is defined as<br>
 * </p>
 * <p>
 * {@code beta = (2u)^(1 / (nu + 1))} if {@code u <= 0.5} and<br>
 * {@code beta = (1 / (2 (1 - u)))^(1 / (nu + 1))} otherwise
 * </p>
 * <p>
 * At this juncture, {@code u} is a random number between {@code (0, 1)}. The
 * variable {@code nu} influences the crossover with a high value leading to a
 * higher probability of 'near-parent' solution while a small value leads to a
 * higher probability of more distant solutions, respectively.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class CrossoverDoubleSBX extends CrossoverDoubleElementwise {

	@Retention(RUNTIME)
	@BindingAnnotation
	protected @interface Nu {
	}

	protected double nu;

	/**
	 * Constructs a {@link CrossoverDoubleSBX} with a nu value and a random
	 * generator.
	 * 
	 * @param nu
	 *            the {@code nu} value
	 * @param normalize
	 *            the normalize operator
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverDoubleSBX(@Constant(value = "nu", namespace = CrossoverDoubleSBX.class) double nu,
			NormalizeDouble normalize, Rand random) {
		super(normalize, random);
		this.nu = nu;
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
		double u = random.nextDouble(); // [0,1)
		double beta;
		if (0.5 <= u) {
			beta = Math.pow(2 * u, 1 / (nu + 1));
		} else {
			beta = Math.pow(1 / (2 - 2 * u), 1 / (nu + 1));
		}

		double o1 = 0.5 * ((1 + beta) * x + (1 - beta) * y);
		double o2 = 0.5 * ((1 - beta) * x + (1 + beta) * y);

		if (random.nextDouble() < 0.5) {
			double tmp = o1;
			o1 = o2;
			o2 = tmp;
		}

		return new Pair<Double>(o1, o2);
	}

}
