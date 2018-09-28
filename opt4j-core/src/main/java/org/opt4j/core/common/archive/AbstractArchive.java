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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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
		List<Individual> candidates = new ArrayList<>(individuals);
		/*
		 * Remove all individuals that are already in the archive.
		 */
		candidates.removeAll(this);
		removeDominatedCandidates(candidates);

		removeArchiveDominated(candidates);

		return updateWithNondominated(candidates);
	}

	/**
	 * Remove candidates, which are weakly dominated by another candidate.
	 * 
	 * Thus, the list of candidates which need to be tested against the whole
	 * archive is reduced.
	 * 
	 * @param candidates
	 *            the list of candidates to sanitize
	 */
	protected void removeDominatedCandidates(List<Individual> candidates) {
		for (int i = 0; i < candidates.size() - 1; i++) {
			Objectives o1 = candidates.get(i).getObjectives();
			for (int j = i + 1; j < candidates.size(); j++) {
				Objectives o2 = candidates.get(j).getObjectives();
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
	}

	/**
	 * Remove those individuals from the candidates which are weakly dominated
	 * by the archive. Remove those individuals from the archive which are
	 * dominated by the candidates. In case of equal objectives, this gives
	 * priority to the individuals in the archive and avoids unnecessary archive
	 * updates.
	 * 
	 * @param candidates
	 *            the list of candidates to sanitize
	 */
	protected void removeArchiveDominated(List<Individual> candidates) {
		Iterator<Individual> i1;
		Iterator<Individual> i2;
		for (i1 = candidates.iterator(); i1.hasNext();) {
			Objectives o1 = i1.next().getObjectives();
			for (i2 = this.iterator(); i2.hasNext();) {
				Objectives o2 = i2.next().getObjectives();
				if (o2.weaklyDominates(o1)) {
					i1.remove();
					break;
				} else if (o1.dominates(o2)) {
					i2.remove();
				}
			}
		}
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
