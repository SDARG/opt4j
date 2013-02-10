/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.operator.copy;

import org.opt4j.genotype.BooleanGenotype;
import org.opt4j.genotype.DoubleGenotype;
import org.opt4j.genotype.ListGenotype;
import org.opt4j.genotype.PermutationGenotype;

/**
 * Copy operator for plain lists like {@link BooleanGenotype},
 * {@link DoubleGenotype}, and {@link PermutationGenotype}.
 * 
 * @author lukasiewycz
 * 
 */
public class CopyList implements Copy<ListGenotype<?>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.copy.Copy#copy(org.opt4j.core.Genotype)
	 */
	@Override
	public ListGenotype<?> copy(ListGenotype<?> genotype) {
		ListGenotype<Object> copy = genotype.newInstance();

		for (Object element : genotype) {
			copy.add(element);
		}

		return copy;
	}

}
