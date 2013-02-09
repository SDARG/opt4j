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

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.start.Constant;
import org.opt4j.viewer.Viewer.CloseEvent;

/**
 * The {@link ViewerModule} configures the optimization process viewer.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.APPLICATION)
@Info("A graphical viewer to observe the optimization process.")
public class ViewerModule extends VisualizationModule {

	@Info("The title of the viewer frame.")
	@Constant(value = "title", namespace = Viewer.class)
	protected String title = "Opt4J @VERSION@ Viewer";

	@Info("Event for the optimization process if the viewer is closed.")
	@Constant(value = "closeEvent", namespace = Viewer.class)
	protected CloseEvent closeEvent = CloseEvent.STOP;

	@Info("Close viewer automatically when the optimization process stops.")
	@Constant(value = "closeOnStop", namespace = Viewer.class)
	protected boolean closeOnStop = false;

	/**
	 * Returns {@code true} if the GUI is automatically closed if the
	 * optimization process has stopped.
	 * 
	 * @return {@code true} if the GUI is automatically closed on optimization
	 *         stop
	 */
	public boolean isCloseOnStop() {
		return closeOnStop;
	}

	/**
	 * Sets the option for automatically closing the GUI if the optimization
	 * stops.
	 * 
	 * @param closeOnStop
	 *            the closeOnStop to set
	 */
	public void setCloseOnStop(boolean closeOnStop) {
		this.closeOnStop = closeOnStop;
	}

	/**
	 * Returns the event when the GUI is closed.
	 * 
	 * @return the closeEvent
	 */
	public CloseEvent getCloseEvent() {
		return closeEvent;
	}

	/**
	 * Sets the event when the GUI is closed.
	 * 
	 * @param closeEvent
	 *            the closeEvent to set
	 */
	public void setCloseEvent(CloseEvent closeEvent) {
		this.closeEvent = closeEvent;
	}

	/**
	 * Returns the title of the GUI frame.
	 * 
	 * @return the title of the GUI frame
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the GUI frame.
	 * 
	 * @param title
	 *            the title of the GUI frame
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	public void config() {
		bind(Viewer.class).in(SINGLETON);
		addOptimizerStateListener(Viewer.class);

		bind(StatusBar.class).in(SINGLETON);
		addOptimizerStateListener(StatusBar.class);
		addOptimizerIterationListener(StatusBar.class);

		bind(Viewport.class).in(SINGLETON);

		addOptimizerIterationListener(Progress.class);
		addOptimizerStateListener(ControlButtons.class);

		addOptimizerIterationListener(ConvergencePlotData.class);

		addToolBarService(ControlToolBarService.class);
		addToolBarService(ViewsToolBarService.class);
	}

}