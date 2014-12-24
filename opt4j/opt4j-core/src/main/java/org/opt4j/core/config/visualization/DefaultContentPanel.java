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
 

package org.opt4j.core.config.visualization;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.opt4j.core.config.ExecutionEnvironment;
import org.opt4j.core.config.Icons;

import com.google.inject.Inject;

/**
 * The {@link DefaultContentPanel}.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DefaultContentPanel extends ContentPanel {

	protected final ExecutionEnvironment executionEnvironment;

	protected final ModulesPanel modulesPanel;

	protected final SelectedPanel selectedPanel;

	protected final TasksPanel tasksPanel;

	/**
	 * Constructs a {@link DefaultContentPanel}.
	 * 
	 * @param executionEnvironment
	 *            the execution environment
	 * @param modulesPanel
	 *            the modules panel
	 * @param selectedPanel
	 *            the selected panel
	 * @param tasksPanel
	 *            the tasks panel
	 */
	@Inject
	public DefaultContentPanel(final ExecutionEnvironment executionEnvironment, final ModulesPanel modulesPanel,
			final SelectedPanel selectedPanel, final TasksPanel tasksPanel) {
		super();
		this.executionEnvironment = executionEnvironment;
		this.modulesPanel = modulesPanel;
		this.selectedPanel = selectedPanel;
		this.tasksPanel = tasksPanel;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {

		setLayout(new BorderLayout());
		final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		splitPane.setResizeWeight(1.0);
		add(splitPane);

		modulesPanel.setPreferredSize(new Dimension(220, 200));
		selectedPanel.setPreferredSize(new Dimension(350, 200));

		JPanel top = new JPanel(new BorderLayout());
		top.add(modulesPanel, BorderLayout.WEST);
		top.add(selectedPanel, BorderLayout.CENTER);

		JTabbedPane bottom = new JTabbedPane();
		bottom.addTab("Tasks", Icons.getIcon(Icons.CONSOLE), tasksPanel);

		bottom.setPreferredSize(new Dimension(300, 160));
		top.setMinimumSize(new Dimension(300, 80));

		splitPane.add(top, JSplitPane.TOP);
		splitPane.add(bottom, JSplitPane.BOTTOM);

		modulesPanel.startup();
		selectedPanel.startup();
		tasksPanel.startup();

		setVisible(true);

	}

}
