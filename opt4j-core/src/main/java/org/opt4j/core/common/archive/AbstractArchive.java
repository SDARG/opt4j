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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Archive;

/**
 * This {@link AbstractArchive} provides some common methods for {@link Archive}
 * s. If one or more new {@link Individual}s are added to this {@link Archive},
 * it is ensured that all {@link Individual}s in this {@link Archive} are not
 * Pareto-dominated. Actual implementations of this class may still refuse or
 * drop some {@link Individual}s. An {@link Archive} can be a
 * {@link BoundedArchive} if it has a bounded size or an
 * {@link UnboundedArchive}, otherwise.
 * 
 * @author helwig, glass, lukasiewycz
 * 
 */
public abstract class AbstractArchive extends Archive {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Archive#update(java.util.Set)
	 */
	@Override
	public boolean update(Set<? extends Individual> individuals) {
		List<Individual> candidates = new ArrayList<Individual>(individuals);
		Objectives o1, o2;

		/*
		 * Remove all individuals that are already in the archive and those that
		 * are dominated among the candidates
		 */
		candidates.removeAll(this);
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

		/*
		 * Remove those individuals from the archive which are dominated by the
		 * candidates. Remove those individuals from the candidates which are
		 * dominated by the archive.
		 */
		Iterator<Individual> i1, i2;
		for (i1 = candidates.iterator(); i1.hasNext();) {
			o1 = i1.next().getObjectives();
			for (i2 = this.iterator(); i2.hasNext();) {
				o2 = i2.next().getObjectives();
				if (o1.weaklyDominates(o2)) {
					i2.remove();
				} else if (o2.dominates(o1)) {
					i1.remove();
					break;
				}
			}
		}

		return updateWithNondominated(candidates);
	}

	/**
	 * Adds new {@code candidates} which are already checked to be not
	 * Pareto-dominated by any other individual in this {@link Archive}. All
	 * {@link Individual}s in the {@link Archive} which were dominated by the
	 * candidates have already been removed.
	 * 
	 * @param candidates
	 *            the non-dominated individuals which can be added
	 * @return true if one or more candidates are added to the archive
	 */
	protected abstract boolean updateWithNondominated(Collection<Individual> candidates);

}
