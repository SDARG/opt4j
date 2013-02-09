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

/**
 * <p>
 * Provides the classes for the optimizer.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.Optimizer} represents an optimization algorithm. Usually, all iteration-based
 * {@link org.opt4j.core.optimizer.Optimizer}s are derived from the {@link org.opt4j.core.optimizer.IterativeOptimizer}.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.Optimizer} manipulates the {@link org.opt4j.core.optimizer.Population} and
 * {@link org.opt4j.core.optimizer.Archive}. The {@link org.opt4j.core.optimizer.Population} contains the
 * {@link org.opt4j.core.Individual}s of the current {@link org.opt4j.core.optimizer.Iteration}, while the
 * {@link org.opt4j.core.optimizer.Archive} contains the non-dominated {@link org.opt4j.core.Individual}s that were
 * obtained throughout the optimization process.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.Optimizer} might use any {@link org.opt4j.core.optimizer.Operator} to vary the
 * {@link org.opt4j.core.Genotype} and use the {@link org.opt4j.core.IndividualFactory} to get new
 * {@link org.opt4j.core.Individual}s. Note that instead using the {@link org.opt4j.core.problem.Decoder} and
 * {@link org.opt4j.core.problem.Evaluator} directly, the {@link org.opt4j.core.optimizer.Optimizer} uses an
 * {@link org.opt4j.core.optimizer.IndividualCompleter} to determine the {@link org.opt4j.core.Phenotype} and
 * {@link org.opt4j.core.Objectives} of a set of {@link org.opt4j.core.Individual}s.
 * </p>
 * <p>
 * The {@link org.opt4j.core.optimizer.AbstractOptimizer} implements some essential methods for coupling optimizers with
 * the framework. It interacts with the {@link org.opt4j.core.optimizer.OptimizerIterationListener}s, the
 * {@link org.opt4j.core.optimizer.OptimizerStateListener}s, and the {@link org.opt4j.core.optimizer.Control}: The
 * {@link org.opt4j.core.optimizer.Control} allows to pause and resume, stop, or terminate the optimization process.
 * Moreover, it updates the {@link org.opt4j.core.optimizer.Archive} with the current
 * {@link org.opt4j.core.optimizer.Population}. It triggers the {@link org.opt4j.core.optimizer.Iteration} count as well
 * as with the {@link org.opt4j.core.optimizer.IndividualCompleter}. The
 * {@link org.opt4j.core.optimizer.OptimizationMediator} is an {@link org.opt4j.core.optimizer.AbstractOptimizer} which
 * handles the {@link org.opt4j.core.optimizer.IterativeOptimizer}.
 * </p>
 */
package org.opt4j.core.optimizer;