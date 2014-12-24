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

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the {@link Crossover} interface.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class CrossoverGenericImplementation extends AbstractGenericOperator<Crossover<Genotype>, Crossover<?>>
		implements Crossover<Genotype> {

	/**
	 * Constructs the {@link CrossoverGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected CrossoverGenericImplementation() {
		super(CrossoverBoolean.class, CrossoverDouble.class, CrossoverInteger.class, CrossoverPermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.crossover.Crossover#crossover(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public Pair<Genotype> crossover(Genotype parent1, Genotype parent2) {
		Crossover<Genotype> crossover = getOperator(parent1);

		if (crossover == null) {
			return crossoverComposite((CompositeGenotype<?, ?>) parent1, (CompositeGenotype<?, ?>) parent2);
		} else {
			return crossover.crossover(parent1, parent2);
		}
	}

	protected Pair<Genotype> crossoverComposite(CompositeGenotype<?, ?> p1, CompositeGenotype<?, ?> p2) {
		CompositeGenotype<Object, Genotype> o1 = p1.newInstance();
		CompositeGenotype<Object, Genotype> o2 = p2.newInstance();

		for (Object key : p1.keySet()) {
			final Genotype g1 = p1.get(key);
			final Genotype g2 = p2.get(key);
			Pair<Genotype> genotype = crossover(g1, g2);
			o1.put(key, genotype.getFirst());
			o2.put(key, genotype.getSecond());
		}

		Pair<Genotype> offspring = new Pair<Genotype>(o1, o2);
		return offspring;
	}
}
