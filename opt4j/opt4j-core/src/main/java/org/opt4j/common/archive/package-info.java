/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

/**
 * <p>
 * Provides different implementations for the {@link org.opt4j.core.optimizer.Archive}. 
 * </p>
 * <h3>Abstract classes</h3>
 * <p>The {@link org.opt4j.common.archive.AbstractArchive} provides common methods for archives to assure that no Pareto-dominated individual remains in the archive.
 * It should be used for all implementations of {@link org.opt4j.core.optimizer.Archive}.</p>
 * <p>The {@link org.opt4j.common.archive.BoundedArchive} is an abstract class which provides common methods for bound archives, i.e. archives that have a specified maximum size.
 * Such archives need to decide which non-dominated individuals should be dropped if the maximum size is reached.
 * </p>
 * <h3>Implementations</h3>
 * <p>The {@link org.opt4j.common.archive.UnboundedArchive} stores each found {@link org.opt4j.core.Individual} as long as it is not dominated.
 * Especially for high dimensional and continuous problems, the number of non-dominated individuals can get very high.</p>
 * <p>The {@link org.opt4j.common.archive.CrowdingArchive} uses the crowding distance of NSGA2 to decide which {@link org.opt4j.core.Individual}s to drop if the maximum size is reached.</p>
 * <p>The {@link org.opt4j.common.archive.AdaptiveGridArchive} uses an adaptive grid to decide which {@link org.opt4j.core.Individual}s to drop.</p>
 * <p>The {@link org.opt4j.common.archive.PopulationArchive} just mirrors the non-dominated {@link org.opt4j.core.Individual}s of the current population.
 * Compared to the bounded archives above, it should only be used for the development or comparison of optimization algorithms.</p>
 * <p>The {@link org.opt4j.common.archive.DefaultArchive} defines the implementation to use if no archive is specified using the {@link org.opt4j.common.archive.ArchiveModule}.
 * The default is the {@link org.opt4j.common.archive.CrowdingArchive} with a maximum size of 100 {@link org.opt4j.core.Individual}s.
 * <h3>Modules</h3>
 * <p>The {@link org.opt4j.common.archive.ArchiveModule} allows to select the implementation for the {@link org.opt4j.core.optimizer.Archive}.
 */
package org.opt4j.common.archive;