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
 

package org.opt4j.core.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.opt4j.core.config.annotations.Multi;
import org.opt4j.core.config.visualization.Configurator;

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
				try {
					// try to initialize static code of the module classes while
					// searching modules and not later
					clazz.newInstance();
				} catch (InstantiationException e) {
				} catch (IllegalAccessException e) {
				}
			}
			System.out.println("Done");

			isInit = true;
		}
	}

}
