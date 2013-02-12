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

	protected final Set<IndividualStateListener> individualStateListeners = new CopyOnWriteArraySet<IndividualStateListener>();

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
