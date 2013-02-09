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

import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Optimizer;

import com.google.inject.Inject;

/**
 * The {@link ControlToolBarService} is a {@link ToolBarService} that allows to
 * {@link Control} the {@link Optimizer}.This service has an
 * {@link ToolBarOrder} with {@code -100}.
 * 
 * @see Control
 * @author lukasiewycz
 * 
 */
@ToolBarOrder(-100)
public class ControlToolBarService implements ToolBarService {

	protected final ControlButtons controlButtons;

	/**
	 * Constructs a {@link ControlToolBarService}.
	 * 
	 * @param controlButtons
	 *            the control buttons
	 */
	@Inject
	public ControlToolBarService(ControlButtons controlButtons) {
		this.controlButtons = controlButtons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.viewer.ToolBarService#getToolBar()
	 */
	@Override
	public JToolBar getToolBar() {
		JToolBar toolbar = new JToolBar("Control");

		toolbar.add(controlButtons.getStart());
		toolbar.add(controlButtons.getPause());
		toolbar.add(controlButtons.getStop());
		toolbar.add(controlButtons.getTerminate());

		return toolbar;
	}

}
