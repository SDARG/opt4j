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

package org.opt4j.core.optimizer;

import org.opt4j.start.Opt4JModule;

/**
 * The {@link ControlListener} is an interface of classes that listen to the
 * {@link Control} state. To add an {@link ControlListener} use the method
 * {@link Opt4JModule#addControlListener(Class)}.
 * 
 * @author lukasiewycz
 * 
 */
public interface ControlListener {

	/**
	 * Invoked if the state of the {@link Control} changes.
	 * 
	 * @param state
	 *            the current state of the optimization process
	 */
	public void stateChanged(Control.State state);

}
