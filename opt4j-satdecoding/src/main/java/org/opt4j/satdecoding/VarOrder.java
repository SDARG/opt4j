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
 * The {@link VarOrder} implements an {@link Order} like it is used in MiniSAT.
 * Each variable has an activity and a prioritized decision phase.
 * 
 * @author lukasiewycz
 * 
 */
public class VarOrder extends Order {

	protected final Map<Object, Double> activity = new HashMap<Object, Double>();

	protected final Map<Object, Boolean> phase = new HashMap<Object, Boolean>();

	/**
	 * Constructs a {@link VarOrder}.
	 */
	public VarOrder() {
		super();
	}

	/**
	 * Sets the activity of the specified variable.
	 * 
	 * @see #getActivity
	 * @param variable
	 *            the specific variable
	 * @param value
	 *            the activity value
	 */
	public void setActivity(Object variable, double value) {
		activity.put(variable, value);
	}

	/**
	 * Sets the prioritized phase of the specified variable.
	 * 
	 * @see #getPhase
	 * @param variable
	 *            the specific variable
	 * @param value
	 *            the prioritized phase
	 */
	public void setPhase(Object variable, boolean value) {
		phase.put(variable, value);
	}

	/**
	 * Returns the activity of a variable.
	 * 
	 * @see #setActivity
	 * @param variable
	 *            the variable
	 * @return the activity
	 */
	public double getActivity(Object variable) {
		return activity.get(variable);
	}

	/**
	 * Returns the prioritized phase of a variable.
	 * 
	 * @see #setPhase
	 * @param variable
	 *            the variable
	 * @return the prioritized phase
	 */
	public boolean getPhase(Object variable) {
		return phase.get(variable);
	}

	/**
	 * Returns the entry set of the activities.
	 * 
	 * @return the entry set of the activities
	 */
	public Set<Entry<Object, Double>> getActivityEntrySet() {
		return activity.entrySet();
	}

	/**
	 * Returns the entry set of the phases.
	 * 
	 * @return the entry set of the phases
	 */
	public Set<Entry<Object, Boolean>> getPhaseEntrySet() {
		return phase.entrySet();
	}

}
