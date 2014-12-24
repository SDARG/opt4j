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
 

package org.opt4j.operators.copy;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.operators.AbstractGenericOperator;

import com.google.inject.Inject;

/**
 * Implementation of the {@link Copy} interface.
 * 
 * @author lukasiewycz
 * 
 */
public class CopyGenericImplementation extends AbstractGenericOperator<Copy<Genotype>, Copy<?>> implements
		Copy<Genotype> {

	/**
	 * Constructs the {@link CopyGenericImplementation}.
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected CopyGenericImplementation() {
		super(CopyList.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.copy.Copy#copy(org.opt4j.core.Genotype)
	 */
	@Override
	public Genotype copy(Genotype genotype) {
		Copy<Genotype> copy = getOperator(genotype);
		if (copy == null) {
			return copyComposite((CompositeGenotype<?, ?>) genotype);
		} else {
			return copy.copy(genotype);
		}
	}

	protected CompositeGenotype<?, ?> copyComposite(CompositeGenotype<?, ?> genotype) {
		CompositeGenotype<Object, Genotype> offspring = genotype.newInstance();
		offspring.clear();

		for (final Object key : genotype.keySet()) {
			final Genotype g = genotype.get(key);
			Genotype go = copy(g);
			offspring.put(key, go);
		}
		return offspring;
	}

}
