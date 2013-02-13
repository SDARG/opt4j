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

package org.opt4j.core.optimizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.opt4j.common.archive.DefaultArchive;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualSet;

import com.google.inject.ImplementedBy;

/**
 * An {@link Archive} is used to store a set of high-quality {@link Individual}
 * s. Commonly, these {@link Individual}s are non-dominated.
 * 
 * @author helwig, lukasiewycz
 */
@ImplementedBy(DefaultArchive.class)
public abstract class Archive extends IndividualSet {

	/**
	 * Updates the archive with a single individual.
	 * 
	 * @param individual
	 *            the individual that is used to update the archive
	 * @return returns {@code true} if the content of the archive changed
	 */
	public boolean update(Individual individual) {
		Set<Individual> set = new HashSet<Individual>();
		set.add(individual);
		return update(set);
	}

	/**
	 * Updates the archive with a set of individuals. Instead of the {@code add}
	 * /{@code addAll} methods, which are prohibited for the archive (throwing
	 * an {@link UnsupportedOperationException}), this method shall be used.
	 * 
	 * @param individuals
	 *            the set of individuals that is used to update the archive
	 * @return returns {@code true} if the content of the archive changed
	 */
	public abstract boolean update(Set<? extends Individual> individuals);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualSet#add(org.opt4j.core.Individual)
	 */
	@Deprecated
	@Override
	public final boolean add(Individual individual) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualSet#addAll(java.util.Collection)
	 */
	@Deprecated
	@Override
	public final boolean addAll(Collection<? extends Individual> c) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualSet#addAll(org.opt4j.core.Individual[])
	 */
	@Deprecated
	@Override
	public final boolean addAll(Individual... c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Adds the {@link Individual} to this {@link Archive} without further
	 * checks. Must be used only if the {@link Individual} is checked to be
	 * Pareto-dominant and, according to possible archive size restrictions, can
	 * be added to this {@link Archive}.
	 * 
	 * @param individual
	 *            the individual to be actually added to the archive
	 * @return true
	 */
	protected boolean addCheckedIndividual(Individual individual) {
		return super.add(individual);
	}

	/**
	 * Adds the {@link Individual}s to this {@link Archive} without further
	 * checks. Must be used only if the {@link Individual}s are checked to be
	 * Pareto-dominant and, according to possible archive size restrictions, can
	 * all be added to this {@link Archive}.
	 * 
	 * @param individuals
	 *            the individuals to be actually added to the archive
	 * @return true
	 */
	protected boolean addCheckedIndividuals(Collection<? extends Individual> individuals) {
		return super.addAll(individuals);
	}

}
