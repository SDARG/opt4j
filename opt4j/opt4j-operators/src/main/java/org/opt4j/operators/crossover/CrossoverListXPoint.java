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

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.genotype.ListGenotype;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link CrossoverListXPoint} performs a crossover on
 * {@link org.opt4j.core.Genotype} objects that are lists of values.
 * </p>
 * <p>
 * The crossover is performed on {@code x} points of the
 * {@link org.opt4j.core.Genotype}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public abstract class CrossoverListXPoint<G extends ListGenotype<?>> implements Crossover<G> {

	protected final int x;

	protected final Random random;

	/**
	 * Constructs a {@link CrossoverListXPoint}.
	 * 
	 * @param x
	 *            the number of crossover points
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public CrossoverListXPoint(int x, Rand random) {
		this.x = x;
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
	@SuppressWarnings("unchecked")
	public Pair<G> crossover(G p1, G p2) {

		ListGenotype<Object> o1 = p1.newInstance();
		ListGenotype<Object> o2 = p2.newInstance();

		int size = p1.size();

		if (x <= 0 || x > size - 1) {
			throw new RuntimeException(this.getClass() + " : x is " + x + " for binary vector size " + size);
		}

		SortedSet<Integer> points = new TreeSet<Integer>();

		while (points.size() < x) {
			points.add(random.nextInt(size - 1) + 1);
		}

		int flip = 0;
		boolean select = random.nextBoolean();

		for (int i = 0; i < size; i++) {
			if (i == flip) {
				select = !select;

				if (points.size() > 0) {
					flip = points.first();
					points.remove(flip);
				}
			}

			if (select) {
				o1.add(p1.get(i));
				o2.add(p2.get(i));
			} else {
				o1.add(p2.get(i));
				o2.add(p1.get(i));
			}
		}

		Pair<G> offspring = new Pair<G>((G) o1, (G) o2);
		return offspring;
	}

}
