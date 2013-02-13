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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import ptolemy.plot.PlotBox;
import ptolemy.plot.zoomBox.ZoomIcons;

/**
 * The {@link AutoZoomButton} is a specialized {@link JButton} which reactivates
 * the auto zoom feature of a {@link PlotBox}. If the auto zoom feature is
 * enabled, the button is disabled.
 * 
 * @author reimann
 * 
 */
public class AutoZoomButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;

	private PlotBox plotBox = null;

	/**
	 * Creates a new {@link AutoZoomButton}.
	 */
	public AutoZoomButton() {
		super(ZoomIcons.getIcon(ZoomIcons.ZOOMaUTO));
		addActionListener(this);
		setEnabled(false);
		setToolTipText("Auto Zoom");
		setPreferredSize(getMinimumSize());
	}

	/**
	 * Returns the {@link PlotBox} this {@link AutoZoomButton} is listening to.
	 * 
	 * @return the plot box
	 */
	public PlotBox getPlotBox() {
		return plotBox;
	}

	/**
	 * Set the {@link PlotBox} to listen for.
	 * 
	 * @param plotBox
	 *            the plot box
	 */
	public void setPlotBox(PlotBox plotBox) {
		if (this.plotBox != null) {
			this.plotBox.removeListener(this);
		}
		this.plotBox = plotBox;
		plotBox.registerListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (plotBox != null) {
			if (e.getSource() == this) {
				// this button was pressed
				plotBox.reactivateAutoScale();
			} else if (e.getSource() == plotBox
					&& e.getActionCommand() == PlotBox.Action.MANUAL_ZOOM_ACTIVATED.toString()) {
				// the plot box has activated manual zoom
				setEnabled(true);
			} else if (e.getSource() == plotBox
					&& e.getActionCommand() == PlotBox.Action.AUTOMATIC_ZOOM_ACTIVATED.toString()) {
				// the plot box has activated automatic zoom
				setEnabled(false);
			}
		}
	}
}
