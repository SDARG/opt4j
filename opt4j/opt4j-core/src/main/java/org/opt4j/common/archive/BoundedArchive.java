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

package org.opt4j.common.archive;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.opt4j.core.Individual;

import com.google.inject.Inject;

/**
 * An {@link org.opt4j.core.optimizer.Archive} with bounded size.
 * 
 * @author helwig, lukasiewycz
 */
public abstract class BoundedArchive extends AbstractArchive {

	/** The capacity of this {@link org.opt4j.core.optimizer.Archive} */
	protected int capacity;

	/**
	 * Constructs a bounded archive with the specified capacity.
	 * 
	 * @param capacity
	 *            Capacity of this archive
	 */
	@Inject
	public BoundedArchive(int capacity) {
		super();
		this.capacity = capacity;

		if (capacity < 0) {
			throw new IllegalArgumentException("Invalid capacity: " + capacity);
		}
	}

	/**
	 * Sets the capacity of this {@link BoundedArchive}.
	 * 
	 * @see #getCapacity
	 * @param capacity
	 *            new capacity of this bounded archive
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Invalid capacity: " + capacity);
		}
		this.capacity = capacity;
	}

	/**
	 * Returns the capacity of this {@link BoundedArchive}.
	 * 
	 * @see #setCapacity
	 * @return capacity of this bounded archive
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             if the capacity is reached.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualCollection#add(org.opt4j.core.Individual)
	 */
	@Override
	public boolean addCheckedIndividual(Individual individual) {
		if (!contains(individual) && individuals.size() >= capacity) {
			throw new IndexOutOfBoundsException();
		}
		return super.addCheckedIndividual(individual);
	}

	/**
	 * @throws IndexOutOfBoundsException
	 *             if the capacity is reached.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualCollection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addCheckedIndividuals(Collection<? extends Individual> c) {
		Set<Individual> set = new LinkedHashSet<Individual>(c);
		set.removeAll(this);
		if (individuals.size() + set.size() > capacity) {
			throw new IndexOutOfBoundsException();
		}

		return super.addCheckedIndividuals(c);
	}
}
