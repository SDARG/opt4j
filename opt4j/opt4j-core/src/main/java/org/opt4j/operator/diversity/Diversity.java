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

package org.opt4j.operator.diversity;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Operator;

import com.google.inject.ImplementedBy;

/**
 * The {@link Diversity} determines the genetic diversity of two
 * {@link Individual}s. The genetic diversity is 0 if both {@link Genotype}s are
 * equal and 1 of they are of maximum diversity.
 * 
 * @author glass, lukasiewycz
 * 
 * @param <G>
 *            the type of genotype
 */
@ImplementedBy(DiversityGenericImplementation.class)
public interface Diversity<G extends Genotype> extends Operator<G> {

	/**
	 * Returns the genetic diversity of two {@link Genotype}s.
	 * 
	 * @param a
	 *            the first genotype
	 * @param b
	 *            the second genotype
	 * @return the diversity of two genotypes
	 */
	public double diversity(G a, G b);

}
