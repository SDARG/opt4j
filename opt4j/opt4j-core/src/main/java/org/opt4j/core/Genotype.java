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
 * The {@link Genotype} represents a marker interface. A {@link Genotype}
 * represents the genetic encoding of a solution of the optimization problem.
 * Thus, a {@link Genotype} can be decoded to a {@link Phenotype} with an
 * appropriate {@link org.opt4j.core.problem.Decoder}.
 * 
 * @see org.opt4j.genotype
 * @author glass, lukasiewycz
 * 
 */
public interface Genotype {

	/**
	 * The number of atomic elements of the {@link Genotype}.
	 * 
	 * @return number of atomic elements of the genotype
	 */
	public int size();

	/**
	 * Constructs a new (empty) instance of this {@link Genotype}.
	 * 
	 * @param <G>
	 *            the type of genotype for an implicit cast
	 * @return new instance of the genotype
	 */
	public <G extends Genotype> G newInstance();

}
