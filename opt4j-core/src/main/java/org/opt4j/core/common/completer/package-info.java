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
 * {@link org.opt4j.core.common.completer.SequentialIndividualCompleter} completes
 * each individual sequentially. The
 * {@link org.opt4j.core.common.completer.ParallelIndividualCompleter} completes
 * {@code n} individuals in parallel exploiting multicore architectures
 * efficiently.
 * </p>
 */
package org.opt4j.core.common.completer;

