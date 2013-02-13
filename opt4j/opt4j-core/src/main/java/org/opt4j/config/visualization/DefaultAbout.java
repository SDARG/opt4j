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

package org.opt4j.config.visualization;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.google.inject.Singleton;

/**
 * The {@link DefaultAbout} panel.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class DefaultAbout implements About {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.config.visualization.AboutFrame#getDialog(org.opt4j.config.
	 * visualization.ApplicationFrame)
	 */
	@Override
	public JDialog getDialog(ApplicationFrame frame) {
		JDialog dialog = new JDialog(frame, "About Configurator", true);
		dialog.setBackground(Color.WHITE);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setResizable(false);

		dialog.add(new JLabel("Void Frame"));
		dialog.pack();
		dialog.setVisible(false);

		return dialog;
	}

}
