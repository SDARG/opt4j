/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.core;

import com.google.inject.ImplementedBy;

/**
 * The {@link IndividualFactory} is a creator for {@link Individual}s.
 * 
 * @author lukasiewycz, glass
 * 
 */
@ImplementedBy(DefaultIndividualFactory.class)
public interface IndividualFactory {

	/**
	 * Creates a new {@link Individual}.
	 * 
	 * @return the built individual
	 */
	public Individual create();

	/**
	 * Creates a new {@link Individual} with a specified {@link Genotype}.
	 * 
	 * @param genotype
	 *            the genotype of the individual
	 * @return the built individual
	 */
	public Individual create(Genotype genotype);

	/**
	 * Adds an {@link IndividualStateListener} to each {@link Individual} that
	 * is created by this class. This listener is invoked if the state of the
	 * {@link Individual} changes.
	 * 
	 * @see #removeIndividualStateListener
	 * @param listener
	 *            the listener that is invoked if the state of any individual
	 *            changes
	 */
	public void addIndividualStateListener(IndividualStateListener listener);

	/**
	 * Removes an {@link IndividualStateListener} that is invoked if the state
	 * of any {@link Individual} changes. All {@link Individual}s created after
	 * this method call by this {@link IndividualFactory} do no longer use the
	 * listener.
	 * 
	 * @see #addIndividualStateListener
	 * @param listener
	 *            the listener that is invoked if the state of any individual
	 *            changes
	 */
	public void removeIndividualStateListener(IndividualStateListener listener);

}
