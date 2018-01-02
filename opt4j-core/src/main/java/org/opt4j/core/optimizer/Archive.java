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


package org.opt4j.core.optimizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.common.archive.DefaultArchive;

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
