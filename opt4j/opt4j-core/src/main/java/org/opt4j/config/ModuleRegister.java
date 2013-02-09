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

package org.opt4j.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.opt4j.config.annotations.Multi;
import org.opt4j.config.visualization.Configurator;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * The {@link ModuleRegister} contains all found modules for the
 * {@link Configurator}.
 * 
 * @author lukasiewycz.
 * 
 */
@Singleton
public class ModuleRegister implements Iterable<Class<? extends Module>> {

	protected final Map<Class<? extends Module>, PropertyModule> map = Collections
			.synchronizedMap(new HashMap<Class<? extends Module>, PropertyModule>());
	protected final Set<Class<? extends Module>> set = Collections
			.synchronizedSet(new HashSet<Class<? extends Module>>());

	protected final ModuleList finder;

	protected boolean isInit = false;

	/**
	 * Constructs and populates the {@link ModuleRegister}.
	 * 
	 * @param finder
	 *            the module finder to be used
	 */
	@Inject
	public ModuleRegister(final ModuleList finder) {
		super();
		this.finder = finder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public synchronized Iterator<Class<? extends Module>> iterator() {
		checkInit();

		return set.iterator();
	}

	/**
	 * Returns the number of found modules.
	 * 
	 * @see java.util.HashSet#size()
	 * @return the number of found modules
	 */
	public synchronized int size() {
		checkInit();
		return set.size();
	}

	/**
	 * Returns the {@link PropertyModule} of the specified class and creates a
	 * new instance if necessary.
	 * 
	 * If the property module can not be created, {@code null} is returned.
	 * 
	 * @param clazz
	 *            the class of the module
	 * @return the property module or null
	 */
	public PropertyModule get(Class<? extends Module> clazz) {
		PropertyModule propertyModule = null;

		if (clazz.getAnnotation(Multi.class) == null && map.containsKey(clazz)) {
			propertyModule = map.get(clazz);
		} else {

			try {
				Module module = clazz.newInstance();
				propertyModule = new PropertyModule(module);
				map.put(clazz, propertyModule);
			} catch (InstantiationException e) {
				System.err.println("Failed to create an instance of module " + clazz + ".");
			} catch (IllegalAccessException e) {
				System.err.println("Failed to create an instance of module " + clazz + ".");
			}
		}

		return propertyModule;
	}

	/**
	 * Initialize the modules once.
	 */
	private synchronized void checkInit() {
		while (!isInit) {
			System.out.print("Searching Modules ... ");
			for (Class<? extends Module> clazz : finder.getModules()) {
				set.add(clazz);
			}
			System.out.println("Done");

			isInit = true;
		}
	}

}
