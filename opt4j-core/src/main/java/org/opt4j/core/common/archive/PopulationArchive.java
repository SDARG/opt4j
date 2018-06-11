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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Population;

/**
 * The {@link PopulationArchive} keeps the non-dominated {@link Individual}s
 * from the {@link Population}. It is assumed that the {@link #update(Set)}
 * method is called with the current {@link Population} in each iteration.
 * 
 * @author lukasiewycz
 * 
 */
public class PopulationArchive extends AbstractArchive {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.common.archive.AbstractArchive#update(java.util.Set)
	 */
	@Override
	public boolean update(Set<? extends Individual> individuals) {

		List<Individual> candidates = new ArrayList<Individual>(individuals);

		Objectives o1;
		Objectives o2;
		for (int i = 0; i < candidates.size() - 1; i++) {
			o1 = candidates.get(i).getObjectives();
			for (int j = i + 1; j < candidates.size(); j++) {
				o2 = candidates.get(j).getObjectives();
				if (o2.weaklyDominates(o1)) {
					candidates.remove(i);
					i--;
					break;
				} else if (o1.weaklyDominates(o2)) {
					candidates.remove(j);
					j--;
				}
			}
		}

		return updateWithNondominated(candidates);
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
		List<Individual> removes = new ArrayList<Individual>();
		for (Individual i : this) {
			if (!candidates.contains(i)) {
				removes.add(i);
			}
		}
		removeAll(removes);

		boolean changed = false;
		for (Individual i : candidates) {
			if (!contains(i)) {
				changed |= addCheckedIndividual(i);
			}
		}
		return changed;
	}

}
