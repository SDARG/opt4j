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
 * Provides the classes that join the problem and optimizer.
 * </p>
 * <p>
 * This package contains the {@link org.opt4j.core.Individual} and the related
 * classes and interfaces. The {@link org.opt4j.core.Individual} is a single
 * solution of the optimization problem. It consists of the
 * {@link org.opt4j.core.Genotype}, the phenotype, and the
 * {@link org.opt4j.core.Objectives}, which are the representation of the
 * {@link org.opt4j.core.Individual} in the search space, the solution space,
 * and the objective space, respectively.
 * </p>
 * <dl>
 * <dt>{@link org.opt4j.core.Genotype}</dt>
 * <dd>The {@link org.opt4j.core.Genotype} is the genetic representation of an
 * {@link org.opt4j.core.Individual}. The package {@code org.opt4j.genotype}
 * contains predefined {@link org.opt4j.core.Genotype} classes like for binary
 * strings or {@link java.lang.Double} vectors that allow a modular assembly.
 * For each optimization problem, the user needs to choose the genetic
 * representation which fits the search space best.</dd>
 * 
 * <dt>phenotype</dt>
 * <dd>The phenotype {@link java.lang.Object} is the decoded representation of
 * an {@link org.opt4j.core.Individual}.</dd>
 * 
 * <dt>{@link org.opt4j.core.Objectives}</dt>
 * <dd>The {@link org.opt4j.core.Objectives} contain the results of the
 * evaluated {@link org.opt4j.core.Objective}s for a phenotype. The
 * {@link org.opt4j.core.Objectives} represent the fitness of the
 * {@link org.opt4j.core.Individual}. To support multi-objective optimization,
 * an {@link org.opt4j.core.Objective} represents one dimension in the objective
 * space. After the evaluation of the phenotype, the
 * {@link org.opt4j.core.Objectives} hold a {@link org.opt4j.core.Value} for
 * each {@link org.opt4j.core.Objective}.</dd>
 * </dl>
 * <p>
 * {@link org.opt4j.core.Individual}s can be grouped in an
 * {@link org.opt4j.core.IndividualSet} which informs
 * {@link org.opt4j.core.IndividualSetListener}s when changed. New
 * {@link org.opt4j.core.Individual}s should be created using the
 * {@link org.opt4j.core.IndividualFactory}.
 * </p>
 * 
 */
package org.opt4j.core;