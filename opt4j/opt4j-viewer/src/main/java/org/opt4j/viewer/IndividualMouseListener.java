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
package org.opt4j.viewer;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JPopupMenu;

import org.opt4j.core.Individual;

/**
 * Listener for mouse events on {@link Individual}s. By default this listener is
 * applied on the {@link ArchiveWidget}.
 * 
 * @author lukasiewycz
 * 
 */
public interface IndividualMouseListener {

	/**
	 * Invoked if an {@link Individual} is selected by a {@code popup} (usually
	 * with a right click of the mouse).
	 * 
	 * @param individual
	 *            the selected individual
	 * @param component
	 *            the component
	 * @param p
	 *            the point
	 * @param menu
	 *            a created popup menu
	 */
	public void onPopup(Individual individual, Component component, Point p, JPopupMenu menu);

	/**
	 * Invoked if an {@link Individual} is selected by a {@code double click}.
	 * 
	 * @param individual
	 *            the selected individual
	 * @param component
	 *            the component
	 * @param p
	 *            the point
	 */
	public void onDoubleClick(Individual individual, Component component, Point p);

}
