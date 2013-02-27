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