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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.visualization.FileOperations.FileOperationsListener;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The {@link Menu}.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class Menu extends JMenuBar implements FileOperationsListener, Startupable {

	final JMenuItem load = new JMenuItem("Load ...", Icons.getIcon(Icons.FOLDER));

	final JMenuItem save = new JMenuItem("Save", Icons.getIcon(Icons.DISK));

	final JMenuItem saveAs = new JMenuItem("Save As ...", Icons.getIcon(Icons.DISK));

	final JMenuItem about = new JMenuItem("About");

	protected final FileOperations fileOperations;

	protected final Provider<ApplicationFrame> frame;

	protected final About aboutInfo;

	/**
	 * Constructs a {@link Menu}.
	 * 
	 * @param fileOperations
	 *            the file operations model
	 * @param about
	 *            the about information
	 * @param frame
	 *            the provider of the application frame
	 */
	@Inject
	public Menu(final FileOperations fileOperations, final About about, final Provider<ApplicationFrame> frame) {
		super();
		this.fileOperations = fileOperations;
		this.aboutInfo = about;
		this.frame = frame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.config.visualization.Startupable#startup()
	 */
	@Override
	public void startup() {

		JMenu file = new JMenu("File");
		JMenu etc = new JMenu("?");

		file.setMnemonic(KeyEvent.VK_F);

		add(file);
		add(etc);

		load.setMnemonic(KeyEvent.VK_L);
		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileOperations.load();
			}
		});

		save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileOperations.save();
			}
		});
		save.setEnabled(false);

		saveAs.setMnemonic(KeyEvent.VK_A);
		saveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fileOperations.saveAs();
			}
		});

		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = aboutInfo.getDialog(frame.get());
				dialog.pack();
				dialog.setVisible(true);
			}
		});

		file.add(load);
		file.add(save);
		file.add(saveAs);

		etc.add(about);
	}

	/**
	 * Initializes the listeners.
	 */
	@Inject
	public void init() {
		fileOperations.addListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.config.visualization.FileOperations.FileOperationsListener#
	 * setCurrentFile(java.io.File)
	 */
	@Override
	public void setCurrentFile(File file) {
		if (file != null) {
			save.setEnabled(true);
		} else {
			save.setEnabled(false);
		}
	}

}
