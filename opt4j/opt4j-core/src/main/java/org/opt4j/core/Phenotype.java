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

package org.opt4j.core;

/**
 * <p>
 * The {@link Phenotype} is a marker interface. A {@link Phenotype} represents a
 * solution of the optimization problem that can be evaluated by the
 * {@link org.opt4j.core.problem.Evaluator}.
 * </p>
 * <p>
 * If the class for the solution is already defined and cannot be extended by a
 * marker interface, use the {@link org.opt4j.core.problem.PhenotypeWrapper} to
 * wrap the object.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public interface Phenotype {

}
