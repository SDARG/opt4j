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

import javax.swing.JDialog;

import com.google.inject.ImplementedBy;

/**
 * The {@link About} {@link JDialog} shows information about this framework.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(DefaultAbout.class)
public interface About {

	/**
	 * Returns the about dialog.
	 * 
	 * @param frame
	 *            the application frame
	 * @return the about dialog
	 */
	public JDialog getDialog(ApplicationFrame frame);

}
