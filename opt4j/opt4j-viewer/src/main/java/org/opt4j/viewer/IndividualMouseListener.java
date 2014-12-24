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
