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

package org.opt4j.sat;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * The {@link Model} is a data structure that represents a solution of the given
 * problem, i.e., all {@link Literal}s are set such that all {@link Constraint}s
 * specified by the {@link AbstractSATDecoder} are feasible.
 * 
 * @author lukasiewycz
 * 
 */
public class Model {

	protected final Map<Object, Boolean> map = new HashMap<Object, Boolean>();

	/**
	 * Constructs a {@link Model}.
	 */
	public Model() {
		super();
	}

	/**
	 * Returns the value of the variable, or null if this variable is not
	 * defined.
	 * 
	 * @param var
	 *            the variable
	 * @return {@code true} if the variable in this model is true or
	 *         {@code null} if it is not defined
	 */
	public Boolean get(Object var) {
		return map.get(var);
	}

	/**
	 * Sets the variable to a phase.
	 * 
	 * @param var
	 *            the variable
	 * @param phase
	 *            the phase
	 */
	public void set(Object var, boolean phase) {
		map.put(var, phase);
	}

	/**
	 * Returns all variables of the model.
	 * 
	 * @return all variables
	 */
	public Set<Object> getVars() {
		return map.keySet();
	}

	/**
	 * Returns the entry set: The entry pairs of all variables to the boolean
	 * values.
	 * 
	 * @return the entry set
	 */
	public Set<Entry<Object, Boolean>> pairs() {
		return map.entrySet();
	}

}
