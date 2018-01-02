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
 

package org.opt4j.operators.mutate;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation of the {@link Mutate} interface.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class MutateGenericImplementation extends AbstractGenericOperator<Mutate<Genotype>, Mutate<?>> implements
		Mutate<Genotype> {

	/**
	 * Constructs the {@link MutateGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected MutateGenericImplementation() {
		super(MutateBoolean.class, MutateDouble.class, MutateInteger.class, MutatePermutation.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.mutate.Mutate#mutate(org.opt4j.core.problem.Genotype,
	 * double)
	 */
	@Override
	public void mutate(Genotype genotype, double p) {
		Mutate<Genotype> mutate = getOperator(genotype);

		if (mutate == null) {
			mutateComposite((CompositeGenotype<?, ?>) genotype, p);
		} else {
			mutate.mutate(genotype, p);
		}
	}

	protected void mutateComposite(CompositeGenotype<?, ?> genotype, double p) {
		for (Genotype entry : genotype.values()) {
			mutate(entry, p);
		}
	}
}
