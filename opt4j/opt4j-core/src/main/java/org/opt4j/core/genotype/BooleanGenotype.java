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


package org.opt4j.core.genotype;

import java.util.ArrayList;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link BooleanGenotype} consists of Boolean values that can be used as a
 * {@link Genotype}.
 * </p>
 * <p>
 * Example problem: Select on/off state of five switches<br/>
 * Example usage:<blockquote>
 * 
 * <pre>
 * BooleanGenotype genotype = new BooleanGenotype();
 * genotype.init(new Random(), 5);
 * </pre>
 * 
 * </blockquote>Example instance: [false, true, true, true, true]<br/>
 * Example search space size: 2<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class BooleanGenotype extends ArrayList<Boolean> implements ListGenotype<Boolean> {

	/**
	 * Constructs a {@link BooleanGenotype}.
	 */
	public BooleanGenotype() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <G extends Genotype> G newInstance() {
		try {
			return (G) this.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Initialize this genotype with {@code n} random values.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the number of elements in the resulting genotype
	 */
	public void init(Random random, int n) {
		for (int i = 0; i < n; i++) {
			if (i >= size()) {
				add(random.nextBoolean());
			} else {
				set(i, random.nextBoolean());
			}
		}
	}
}
