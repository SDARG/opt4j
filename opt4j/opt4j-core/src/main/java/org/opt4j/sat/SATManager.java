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

package org.opt4j.sat;

import java.util.List;
import java.util.Map;

import org.opt4j.core.Genotype;

import com.google.inject.ImplementedBy;

/**
 * Classes with this interface manage the creation of {@link Genotype}s and the
 * decoding to a {@link Model}.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(MixedSATManager.class)
public interface SATManager {

	/**
	 * Returns the {@link Solver}.
	 * 
	 * @return the solver
	 */
	public Solver getSolver();

	/**
	 * Creates a new {@link Genotype} from the variables, priorities, and
	 * phases.
	 * 
	 * @param variables
	 *            the variables
	 * @param lowerBounds
	 *            the lower bounds for the priority for the variables
	 * @param upperBounds
	 *            the upper bounds for the priority for the variables
	 * @param priorities
	 *            the priorities
	 * @param phases
	 *            the phases
	 * @return the new genotype
	 */
	public Genotype createSATGenotype(List<Object> variables, Map<Object, Double> lowerBounds,
			Map<Object, Double> upperBounds, Map<Object, Double> priorities, Map<Object, Boolean> phases);

	/**
	 * Decodes the {@link Genotype} into a {@link Model}.
	 * 
	 * @param variables
	 *            the variables of the problem
	 * @param genotype
	 *            the genotype
	 * @return the model
	 */
	public Model decodeSATGenotype(List<Object> variables, Genotype genotype);

}
