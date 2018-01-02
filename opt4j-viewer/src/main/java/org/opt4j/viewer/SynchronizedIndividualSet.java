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
