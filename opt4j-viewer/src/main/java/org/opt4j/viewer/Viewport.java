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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.opt4j.core.config.Icons;

/**
 * The {@link Viewport} is a desktop for {@link Widget}s.
 * 
 * @see Widget
 * @author lukasiewycz
 * 
 */
public class Viewport {

	protected final JDesktopPane desktop = new JDesktopPane();

	protected final JPanel panel = new JPanel();

	protected final static int OFFSET = 30;

	protected final Map<Widget, JInternalFrame> widgets = new HashMap<>();

	/**
	 * Initialization. This method has to be called once after construction.
	 */
	public void init() {
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		panel.setLayout(new BorderLayout());
		panel.add(desktop, BorderLayout.CENTER);

		desktop.setBackground(Color.WHITE);
	}

	/**
	 * Adds a widget.
	 * 
	 * @param widget
	 *            the widget to be added
	 */
	public void addWidget(final Widget widget) {
		final JInternalFrame frame;

		if (!widgets.keySet().contains(widget)) {
			frame = createInternalFrame(widget);
			addToDesktop(frame);
		} else {
			frame = widgets.get(widget);
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				if (frame.isIcon()) {
					try {
						frame.setIcon(false);
					} catch (PropertyVetoException e) {
						// ignore
					}
				}
				frame.setVisible(true);
				frame.moveToFront();
			}
		});
	}

	/**
	 * Creates a widget as a {@link JInternalFrame}.
	 * 
	 * @param widget
	 *            the widget
	 * @return the constructed JInternalFrame
	 */
	protected JInternalFrame createInternalFrame(final Widget widget) {
		final JPanel panel = widget.getPanel();

		final String frameTitle;
		final Icon frameIcon;

		boolean resizable = true;
		boolean closable = true;
		boolean maximizable = true;
		boolean iconifiable = true;

		WidgetParameters parameters = widget.getClass().getAnnotation(WidgetParameters.class);
		if (parameters != null) {
			frameTitle = parameters.title();
			if (!parameters.icon().equals("")) {
				frameIcon = Icons.getIcon(parameters.icon());
			} else {
				frameIcon = null;
			}
			resizable = parameters.resizable();
			closable = parameters.closable();
			maximizable = parameters.maximizable();
			iconifiable = parameters.iconifiable();
		} else {
			frameTitle = null;
			frameIcon = null;
		}

		final JInternalFrame inFrame = new JInternalFrame(frameTitle, resizable, closable, maximizable, iconifiable);
		widget.init(this);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				inFrame.addInternalFrameListener(new InternalFrameAdapter() {
					@Override
					public void internalFrameClosed(InternalFrameEvent arg0) {
						widgets.remove(widget);
					}
				});
				inFrame.setFrameIcon(frameIcon);
				inFrame.getContentPane().setLayout(new BorderLayout());
				inFrame.getContentPane().add(panel, BorderLayout.CENTER);

				int windows = desktop.getAllFrames().length;
				windows = windows % 6;
				inFrame.setLocation(OFFSET * windows, OFFSET * windows);
			}
		});

		widgets.put(widget, inFrame);

		return inFrame;
	}

	/**
	 * Adds a {@link JInternalFrame}.
	 * 
	 * @param inFrame
	 *            the JInternalFrame to be added
	 */
	public void addToDesktop(final JInternalFrame inFrame) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				inFrame.pack();
				desktop.add(inFrame);
			}
		});
	}

	/**
	 * Returns the {@link JInternalFrame} of a {@link Widget} if it exists and
	 * {@code null} otherwise.
	 * 
	 * @param widget
	 *            the widget
	 * @return the internal frame
	 */
	public JInternalFrame getInternalFrame(Widget widget) {
		return widgets.get(widget);
	}

	/**
	 * Returns the component.
	 * 
	 * @return the component
	 */
	public JComponent get() {
		return panel;
	}

}
