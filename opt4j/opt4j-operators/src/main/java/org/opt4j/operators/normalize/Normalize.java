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

package org.opt4j.operators.normalize;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.Bounds;
import org.opt4j.core.optimizer.Operator;

/**
 * The {@link Normalize} is an operator that achieves that the {@link Bounds} of
 * a {@link Genotype} are fulfilled.
 * 
 * @author lukasiewycz
 * 
 * @param <G>
 *            the type of genotype
 */
public interface Normalize<G extends Genotype> extends Operator<G> {

	/**
	 * Normalizes the {@link Genotype} .
	 * 
	 * @param genotype
	 *            the genotype to be normalized
	 */
	public void normalize(G genotype);

}
