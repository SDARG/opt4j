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
 

package org.opt4j.operators.diversity;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link DiversityGenericImplementation} is a standard implementation of
 * the {@link Diversity} interface.
 * 
 * @author glass, lukasiewycz
 * 
 */
@Singleton
public class DiversityGenericImplementation extends AbstractGenericOperator<Diversity<Genotype>, Diversity<?>>
		implements Diversity<Genotype> {

	/**
	 * Constructs the {@link DiversityGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected DiversityGenericImplementation() {
		super(DiversityBoolean.class, DiversityDouble.class, DiversityInteger.class, DiversityPermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.diversity.Diversity#diversity(org.opt4j.core.Genotype,
	 * org.opt4j.core.Genotype)
	 */
	@Override
	public double diversity(Genotype a, Genotype b) {
		Diversity<Genotype> diversity = getOperator(a);

		if (diversity == null) {
			return diversityComposite((CompositeGenotype<?, ?>) a, (CompositeGenotype<?, ?>) b);
		} else {
			return diversity.diversity(a, b);
		}
	}

	protected double diversityComposite(CompositeGenotype<?, ?> a, CompositeGenotype<?, ?> b) {

		double diversity = 0;
		for (Object key : a.keySet()) {
			Genotype childA = a.get(key);
			Genotype childB = b.get(key);
			int size = childA.size();

			diversity += diversity(childA, childB) * size;
		}
		return diversity / a.size();

	}

}
