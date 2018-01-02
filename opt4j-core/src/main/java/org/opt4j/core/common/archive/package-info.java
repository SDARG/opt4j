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
 * Provides different implementations for the
 * {@link org.opt4j.core.optimizer.Archive}.
 * </p>
 * <h3>Abstract classes</h3>
 * <p>
 * The {@link org.opt4j.core.common.archive.AbstractArchive} provides common methods
 * for archives to assure that no Pareto-dominated individual remains in the
 * archive. It should be used for all implementations of
 * {@link org.opt4j.core.optimizer.Archive}.
 * </p>
 * <p>
 * The {@link org.opt4j.core.common.archive.BoundedArchive} is an abstract class
 * which provides common methods for bound archives, i.e. archives that have a
 * specified maximum size. Such archives need to decide which non-dominated
 * individuals should be dropped if the maximum size is reached.
 * </p>
 * <h3>Implementations</h3>
 * <p>
 * The {@link org.opt4j.core.common.archive.UnboundedArchive} stores each found
 * {@link org.opt4j.core.Individual} as long as it is not dominated. Especially
 * for high dimensional and continuous problems, the number of non-dominated
 * individuals can get very high.
 * </p>
 * <p>
 * The {@link org.opt4j.core.common.archive.CrowdingArchive} uses the crowding
 * distance of NSGA2 to decide which {@link org.opt4j.core.Individual}s to drop
 * if the maximum size is reached.
 * </p>
 * <p>
 * The {@link org.opt4j.core.common.archive.AdaptiveGridArchive} uses an adaptive
 * grid to decide which {@link org.opt4j.core.Individual}s to drop.
 * </p>
 * <p>
 * The {@link org.opt4j.core.common.archive.PopulationArchive} just mirrors the
 * non-dominated {@link org.opt4j.core.Individual}s of the current population.
 * Compared to the bounded archives above, it should only be used for the
 * development or comparison of optimization algorithms.
 * </p>
 * <p>
 * The {@link org.opt4j.core.common.archive.DefaultArchive} defines the
 * implementation to use if no archive is specified using the
 * {@link org.opt4j.core.common.archive.ArchiveModule}. The default is the
 * {@link org.opt4j.core.common.archive.CrowdingArchive} with a maximum size of 100
 * {@link org.opt4j.core.Individual}s.
 * <h3>Modules</h3>
 * <p>
 * The {@link org.opt4j.core.common.archive.ArchiveModule} allows to select the
 * implementation for the {@link org.opt4j.core.optimizer.Archive}.
 */
package org.opt4j.core.common.archive;