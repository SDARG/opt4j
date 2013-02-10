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
package org.opt4j.viewer;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.IndividualSetListener;

/**
 * The {link SynchronizedIndividualList} is a synchronized set wrapper of the
 * {@link IndividualSet}.
 * 
 * @author lukasiewycz
 * 
 */
class SynchronizedIndividualSet implements Set<Individual>, IndividualSetListener {

	private final Set<Individual> set = new HashSet<Individual>();

	private final Object mutex;

	protected final Set<IndividualSetListener> listeners = new CopyOnWriteArraySet<IndividualSetListener>();

	public void addListener(IndividualSetListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IndividualSetListener listener) {
		listeners.remove(listener);
	}

	public SynchronizedIndividualSet(IndividualSet collection) {
		mutex = this;

		collection.addListener(this);

		while (true) {
			try {
				set.addAll(collection);
				break;
			} catch (ConcurrentModificationException e) {
				set.clear();
			}
		}
	}

	@Override
	public int size() {
		synchronized (mutex) {
			return set.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (mutex) {
			return set.isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {
		synchronized (mutex) {
			return set.contains(o);
		}
	}

	@Override
	public Iterator<Individual> iterator() {
		synchronized (mutex) {
			return set.iterator();
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (mutex) {
			return set.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized (mutex) {
			return set.toArray(a);
		}
	}

	@Override
	public boolean add(Individual e) {
		synchronized (mutex) {
			return set.add(e);
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized (mutex) {
			return set.remove(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (mutex) {
			return set.containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends Individual> c) {
		synchronized (mutex) {
			return set.addAll(c);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (mutex) {
			return set.retainAll(c);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized (mutex) {
			return c.removeAll(c);
		}
	}

	@Override
	public void clear() {
		synchronized (mutex) {
			set.clear();
		}
	}

	@Override
	public void individualAdded(IndividualSet collection, Individual individual) {
		add(individual);
		for (IndividualSetListener listener : listeners) {
			listener.individualAdded(collection, individual);
		}
	}

	@Override
	public void individualRemoved(IndividualSet collection, Individual individual) {
		remove(individual);
	}

}
