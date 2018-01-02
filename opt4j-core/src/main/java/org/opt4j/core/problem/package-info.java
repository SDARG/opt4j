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
 * Provides the classes for the optimization problem. To define a new
 * optimization problem, the {@link org.opt4j.core.problem.Creator}, the
 * {@link org.opt4j.core.problem.Decoder}, and the
 * {@link org.opt4j.core.problem.Evaluator} must be implemented.
 * </p>
 * <dl>
 * <dt>{@link org.opt4j.core.problem.Creator}</dt>
 * <dd>
 * The task of the {@link org.opt4j.core.problem.Creator} is the creation of
 * random {@link org.opt4j.core.Genotype}s. It is typically used at the
 * beginning of an optimization by the
 * {@link org.opt4j.core.optimizer.Optimizer} to create the initial
 * {@link org.opt4j.core.optimizer.Population}. The
 * {@link org.opt4j.core.Genotype}s, the {@link org.opt4j.core.problem.Creator}
 * creates, define the search space for the
 * {@link org.opt4j.core.optimizer.Optimizer}. The package
 * {@code org.opt4j.core.genotype} contains predefined
 * {@link org.opt4j.core.Genotype} classes that allow a modular assembly. Thus,
 * usually you do not have to write your custom {@link org.opt4j.core.Genotype}.
 * </dd>
 * 
 * <dt>{@link org.opt4j.core.problem.Decoder}</dt>
 * <dd>The task of the {@link org.opt4j.core.problem.Decoder} is the decoding
 * from a given {@link org.opt4j.core.Genotype} to a corresponding phenotype.
 * The phenotype is the object you expect as a solution of the optimization.</dd>
 * 
 * <dt>{@link org.opt4j.core.problem.Evaluator}</dt>
 * <dd>The task of the {@link org.opt4j.core.problem.Evaluator} is the
 * evaluation of phenotypes to the {@link org.opt4j.core.Objectives}. These are
 * typically used by the {@link org.opt4j.core.optimizer.Optimizer} to determine
 * the fitness of the {@link org.opt4j.core.Individual} or by the
 * {@link org.opt4j.core.optimizer.Archive} to determine if the
 * {@link org.opt4j.core.Individual} Pareto-dominates another one.</dd>
 * </dl>
 * <p>
 * Inherit from the {@link org.opt4j.core.problem.ProblemModule} to bind a
 * {@link org.opt4j.core.problem.Creator},
 * {@link org.opt4j.core.problem.Decoder}, and
 * {@link org.opt4j.core.problem.Evaluator} belonging together.
 * </p>
 * <p>
 * For multi-objective optimization, one
 * {@link org.opt4j.core.problem.Evaluator} can set several
 * {@link org.opt4j.core.Objective}s or - to allow a separation of concerns -
 * also several {@link org.opt4j.core.problem.Evaluator}s can be added using the
 * {@link org.opt4j.core.problem.ProblemModule}. They are managed by the
 * {@link org.opt4j.core.problem.MultiEvaluator}. The order in which the
 * {@link org.opt4j.core.problem.MultiEvaluator} calls the
 * {@link org.opt4j.core.problem.Evaluator}s can be influenced by the
 * {@link org.opt4j.core.problem.Priority}.
 * </p>
 */
package org.opt4j.core.problem;