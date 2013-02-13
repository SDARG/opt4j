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

package org.opt4j.operator.crossover;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;

import com.google.inject.ImplementedBy;

/**
 * Crossover operator that performs a crossover for two parents.
 * 
 * @author lukasiewycz
 * 
 * @param <G>
 *            the type of genotype
 */
@ImplementedBy(CrossoverGenericImplementation.class)
public interface Crossover<G extends Genotype> extends Operator<G> {

	/**
	 * Performs a crossover for two {@link Genotype} parents. The resulting pair
	 * of {@link Genotype} offspring is returned.
	 * 
	 * @param parent1
	 *            The first parent genotype for the crossover
	 * @param parent2
	 *            The second parents genotype for the crossover
	 * @return The resulting pair of offspring genotypes
	 */
	public Pair<G> crossover(G parent1, G parent2);
}
