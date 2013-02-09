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
import java.util.Map;

import org.opt4j.common.archive.Crowding;
import org.opt4j.core.Individual;

import com.google.inject.ImplementedBy;

/**
 * The {@code FrontDensityIndicator} is an interface for the determination of
 * density values of a front of {@link Individual}s. It is required that the
 * front consists of non-dominated {@code Individuals} only. This density
 * indicator is used for instance in the {@link Nsga2}.
 * 
 * @see Nsga2
 * @see Crowding
 * @see Hypervolume
 * @author lukasiewycz
 * 
 */
@ImplementedBy(Crowding.class)
public interface FrontDensityIndicator {

	/**
	 * Returns the density values for a collection on {@code Individuals}.
	 * 
	 * @param individuals
	 *            the individuals
	 * @return a map of each individual to its density value
	 */
	public Map<Individual, Double> getDensityValues(Collection<Individual> individuals);	

}
