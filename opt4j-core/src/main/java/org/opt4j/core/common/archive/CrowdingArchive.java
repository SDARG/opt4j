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
import java.util.List;

import org.opt4j.core.Individual;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link CrowdingArchive} is based on the {@link Crowding} distance.
 * 
 * @author lukasiewycz
 * @see Crowding
 * 
 */
public class CrowdingArchive extends BoundedArchive {

	/**
	 * Constructs a {@link CrowdingArchive}.
	 * 
	 * @param capacity
	 *            capacity of this archive (using namespace
	 *            {@link BoundedArchive})
	 */
	@Inject
	public CrowdingArchive(@Constant(value = "capacity", namespace = BoundedArchive.class) int capacity) {
		super(capacity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.common.archive.AbstractArchive#updateWithNondominated(java.
	 * util.Collection)
	 */
	@Override
	protected boolean updateWithNondominated(Collection<Individual> candidates) {
		boolean changed = false;
		if (this.size() + candidates.size() <= capacity) {
			changed = addCheckedIndividuals(candidates);
		} else {
			candidates.addAll(this);
			Crowding crowding = new Crowding();
			List<Individual> list = crowding.order(crowding.getDensityValues(candidates));
			List<Individual> worst = list.subList(capacity, list.size());
			candidates.removeAll(worst);

			this.retainAll(candidates);
			for (Individual i : candidates) {
				if (!this.contains(i)) {
					changed |= addCheckedIndividual(i);
				}
			}
		}
		return changed;
	}

}
