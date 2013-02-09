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

package org.opt4j.config.visualization;

import javax.swing.JPanel;

import com.google.inject.ImplementedBy;

/**
 * The {@link ContentPanel}. This panel contains a visualization of the selected
 * modules.
 * 
 * @author lukasiewycz
 * @see SelectedModules
 * 
 */
@SuppressWarnings("serial")
@ImplementedBy(DefaultSelectedPanel.class)
public abstract class SelectedPanel extends JPanel implements Startupable {

}
