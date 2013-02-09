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

package org.opt4j.common.archive;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Required;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.start.Constant;
import org.opt4j.start.Opt4JModule;

/**
 * The {@link ArchiveModule} determines an implementation for the
 * {@link Archive} interface.
 * 
 * @see UnboundedArchive
 * @see PopulationArchive
 * @see CrowdingArchive
 * @see AdaptiveGridArchive
 * @see Archive
 * @author helwig, lukasiewycz
 * 
 */
@Icon(Icons.PUZZLE_BLUE)
@Info("The archive of non-dominated solutions found during the optimization process.")
public class ArchiveModule extends Opt4JModule {

	@Info("Archive type")
	protected Type type = Type.CROWDING;

	@Info("Maximal archive capacity if a bounded archive is selected")
	@Required(property = "type", elements = { "ADAPTIVE_GRID", "CROWDING" })
	@Constant(value = "capacity", namespace = BoundedArchive.class)
	protected int capacity = 100;

	@Info("Divisions for the adaptive grid archive. A convergence is guaranteed if 'capacity>1+div^m+(div-1)^m+2*m' with m being the number of objectives holds")
	@Required(property = "type", elements = { "ADAPTIVE_GRID" })
	@Constant(value = "div", namespace = AdaptiveGridArchive.class)
	protected int divisions = 7;

	/** Archive type. */
	public enum Type {
		/**
		 * Archive of unlimited size.
		 * 
		 * @see UnboundedArchive
		 */
		@Info("Archive of unlimited size")
		UNBOUNDED,

		/**
		 * Archive that keeps the non-dominated individual of the population.
		 * 
		 * @see PopulationArchive
		 */
		@Info("Archive that keeps the non-dominated individual of the population")
		POPULATION,

		/**
		 * Adaptive grid archive.
		 * 
		 * @see AdaptiveGridArchive
		 */
		@Info("Adaptive grid archive")
		ADAPTIVE_GRID,

		/**
		 * Bounded archive based on the NSGA2 crowding distance.
		 * 
		 * @see CrowdingArchive
		 */
		@Info("Bounded archive based on the crowding distance (NSGA2)")
		CROWDING;
	}

	/**
	 * Returns the number of divisions for the {@link AdaptiveGridArchive}.
	 * 
	 * @see #setDivisions
	 * @return the number of divisions
	 */
	public int getDivisions() {
		return divisions;
	}

	/**
	 * Set the number of divisions for the {@link AdaptiveGridArchive}.
	 * 
	 * @see #getDivisions
	 * @param divisions
	 *            the number of divisions
	 */
	public void setDivisions(int divisions) {
		this.divisions = divisions;
	}

	/**
	 * Sets the archive type to the specified value.
	 * 
	 * @see #getType
	 * @param type
	 *            the new archive type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the archive type.
	 * 
	 * @see #setType
	 * @return the archive type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the archive's capacity to the specified value.
	 * 
	 * @see #getCapacity
	 * @param capacity
	 *            the new capacity (using namespace {@link BoundedArchive})
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Returns the archive's capacity.
	 * 
	 * @see #setCapacity
	 * @return the archive's capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		Class<? extends Archive> archiveClass = null;

		switch (type) {
		case ADAPTIVE_GRID:
			archiveClass = AdaptiveGridArchive.class;
			break;
		case POPULATION:
			archiveClass = PopulationArchive.class;
			break;
		case CROWDING:
			archiveClass = CrowdingArchive.class;
			break;
		default: // UNBOUNDED
			archiveClass = UnboundedArchive.class;
			break;
		}

		bind(Archive.class).to(archiveClass).in(SINGLETON);
	}
}
