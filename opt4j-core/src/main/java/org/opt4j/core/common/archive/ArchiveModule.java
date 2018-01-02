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


package org.opt4j.core.common.archive;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.start.Constant;
import org.opt4j.core.start.Opt4JModule;

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
