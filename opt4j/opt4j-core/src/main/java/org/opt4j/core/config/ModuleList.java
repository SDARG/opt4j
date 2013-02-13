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

import com.google.inject.ImplementedBy;
import com.google.inject.Module;

/**
 * The {@link ModuleList} offers a method to obtain a {@link Collection} of
 * {@link Module}s.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(ModuleAutoFinder.class)
public interface ModuleList {

	/**
	 * Returns the classes of all modules.
	 * 
	 * @return the classes of all modules
	 */
	public Collection<Class<? extends Module>> getModules();

}
