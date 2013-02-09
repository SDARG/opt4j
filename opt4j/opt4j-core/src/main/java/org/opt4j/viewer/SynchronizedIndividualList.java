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

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualSet;
import org.opt4j.core.IndividualSetListener;

/**
 * The {link SynchronizedIndividualList} is a synchronized list wrapper of the
 * {@link IndividualSet}.
 * 
 * @author lukasiewycz
 * 
 */
class SynchronizedIndividualList implements List<Individual>, IndividualSetListener {

	private final List<Individual> list = new ArrayList<Individual>();

	private final Object mutex;

	protected final Set<IndividualSetListener> listeners = new CopyOnWriteArraySet<IndividualSetListener>();

	public void addListener(IndividualSetListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IndividualSetListener listener) {
		listeners.remove(listener);
	}

	public SynchronizedIndividualList(IndividualSet collection) {
		mutex = this;

		collection.addListener(this);

		while (true) {
			try {
				list.addAll(collection);
				break;
			} catch (ConcurrentModificationException e) {
				list.clear();
			}
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
		for (IndividualSetListener listener : listeners) {
			listener.individualRemoved(collection, individual);
		}
	}

	@Override
	public int size() {
		synchronized (mutex) {
			return list.size();
		}
	}

	@Override
	public boolean isEmpty() {
		synchronized (mutex) {
			return list.isEmpty();
		}
	}

	@Override
	public boolean contains(Object o) {
		synchronized (mutex) {
			return list.contains(o);
		}
	}

	@Override
	public Iterator<Individual> iterator() {
		synchronized (mutex) {
			return list.iterator();
		}
	}

	@Override
	public Object[] toArray() {
		synchronized (mutex) {
			return list.toArray();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		synchronized (mutex) {
			return list.toArray(a);
		}
	}

	@Override
	public boolean add(Individual e) {
		synchronized (mutex) {
			return list.add(e);
		}
	}

	@Override
	public boolean remove(Object o) {
		synchronized (mutex) {
			return list.remove(o);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		synchronized (mutex) {
			return list.containsAll(c);
		}
	}

	@Override
	public boolean addAll(Collection<? extends Individual> c) {
		synchronized (mutex) {
			return list.addAll(c);
		}
	}

	@Override
	public boolean addAll(int index, Collection<? extends Individual> c) {
		synchronized (mutex) {
			return list.addAll(index, c);
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		synchronized (mutex) {
			return list.removeAll(c);
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		synchronized (mutex) {
			return list.retainAll(c);
		}
	}

	@Override
	public void clear() {
		synchronized (mutex) {
			list.clear();
		}
	}

	@Override
	public Individual get(int index) {
		synchronized (mutex) {
			return list.get(index);
		}
	}

	@Override
	public Individual set(int index, Individual element) {
		synchronized (mutex) {
			return list.set(index, element);
		}
	}

	@Override
	public void add(int index, Individual element) {
		synchronized (mutex) {
			list.add(index, element);
		}
	}

	@Override
	public Individual remove(int index) {
		synchronized (mutex) {
			return list.remove(index);
		}
	}

	@Override
	public int indexOf(Object o) {
		synchronized (mutex) {
			return list.indexOf(o);
		}
	}

	@Override
	public int lastIndexOf(Object o) {
		synchronized (mutex) {
			return lastIndexOf(o);
		}
	}

	@Override
	public ListIterator<Individual> listIterator() {
		synchronized (mutex) {
			return list.listIterator();
		}
	}

	@Override
	public ListIterator<Individual> listIterator(int index) {
		synchronized (mutex) {
			return list.listIterator(index);
		}
	}

	@Override
	public List<Individual> subList(int fromIndex, int toIndex) {
		synchronized (mutex) {
			return list.subList(fromIndex, toIndex);
		}
	}

}
