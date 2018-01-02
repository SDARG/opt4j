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
