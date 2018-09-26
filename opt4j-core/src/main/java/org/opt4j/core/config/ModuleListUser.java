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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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

	protected Set<Class<? extends Module>> set = new HashSet<>();

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
