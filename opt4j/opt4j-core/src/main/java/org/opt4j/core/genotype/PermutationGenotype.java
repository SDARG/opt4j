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
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link PermutationGenotype} can be used as a {@link Genotype}. The order
 * of these elements is to be optimized.
 * </p>
 * <p>
 * Example problem: Select the order of five balls ball<sub>1</sub>,
 * ball<sub>2</sub>, ball<sub>3</sub>, ball<sub>4</sub>, ball<sub>5</sub><br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * PermutationGenotype&lt;Ball&gt; genotype = new PermutationGenotype&lt;Ball&gt;(Arrays.asList(ball1, ball2, ball3, ball4, ball5));
 * genotype.init(new Random());
 * </pre>
 * 
 * </blockquote> Example instance: [ball5, ball2, ball1, ball3, ball4]<br/>
 * Example search space size: 5!
 * </p>
 * 
 * @param <E>
 *            the type of elements
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class PermutationGenotype<E> extends ArrayList<E> implements ListGenotype<E> {

	/**
	 * Constructs a {@link PermutationGenotype}.
	 */
	public PermutationGenotype() {
		super();
	}

	/**
	 * Constructs a {@link PermutationGenotype}.
	 * 
	 * @param values
	 *            the initial values
	 */
	public PermutationGenotype(Collection<E> values) {
		super(values);
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
	 * Randomizes this genotype by a random permutation.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public void init(Random random) {
		Collections.shuffle(this, random);
	}
}
