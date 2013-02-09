/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.core.problem;

import org.opt4j.core.Objectives;
import org.opt4j.core.Phenotype;

import com.google.inject.ImplementedBy;

/**
 * The {@link Evaluator} evaluates {@link Phenotype}s to {@link Objectives}.
 * 
 * The {@link Evaluator} must always add the same {@link org.opt4j.core.Objective}s to the {@link Objectives}.
 * 
 * @author glass, lukasiewycz
 * 
 * @see Phenotype
 * @see Objectives
 * 
 * @param <P>
 *            the type of the phenotype that is evaluated
 */
@ImplementedBy(MultiEvaluator.class)
public interface Evaluator<P extends Phenotype> {

	/**
	 * Evaluates a {@link Phenotype} and returns the results in the {@link Objectives}.
	 * 
	 * @param phenotype
	 *            the phenotype to be evaluated
	 * 
	 * @return the results in the objectives
	 */
	public Objectives evaluate(P phenotype);

}
