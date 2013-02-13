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
