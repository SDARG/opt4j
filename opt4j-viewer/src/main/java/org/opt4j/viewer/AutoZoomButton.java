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
