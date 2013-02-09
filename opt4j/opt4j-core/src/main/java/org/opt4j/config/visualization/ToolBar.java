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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.opt4j.config.ExecutionEnvironment;
import org.opt4j.config.Icons;
import org.opt4j.config.ModuleSaver;
import org.opt4j.config.visualization.FileOperations.FileOperationsListener;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * The {@link ToolBar}.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
@Singleton
public class ToolBar extends JToolBar implements FileOperationsListener, Startupable {

	protected JButton execute;
	protected JButton save;
	protected JButton saveAs;
	protected JButton load;
	protected JButton showXML;

	protected final ExecutionEnvironment executionEnvironment;
	protected final FileOperations fileOperations;
	protected final SelectedModules selectedModules;

	/**
	 * Constructs a {@link ToolBar}.
	 * 
	 * @param executionEnvironment
	 *            the execution environment
	 * @param selectedModules
	 *            the selected modules
	 * @param fileOperations
	 *            the file operations
	 */
	@Inject
	public ToolBar(final ExecutionEnvironment executionEnvironment, final SelectedModules selectedModules,
			FileOperations fileOperations) {
		super();
		this.executionEnvironment = executionEnvironment;
		this.selectedModules = selectedModules;
		this.fileOperations = fileOperations;
	}

	/**
	 * Initializes the listeners.
	 */
	@Inject
	public void init() {
		fileOperations.addListener(this);
		setFloatable(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.gui.Startupable#startup()
	 */
	@Override
	public void startup() {
		execute = new JButton("Run ", Icons.getIcon(Icons.PLAY)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_R);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Set<Module> modules = new HashSet<Module>();
						modules.addAll(selectedModules);
						executionEnvironment.execute(modules);
					}
				});
			}
		};

		load = new JButton("Load ... ", Icons.getIcon(Icons.FOLDER)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_L);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fileOperations.load();
					}
				});
			}
		};
		save = new JButton("Save ", Icons.getIcon(Icons.DISK)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_S);
				setEnabled(false);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fileOperations.save();
					}
				});
			}
		};
		saveAs = new JButton("Save As ... ", Icons.getIcon(Icons.DISK)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_A);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						fileOperations.saveAs();
					}
				});
			}
		};

		showXML = new JButton("Show configuration", Icons.getIcon(Icons.XML)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_X);
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						ModuleSaver saver = new ModuleSaver();
						ClipboardFrame clipboardFrame = new ClipboardFrame(saver.toXMLString(selectedModules));
						clipboardFrame.pack();
						clipboardFrame.setVisible(true);
					}
				});
			}
		};

		add(execute);
		addSeparator();
		add(load);
		add(save);
		add(saveAs);
		addSeparator();
		add(showXML);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.conf.gui.FileOperations.FileOperationsListener#setCurrentFile
	 * (java.io.File)
	 */
	@Override
	public void setCurrentFile(File file) {
		save.setEnabled(file == null ? false : true);
	}
}
