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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.operators.algebra;

import java.util.ArrayList;
import java.util.List;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link AlgebraGenericImplementation} is a standard implementation of the
 * {@link Algebra} interface.
 * 
 * @author glass, lukasiewycz
 * 
 */
@Singleton
public class AlgebraGenericImplementation extends AbstractGenericOperator<Algebra<Genotype>, Algebra<?>>
		implements Algebra<Genotype> {

	/**
	 * Constructs the {@link AlgebraGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected AlgebraGenericImplementation() {
		super(AlgebraDouble.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.algebra.Algebra#algebra(org.opt4j.operator.algebra
	 * .Term, org.opt4j.core.Genotype[])
	 */
	@Override
	public Genotype algebra(Term term, Genotype... genotypes) {
		Algebra<Genotype> algebra = getOperator(genotypes[0]);
		if (algebra == null) {
			return algebraComposite(term, genotypes);
		} else {
			return algebra.algebra(term, genotypes);
		}
	}

	protected CompositeGenotype<?, ?> algebraComposite(Term term, Genotype... genotypes) {
		int n = genotypes.length;
		List<CompositeGenotype<?, ?>> composites = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			composites.add((CompositeGenotype<?, ?>) genotypes[i]);
		}

		CompositeGenotype<Object, Genotype> offspring = composites.get(0).newInstance();

		for (Object key : composites.get(0).keySet()) {
			Genotype[] g = new Genotype[n];
			for (int i = 0; i < n; i++) {
				if (composites.get(i) == null) {
					g[i] = null;
				} else {
					Genotype genotype = composites.get(i).get(key);
					g[i] = genotype;
				}
			}
			Genotype value = algebra(term, g);
			offspring.put(key, value);
		}

		return offspring;
	}

}
