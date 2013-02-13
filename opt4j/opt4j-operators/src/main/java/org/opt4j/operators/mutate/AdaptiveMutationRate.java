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

package org.opt4j.operators.mutate;

import static org.opt4j.core.Individual.State.EMPTY;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.IndividualStateListener;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Self adaptive {@link MutationRate} that uses the size of the genotype (
 * {@code size}) to estimate a rate. The calculated rate is 1/{@code size}.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class AdaptiveMutationRate implements MutationRate, IndividualStateListener {

	protected boolean isInit = false;

	protected double rate = 0;

	protected final IndividualFactory individualFactory;

	/**
	 * Constructs an {@link AdaptiveMutationRate}.
	 * 
	 * @param individualFactory
	 *            the individual creator
	 */
	@Inject
	public AdaptiveMutationRate(IndividualFactory individualFactory) {
		this.individualFactory = individualFactory;
	}

	/**
	 * Initializes the listeners.
	 */
	@Inject
	public void init() {
		individualFactory.addIndividualStateListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#get()
	 */
	@Override
	public double get() {
		return rate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.mutate.MutationRate#set(double)
	 */
	@Override
	public void set(double value) {
		this.rate = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j
	 * .core.Individual)
	 */
	@Override
	public synchronized void inidividualStateChanged(Individual individual) {
		if (!isInit && individual.getState() != EMPTY) {
			final int size = individual.getGenotype().size();
			if (size > 0) {
				set(1.0 / individual.getGenotype().size());
			}
			individualFactory.removeIndividualStateListener(this);
			isInit = true;
		}
	}
}
