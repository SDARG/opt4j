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
