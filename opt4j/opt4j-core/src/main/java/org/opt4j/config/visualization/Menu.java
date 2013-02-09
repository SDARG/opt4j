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

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.opt4j.config.Icons;
import org.opt4j.config.visualization.FileOperations.FileOperationsListener;

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
