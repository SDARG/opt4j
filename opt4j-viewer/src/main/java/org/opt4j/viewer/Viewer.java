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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.Optimizer;
import org.opt4j.core.optimizer.OptimizerStateListener;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link Viewer}.
 * 
 * @author lukasiewycz
 * 
 */
public class Viewer implements OptimizerStateListener {

	/**
	 * The {@link CloseEvent} that is triggered if this viewer is closed.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum CloseEvent {
		/**
		 * Optimization continues.
		 */
		@Info("Opimization is not influenced")
		NONE,
		/**
		 * Optimization is stopped.
		 */
		@Info("Opimization id stopped")
		STOP,
		/**
		 * Optimization is terminated.
		 */
		@Info("Opimization is terminated")
		TERMINATE;

	}

	protected final Control control;

	protected final CloseEvent closeEvent;

	protected final boolean closeOnStop;

	protected final String title;

	protected final Viewport viewport;

	protected final ToolBar toolBar;

	protected final StatusBar statusBar;

	protected JFrame frame = null;

	/**
	 * Constructs a {@link Viewer}.
	 * 
	 * @param viewport
	 *            the viewport
	 * @param toolBar
	 *            the toolBar
	 * @param statusBar
	 *            the statusBar
	 * @param control
	 *            the control
	 * @param title
	 *            the title of the frame
	 * @param closeEvent
	 *            event on closing the GUI window
	 * @param closeOnStop
	 *            close window at the end of the optimization run
	 */
	@Inject
	public Viewer(Viewport viewport, ToolBar toolBar, StatusBar statusBar, Control control,
			@Constant(value = "title", namespace = Viewer.class) String title,
			@Constant(value = "closeEvent", namespace = Viewer.class) CloseEvent closeEvent,
			@Constant(value = "closeOnStop", namespace = Viewer.class) boolean closeOnStop) {
		this.viewport = viewport;
		this.toolBar = toolBar;
		this.statusBar = statusBar;
		this.title = title;
		this.control = control;
		this.closeEvent = closeEvent;
		this.closeOnStop = closeOnStop;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStarted(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStarted(Optimizer optimizer) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Viewer.this.frame = new JFrame();
		final JFrame frame = Viewer.this.frame;

		ImageIcon logo = Icons.getIcon(Icons.OPT4J);
		frame.setIconImage(logo.getImage());
		frame.setTitle(title);
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(800, 600));

		toolBar.init();
		viewport.init();
		statusBar.init();

		frame.add(toolBar.get(), BorderLayout.NORTH);
		frame.add(viewport.get(), BorderLayout.CENTER);
		frame.add(statusBar.get(), BorderLayout.SOUTH);

		WindowListener windowListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				switch (closeEvent) {
				case NONE:
					break;
				case TERMINATE:
					control.doTerminate();
					break;
				default:
					control.doStop();
				}
				frame.dispose();
			}
		};

		frame.addWindowListener(windowListener);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.OptimizerStateListener#optimizationStopped(org
	 * .opt4j.core.optimizer.Optimizer)
	 */
	@Override
	public void optimizationStopped(Optimizer optimizer) {
		if (closeOnStop && frame != null) {
			frame.dispose();
		}
	}
}
