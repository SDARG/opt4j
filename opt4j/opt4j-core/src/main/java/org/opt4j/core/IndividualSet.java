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

package org.opt4j.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The {@link IndividualSet} is a {@link Set} of {@link Individual}s. It allows
 * to add and remove listeners, see {@link IndividualSetListener}.
 * 
 * @see org.opt4j.core.optimizer.Archive
 * @see org.opt4j.core.optimizer.Population
 * @author lukasiewycz
 * 
 */
public class IndividualSet implements Set<Individual> {

	protected final Set<Individual> individuals = new LinkedHashSet<Individual>();

	protected final Set<IndividualSetListener> listeners = new CopyOnWriteArraySet<IndividualSetListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	@Override
	public boolean add(Individual individual) {
		boolean b = false;
		if (!individuals.contains(individual)) {
			b = individuals.add(individual);
		}
		if (b) {
			for (IndividualSetListener listener : listeners) {
				listener.individualAdded(this, individual);
			}
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#iterator()
	 */
	@Override
	public Iterator<Individual> iterator() {
		final Iterator<Individual> iterator = individuals.iterator();

		return new Iterator<Individual>() {

			Individual current = null;

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public Individual next() {
				current = iterator.next();
				return current;
			}

			@Override
			public void remove() {
				iterator.remove();
				if (current != null) {
					for (IndividualSetListener listener : listeners) {
						listener.individualRemoved(IndividualSet.this, current);
					}
				}
			}
		};

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#size()
	 */
	@Override
	public int size() {
		return individuals.size();
	}

	/**
	 * Adds a listener.
	 * 
	 * @see #removeListener
	 * @param listener
	 *            the added listener
	 */
	public void addListener(IndividualSetListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener.
	 * 
	 * @see #addListener
	 * @param listener
	 *            the removed listener
	 */
	public void removeListener(IndividualSetListener listener) {
		listeners.remove(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#clear()
	 */
	@Override
	public void clear() {
		Iterator<Individual> it = iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Individual> c) {
		boolean res = false;
		for (Individual individual : c) {
			boolean b = individuals.add(individual);
			if (b) {
				for (IndividualSetListener listener : listeners) {
					listener.individualAdded(this, individual);
				}
			}
			res |= b;
		}
		return res;
	}

	/**
	 * Add all {@link Individual}s.
	 * 
	 * @param c
	 *            the individuals to be added
	 * @return true if at least one individual was added
	 */
	public boolean addAll(Individual... c) {
		boolean res = false;
		for (Individual individual : c) {
			boolean b = individuals.add(individual);
			if (b) {
				for (IndividualSetListener listener : listeners) {
					listener.individualAdded(this, individual);
				}
			}
			res |= b;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		return individuals.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		return individuals.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return individuals.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		boolean value = individuals.remove(o);
		if (value) {
			for (IndividualSetListener listener : listeners) {
				listener.individualRemoved(this, (Individual) o);
			}
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		boolean res = false;
		Iterator<Individual> it = iterator();
		while (it.hasNext()) {
			if (c.contains(it.next())) {
				it.remove();
				res = true;
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		boolean res = false;
		Iterator<Individual> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				res = true;
			}
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		return individuals.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Collection#toArray(T[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		return individuals.toArray(a);
	}

}
