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

package org.opt4j.operator.mutate;

import org.opt4j.genotype.IntegerGenotype;

import com.google.inject.ImplementedBy;

/**
 * The {@link MutateInteger} is the interface for {link Mutate} operators for
 * {@link IntegerGenotype} objects.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(MutateIntegerRandom.class)
public interface MutateInteger extends Mutate<IntegerGenotype> {

}
