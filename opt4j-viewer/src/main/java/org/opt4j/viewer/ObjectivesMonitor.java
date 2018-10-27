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

package org.opt4j.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.Objective;
import org.opt4j.core.optimizer.Archive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link ObjectivesMonitor} informs its listeners about the
 * {@link Objective}s of the optimization problem as soon as it is available.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class ObjectivesMonitor implements IndividualStateListener {

	protected boolean done = false;

	protected List<Objective> objectives = null;

	protected Set<ObjectivesListener> listeners = new CopyOnWriteArraySet<>();

	/**
	 * The {@link ObjectivesListener} is an interface for classes that need the
	 * objectives.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public interface ObjectivesListener {

		/**
		 * Callback method that passes the objectives of the optimization
		 * problem.
		 * 
		 * @param objectives
		 *            the objectives
		 */
		public void objectives(Collection<Objective> objectives);
	}

	/**
	 * Constructs the {@link ObjectivesMonitor}.
	 * 
	 * @param individualFactory
	 *            the individual factory
	 * @param archive
	 *            the archive
	 */
	@Inject
	public ObjectivesMonitor(IndividualFactory individualFactory, Archive archive) {
		super();
		individualFactory.addIndividualStateListener(this);

		if (!archive.isEmpty()) {
			publishObjectives(archive.iterator().next());
		}
	}

	/**
	 * Adds an {@link ObjectivesListener}.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addListener(ObjectivesListener listener) {
		synchronized (this) {
			if (done) {
				listener.objectives(objectives);
			} else {
				listeners.add(listener);
			}
		}
	}

	/**
	 * Remove an {@link ObjectivesListener}.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removeListener(ObjectivesListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j
	 * .core.Individual)
	 */
	@Override
	public void inidividualStateChanged(Individual individual) {
		if (!done && individual.getState() == State.EVALUATED) {
			publishObjectives(individual);
		}
	}

	protected synchronized void publishObjectives(Individual individual) {
		if (!done) {
			objectives = new ArrayList<>(individual.getObjectives().getKeys());
			for (ObjectivesListener listener : listeners) {
				listener.objectives(objectives);
			}
			Collections.sort(objectives);
			done = true;
		}
	}
}
