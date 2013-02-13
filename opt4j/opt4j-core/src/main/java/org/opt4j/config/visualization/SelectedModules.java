/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.config.visualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

import org.opt4j.config.PropertyModule;

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

	/**
	 * Constructs a new, empty {@link SelectedModules} set.
	 */
	public SelectedModules() {
		super();
	}

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
