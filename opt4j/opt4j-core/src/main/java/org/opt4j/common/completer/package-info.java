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

/**
 * <p>
 * Provides the classes for the
 * {@link org.opt4j.core.optimizer.IndividualCompleter}s.
 * </p>
 * <p>
 * The task of an {@link org.opt4j.core.optimizer.IndividualCompleter} is to
 * decode and evaluate an {@link org.opt4j.core.Individual}. Finally, the
 * {@link org.opt4j.core.Individual} is in the state {@code evaluated}. In
 * real-world and industrial problems the decoding and evaluation is the most
 * time consuming task. Internally, the
 * {@link org.opt4j.core.optimizer.IndividualCompleter} uses the
 * {@link org.opt4j.core.problem.Decoder} and
 * {@link org.opt4j.core.problem.Evaluator}. In fact, an
 * {@link org.opt4j.core.optimizer.Optimizer} shall use the
 * {@link org.opt4j.core.optimizer.IndividualCompleter} instead of the
 * {@link org.opt4j.core.problem.Decoder} and
 * {@link org.opt4j.core.problem.Evaluator}.
 * </p>
 * <p>
 * There are two predefined completers: The
 * {@link org.opt4j.common.completer.SequentialIndividualCompleter} completes
 * each individual sequentially. The
 * {@link org.opt4j.common.completer.ParallelIndividualCompleter} completes
 * {@code n} individuals in parallel exploiting multicore architectures
 * efficiently.
 * </p>
 */
package org.opt4j.common.completer;

