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
