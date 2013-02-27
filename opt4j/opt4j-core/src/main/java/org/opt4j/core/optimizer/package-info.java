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