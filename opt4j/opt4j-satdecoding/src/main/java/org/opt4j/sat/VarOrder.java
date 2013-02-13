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

package org.opt4j.sat;

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
