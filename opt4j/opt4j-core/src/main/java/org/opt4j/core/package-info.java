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
 * Provides the classes that join the problem and optimizer.
 * </p>
 * <p>
 * This package contains the {@link org.opt4j.core.Individual} and the related classes and interfaces. The
 * {@link org.opt4j.core.Individual} is a single solution of the optimization problem. It consists of the
 * {@link org.opt4j.core.Genotype}, the {@link org.opt4j.core.Phenotype}, and the {@link org.opt4j.core.Objectives},
 * which are the representation of the {@link org.opt4j.core.Individual} in the search space, the solution space, and
 * the objective space, respectively.
 * </p>
 * <dl>
 * <dt>{@link org.opt4j.core.Genotype}</dt>
 * <dd>The {@link org.opt4j.core.Genotype} is the genetic representation of an {@link org.opt4j.core.Individual}. The
 * package {@link org.opt4j.genotype} contains predefined {@link org.opt4j.core.Genotype} classes like for binary
 * strings or {@link java.lang.Double} vectors that allow a modular assembly. For each optimization problem, the user
 * needs to choose the genetic representation which fits the search space best.</dd>
 * 
 * <dt>{@link org.opt4j.core.Phenotype}</dt>
 * <dd>The {@link org.opt4j.core.Phenotype} is the decoded representation of an {@link org.opt4j.core.Individual}.</dd>
 * 
 * <dt>{@link org.opt4j.core.Phenotype}</dt>
 * <dd>The {@link org.opt4j.core.Objectives} contain the results of the evaluated {@link org.opt4j.core.Objective}s for
 * a {@link org.opt4j.core.Phenotype}. The {@link org.opt4j.core.Objectives} represent the fitness of the
 * {@link org.opt4j.core.Individual}. To support multi-objective optimization, an {@link org.opt4j.core.Objective}
 * represents one dimension in the objective space. After the evaluation of the {@link org.opt4j.core.Phenotype}, the
 * {@link org.opt4j.core.Objectives} hold a {@link org.opt4j.core.Value} for each {@link org.opt4j.core.Objective}.</dd>
 * </dl>
 * <p>
 * {@link org.opt4j.core.Individual}s can be grouped in an {@link org.opt4j.core.IndividualSet} which informs
 * {@link org.opt4j.core.IndividualSetListener}s when changed. New {@link org.opt4j.core.Individual}s should be created
 * using the {@link org.opt4j.core.IndividualFactory}.
 * </p>
 * 
 */
package org.opt4j.core;