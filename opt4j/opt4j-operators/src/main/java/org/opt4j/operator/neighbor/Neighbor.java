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

package org.opt4j.operator.neighbor;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;

import com.google.inject.ImplementedBy;

/**
 * The {@link Neighbor} operator as it is used for Simulated Annealing.
 * 
 * @author lukasiewycz
 * 
 * @param <G>
 *            the type of genotype
 */
@ImplementedBy(NeighborGenericImplementation.class)
public interface Neighbor<G extends Genotype> extends Operator<G> {

	/**
	 * Moves the {@link Genotype} to a neighbor.
	 * 
	 * @param genotype
	 *            the {@link Genotype} to be changed
	 */
	public void neighbor(G genotype);

}
