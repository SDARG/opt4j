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

package org.opt4j.core.optimizer;

import org.opt4j.common.completer.SequentialIndividualCompleter;
import org.opt4j.core.Individual;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.ImplementedBy;

/**
 * An {@link IndividualCompleter} completes the evaluation process of
 * {@link Individual}s. In particular, the individuals are decoded using a
 * {@link Decoder} and evaluated using an {@link Evaluator}.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(SequentialIndividualCompleter.class)
public interface IndividualCompleter {

	/**
	 * Decodes and evaluates all {@link Individual}s in the {@link Iterable} if
	 * they are not already evaluated.
	 * 
	 * @param iterable
	 *            the set of individuals to be completed
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public void complete(Iterable<? extends Individual> iterable) throws TerminationException;

	/**
	 * Decodes and evaluates all given {@link Individual}s if they are not
	 * already evaluated.
	 * 
	 * @param individuals
	 *            the individuals to be completed
	 * @throws TerminationException
	 *             if the optimization is terminated
	 */
	public void complete(Individual... individuals) throws TerminationException;
}
