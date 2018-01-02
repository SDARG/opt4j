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
 * Provides the classes for the optimizer.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.Optimizer} represents an optimization
 * algorithm. Usually, all iteration-based
 * {@link org.opt4j.core.optimizer.Optimizer}s are derived from the
 * {@link org.opt4j.core.optimizer.IterativeOptimizer}.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.IterativeOptimizer} initializes the
 * {@link org.opt4j.core.optimizer.Population} by using the
 * {@link org.opt4j.core.IndividualFactory} to get new
 * {@link org.opt4j.core.Individual}s. In each iteration, the
 * {@link org.opt4j.core.optimizer.IterativeOptimizer} uses
 * {@link org.opt4j.core.optimizer.Operator}s to vary the
 * {@link org.opt4j.core.Genotype}s. The package {@code org.opt4j.operators}
 * contains predefined {@link org.opt4j.core.optimizer.Operator} classes which
 * are applicable for different {@link org.opt4j.core.Genotype}s.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.IterativeOptimizer} is managed by the
 * {@link org.opt4j.core.optimizer.OptimizationMediator} which itself is an
 * {@link org.opt4j.core.optimizer.AbstractOptimizer}. The
 * {@link org.opt4j.core.optimizer.AbstractOptimizer} implements some essential
 * methods for coupling optimizers with the framework. It interacts with the
 * {@link org.opt4j.core.optimizer.OptimizerIterationListener}s which are
 * informed whenever an iteration is complete, the
 * {@link org.opt4j.core.optimizer.OptimizerStateListener}s which are informed
 * whenever the optimization starts or stops, and the
 * {@link org.opt4j.core.optimizer.Control}: The
 * {@link org.opt4j.core.optimizer.Control} allows to pause and resume, stop, or
 * terminate the optimization process. While the
 * {@link org.opt4j.core.optimizer.Population} contains the
 * {@link org.opt4j.core.Individual}s of the current
 * {@link org.opt4j.core.optimizer.Iteration}, the
 * {@link org.opt4j.core.optimizer.Archive} contains the non-dominated
 * {@link org.opt4j.core.Individual}s that were obtained throughout the whole
 * optimization process. The {@link org.opt4j.core.optimizer.AbstractOptimizer}
 * updates the {@link org.opt4j.core.optimizer.Archive} with the current
 * {@link org.opt4j.core.optimizer.Population} and triggers the
 * {@link org.opt4j.core.optimizer.Iteration} count. Instead of using the
 * {@link org.opt4j.core.problem.Decoder} and
 * {@link org.opt4j.core.problem.Evaluator} directly, the
 * {@link org.opt4j.core.optimizer.IndividualCompleter} is used by
 * {@link org.opt4j.core.optimizer.Optimizer}s to determine the phenotype and
 * the {@link org.opt4j.core.Objectives} of new
 * {@link org.opt4j.core.Individual}s in the
 * {@link org.opt4j.core.optimizer.Population}.
 * </p>
 */
package org.opt4j.core.optimizer;