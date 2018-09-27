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

package org.opt4j.core.config.visualization;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JToolBar;

import org.opt4j.core.config.ExecutionEnvironment;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.ModuleSaver;
import org.opt4j.core.config.visualization.FileOperations.FileOperationsListener;

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
				addActionListener((ActionEvent e) -> {
					Set<Module> modules = new HashSet<>();
					modules.addAll(selectedModules);
					executionEnvironment.execute(modules);
				});
			}
		};

		load = new JButton("Load ... ", Icons.getIcon(Icons.FOLDER)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_L);
				addActionListener((ActionEvent e) -> fileOperations.load());
			}
		};
		save = new JButton("Save ", Icons.getIcon(Icons.DISK)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_S);
				setEnabled(false);
				addActionListener((ActionEvent e) -> fileOperations.save());
			}
		};
		saveAs = new JButton("Save As ... ", Icons.getIcon(Icons.DISK)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_A);
				addActionListener((ActionEvent e) -> fileOperations.saveAs());
			}
		};

		showXML = new JButton("Show configuration", Icons.getIcon(Icons.XML)) {
			{
				setFocusable(false);
				setMnemonic(KeyEvent.VK_X);
				addActionListener((ActionEvent e) -> {
					ModuleSaver saver = new ModuleSaver();
					ClipboardFrame clipboardFrame = new ClipboardFrame(saver.toXMLString(selectedModules));
					clipboardFrame.pack();
					clipboardFrame.setVisible(true);
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
