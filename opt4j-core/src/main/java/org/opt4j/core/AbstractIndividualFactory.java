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

package org.opt4j.core;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The {@link AbstractIndividualFactory} creates {@link Individual}s using a
 * given {@link Provider} and sets the registered
 * {@link IndividualStateListener}s. The {@link Creator} is used to create the
 * problem specific {@link Genotype} with which the created {@link Individual}
 * is initialized.
 * 
 * @author lukasiewycz
 * 
 * @param <I>
 *            the type of Individual
 */
public class AbstractIndividualFactory<I extends Individual> implements IndividualFactory {

	protected final Creator<Genotype> creator;
	protected final Provider<I> individualProvider;

	protected final Set<IndividualStateListener> individualStateListeners = new CopyOnWriteArraySet<>();

	/**
	 * Constructs an {@link AbstractIndividualFactory} with a {@link Provider}
	 * for {@link Individual}s.
	 * 
	 * @param individualProvider
	 *            the provider that creates new individuals
	 * @param creator
	 *            the creator that creates random genotypes
	 */
	public AbstractIndividualFactory(Provider<I> individualProvider, Creator<Genotype> creator) {
		this.individualProvider = individualProvider;
		this.creator = creator;
	}

	/**
	 * The {@link IndividualStateListener}s will be transmitted to each
	 * {@link Individual} that is created by this class. The listeners are
	 * invoked if the state of the {@link Individual} changes.
	 * 
	 * @param listeners
	 *            the listeners
	 */
	@Inject
	protected void injectListeners(Set<IndividualStateListener> listeners) {
		individualStateListeners.addAll(listeners);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualFactory#addIndividualStateListener(org.opt4j
	 * .core.IndividualStateListener)
	 */
	@Override
	public void addIndividualStateListener(IndividualStateListener listener) {
		individualStateListeners.add(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.IndividualFactory#create()
	 */
	@Override
	public Individual create() {
		Individual individual = individualProvider.get();
		individual.setIndividualStatusListeners(individualStateListeners);
		Genotype genotype = creator.create();
		individual.setGenotype(genotype);

		return individual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualFactory#create(org.opt4j.core.problem.Genotype)
	 */
	@Override
	public Individual create(Genotype genotype) {
		Individual individual = individualProvider.get();
		individual.setIndividualStatusListeners(individualStateListeners);
		individual.setGenotype(genotype);
		return individual;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualFactory#removeIndividualStateListener(org.opt4j
	 * .core.IndividualStateListener)
	 */
	@Override
	public void removeIndividualStateListener(IndividualStateListener listener) {
		individualStateListeners.remove(listener);
	}
}
