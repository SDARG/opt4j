/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */
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
 * The {@link ObjectivesMonitor} informs its listeners about the {@link Objective}s of the optimization problem as soon
 * as it is available.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class ObjectivesMonitor implements IndividualStateListener {

	protected boolean done = false;

	protected List<Objective> objectives = null;

	protected Set<ObjectivesListener> listeners = new CopyOnWriteArraySet<ObjectivesListener>();

	/**
	 * The {@link ObjectivesListener} is an interface for classes that need the objectives.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public interface ObjectivesListener {

		/**
		 * Callback method that passes the objectives of the optimization problem.
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
	 * @see org.opt4j.core.IndividualStateListener#inidividualStateChanged(org.opt4j .core.Individual)
	 */
	@Override
	public void inidividualStateChanged(Individual individual) {
		if (!done && individual.getState() == State.EVALUATED) {
			publishObjectives(individual);
		}
	}

	protected synchronized void publishObjectives(Individual individual) {
		if (!done) {
			objectives = new ArrayList<Objective>(individual.getObjectives().getKeys());
			for (ObjectivesListener listener : listeners) {
				listener.objectives(objectives);
			}
			Collections.sort(objectives);
			done = true;
		}
	}
}
