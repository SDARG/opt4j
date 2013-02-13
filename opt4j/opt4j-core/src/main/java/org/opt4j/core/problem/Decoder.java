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

package org.opt4j.core.problem;

import org.opt4j.core.Genotype;

/**
 * The {@link Decoder} decodes {@link Genotype}s into phenotype {@link Object}s.
 * 
 * @author glass, lukasiewycz
 * @see Genotype
 * @param <G>
 *            the type of genotype that is decoded
 * @param <P>
 *            the type of the resulting phenotype}
 */
public interface Decoder<G extends Genotype, P extends Object> {

	/**
	 * Decodes a given {@link Genotype} to the corresponding phenotype.
	 * 
	 * @param genotype
	 *            the genotype to decode
	 * @return the decoded phenotype
	 */
	public P decode(G genotype);

}
