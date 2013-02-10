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

package org.opt4j.optimizer.ea;

import java.util.Collection;
import java.util.List;

import org.opt4j.core.Individual;
import org.opt4j.operator.crossover.Pair;

import com.google.inject.ImplementedBy;

/**
 * The {@link Coupler} determines couples that are used to create the offspring
 * for a given set of possible parents.
 * 
 * @author glass
 */
@ImplementedBy(CouplerDefault.class)
public interface Coupler {

	/**
	 * Returns a list of {@link Individual}-groups (couples) that are designated
	 * to create offspring.
	 * 
	 * @param size
	 *            the number of couples to create
	 * @param parents
	 *            the parents
	 * @return the selected couples
	 */
	public Collection<Pair<Individual>> getCouples(int size, List<Individual> parents);

}
