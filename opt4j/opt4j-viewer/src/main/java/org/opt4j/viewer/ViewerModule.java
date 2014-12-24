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

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.start.Constant;
import org.opt4j.core.start.Opt4J;
import org.opt4j.core.start.Progress;
import org.opt4j.viewer.Viewer.CloseEvent;

import ptolemy.plot.DefaultFonts;

/**
 * The {@link ViewerModule} configures the optimization process viewer.
 * 
 * @author lukasiewycz
 * 
 */
@Icon(Icons.APPLICATION)
@Info("A graphical viewer to observe the optimization process.")
public class ViewerModule extends VisualizationModule {

	static {
		// initialize ptolemy plot once the viewer module is on the classpath
		if (DefaultFonts.LABElFONT == null) {
			throw new IllegalStateException();
		}
	}

	@Info("The title of the viewer frame.")
	@Constant(value = "title", namespace = Viewer.class)
	protected String title = "Opt4J " + Opt4J.getVersion() + " Viewer";

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