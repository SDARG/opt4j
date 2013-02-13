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

import org.opt4j.core.genotype.DoubleGenotype;

import com.google.inject.ImplementedBy;

/**
 * <p>
 * The {@link NormalizeDouble} is the {@link Normalize} operator for the
 * {@link DoubleGenotype}.
 * </p>
 * 
 * <p>
 * The default implementation is the {@link NormalizeDoubleBorder}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(NormalizeDoubleBorder.class)
public interface NormalizeDouble extends Normalize<DoubleGenotype> {

}
