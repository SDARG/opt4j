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

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Archive;

import com.google.inject.Singleton;

/**
 * The {@code UnboundedArchive} is an {@link Archive} with unbounded size.
 * 
 * @author helwig, lukasiewycz
 */
@Singleton
public class UnboundedArchive extends AbstractArchive {

	/**
	 * Constructs a new archive of unbounded size.
	 */
	public UnboundedArchive() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.common.archive.AbstractArchive#addNondominated(java.util.Collection
	 * )
	 */
	@Override
	protected boolean updateWithNondominated(Collection<Individual> candidates) {
		boolean changed = false;
		for (Individual i : candidates) {
			if (!contains(i)) {
				changed |= addCheckedIndividual(i);
			}
		}
		return changed;
	}
}
