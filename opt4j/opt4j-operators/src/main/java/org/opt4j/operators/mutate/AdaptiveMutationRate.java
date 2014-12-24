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
