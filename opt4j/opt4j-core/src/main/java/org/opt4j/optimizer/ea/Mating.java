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

import org.opt4j.core.Individual;

import com.google.inject.ImplementedBy;

/**
 * The {@link Mating} interface is used to create offspring from a given set of
 * parents.
 * 
 * @author glass, lukasiewycz
 * 
 */
@ImplementedBy(MatingCrossoverMutate.class)
public interface Mating {

	/**
	 * Creates offspring from a given set of parents.
	 * 
	 * @param size
	 *            the number of individuals to create
	 * @param parents
	 *            the parents
	 * @return the offspring
	 */
	public Collection<Individual> getOffspring(int size, Individual... parents);

	/**
	 * Creates offspring from a given set of parents.
	 * 
	 * @param size
	 *            the number of individuals to create
	 * @param parents
	 *            the parents
	 * @return the offspring
	 */
	public Collection<Individual> getOffspring(int size, Collection<Individual> parents);

}
