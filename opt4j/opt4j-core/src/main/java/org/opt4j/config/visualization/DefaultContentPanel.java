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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.opt4j.config.ExecutionEnvironment;
import org.opt4j.config.Icons;

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
