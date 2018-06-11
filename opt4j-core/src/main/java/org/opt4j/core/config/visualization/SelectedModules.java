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
 

package org.opt4j.core.config.visualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.core.config.PropertyModule;

import com.google.inject.Singleton;

/**
 * The {@link SelectedModules}. A set of all selected modules that is extended
 * by a listener functionality.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
@Singleton
public class SelectedModules extends TreeSet<PropertyModule> {

	protected Set<SetListener> listeners = new CopyOnWriteArraySet<SetListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	@Override
	public boolean add(PropertyModule module) {
		boolean b = super.add(module);
		for (SetListener listener : listeners) {
			listener.moduleAdded(this, module);
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends PropertyModule> modules) {
		boolean r = false;
		for (PropertyModule module : modules) {
			r = add(module) || r;
		}
		return r;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashSet#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		boolean b = super.remove(o);
		if (b) {
			for (SetListener listener : listeners) {
				listener.moduleRemoved(this, (PropertyModule) o);
			}
		}
		return b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashSet#clear()
	 */
	@Override
	public void clear() {
		Collection<PropertyModule> modules = new ArrayList<PropertyModule>();
		modules.addAll(this);

		for (PropertyModule module : modules) {
			remove(module);
		}
	}

	/**
	 * Adds a {@link SetListener}.
	 * 
	 * @see #removeListener
	 * @param listener
	 *            the listener to be added
	 */
	public void addListener(SetListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a {@link SetListener}.
	 * 
	 * @see #addListener
	 * @param listener
	 *            the listener to be removed
	 */
	public void removeListener(SetListener listener) {
		listeners.remove(listener);
	}

}
