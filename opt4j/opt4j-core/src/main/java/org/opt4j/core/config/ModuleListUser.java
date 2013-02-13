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
package org.opt4j.core.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.google.inject.Module;

/**
 * The {@link ModuleListUser} is a {@link ModuleList} for user defined modules.
 * 
 * @author lukasiewycz
 * 
 */
public class ModuleListUser implements ModuleList {

	protected Set<Class<? extends Module>> set = new HashSet<Class<? extends Module>>();

	/**
	 * Adds a module.
	 * 
	 * @param module
	 *            the module to be added
	 */
	public void add(Class<? extends Module> module) {
		set.add(module);
	}

	/**
	 * Removes a module.
	 * 
	 * @param module
	 *            the module to be removed
	 */
	public void remove(Class<? extends Module> module) {
		set.remove(module);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.ModuleList#getModules()
	 */
	@Override
	public Collection<Class<? extends Module>> getModules() {
		return set;
	}

}
