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


package org.opt4j.satdecoding;

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
