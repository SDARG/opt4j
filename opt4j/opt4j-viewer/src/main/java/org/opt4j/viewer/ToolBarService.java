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
package org.opt4j.viewer;

import javax.swing.JToolBar;

/**
 * The {@link ToolBarService} is an interface for arbitrary components that are
 * added to the tool bar in the viewer.
 * 
 * @see VisualizationModule#addToolBarService(Class)
 * @see VisualizationModule#addToolBarService(com.google.inject.Binder, Class)
 * @author lukasiewycz
 * 
 */
public interface ToolBarService {

	/**
	 * Returns the component.
	 * 
	 * @return the component
	 */
	public JToolBar getToolBar();

}
