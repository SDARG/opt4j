/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/


package org.opt4j.core.optimizer;

import org.opt4j.core.Individual;
import org.opt4j.core.common.completer.SequentialIndividualCompleter;
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
