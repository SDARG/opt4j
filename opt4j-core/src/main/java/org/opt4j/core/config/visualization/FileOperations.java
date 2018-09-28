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

import java.io.File;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JFileChooser;

import org.opt4j.core.config.ModuleLoader;
import org.opt4j.core.config.ModuleRegister;
import org.opt4j.core.config.ModuleSaver;
import org.opt4j.core.config.PropertyModule;

import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * The {@link FileOperations}.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class FileOperations {

	protected File file = null;

	protected final FileChooser fileChooser;

	protected final ModuleRegister moduleRegister;

	protected final SelectedModules selectedModules;

	protected final Set<FileOperationsListener> listeners = new CopyOnWriteArraySet<>();

	/**
	 * The {@link FileOperationsListener}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public interface FileOperationsListener {

		/**
		 * Invoked if the current file is changed (on load or save).
		 * 
		 * @param file
		 *            the current file
		 */
		public void setCurrentFile(File file);
	}

	/**
	 * Constructs a {@link FileOperations}.
	 * 
	 * @param moduleRegister
	 *            all available modules
	 * @param selectedModules
	 *            all selected modules
	 * @param fileChooser
	 *            the FileChooser
	 */
	@Inject
	public FileOperations(ModuleRegister moduleRegister, SelectedModules selectedModules, FileChooser fileChooser) {
		super();
		this.moduleRegister = moduleRegister;
		this.selectedModules = selectedModules;
		this.fileChooser = fileChooser;
	}

	/**
	 * Sets the current file
	 * 
	 * @see #getFile
	 * @param file
	 *            the current file
	 */
	protected void setFile(File file) {
		this.file = file;
		for (FileOperationsListener listener : listeners) {
			listener.setCurrentFile(file);
		}
	}

	/**
	 * Returns the current file.
	 * 
	 * @see #setFile
	 * @return the current file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Opens the file chooser for loading the current configuration file.
	 */
	public void load() {
		JFileChooser fc = fileChooser.get();
		fc.setFileFilter(null);
		fc.setVisible(true);
		fc.setCurrentDirectory(getFile());

		int returnVal = fc.showDialog(null, "Load");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			load(fc.getSelectedFile());
		}
	}

	/**
	 * Opens the file chooser for saving the current configuration file.
	 */
	public void saveAs() {
		JFileChooser fc = fileChooser.get();
		fc.setFileFilter(null);
		fc.setVisible(true);
		fc.setCurrentDirectory(getFile());

		int returnVal = fc.showDialog(null, "Save");

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			save(fc.getSelectedFile());
		}
	}

	/**
	 * Loads the selected modules from the specified file.
	 * 
	 * @param file
	 *            the configuration file
	 */
	public void load(File file) {
		setFile(file);

		selectedModules.clear();

		ModuleLoader loader = new ModuleLoader(moduleRegister);

		Collection<? extends Module> modules = loader.load(file);
		for (Module module : modules) {
			PropertyModule m = new PropertyModule(module);
			selectedModules.add(m);
		}
		System.out.println("Loaded configuration from " + file);
	}

	/**
	 * Saves the current selected modules to the current file.
	 */
	public void save() {
		save(file);
	}

	/**
	 * Saves the current selected modules to the specified file.
	 * 
	 * @param file
	 *            the configuration file
	 */
	public void save(File file) {
		setFile(file);
		ModuleSaver saver = new ModuleSaver();

		Collection<PropertyModule> modules = selectedModules;

		System.out.println(modules);

		saver.save(file, modules);
		System.out.println("Saved configuration to " + file);
	}

	/**
	 * Adds a listener.
	 * 
	 * @see #removeListener
	 * @param listener
	 *            the listener to add
	 */
	public synchronized void addListener(FileOperationsListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes a listener.
	 * 
	 * @see #addListener
	 * @param listener
	 *            the listener to remove
	 */
	public synchronized void removeListener(FileOperationsListener listener) {
		listeners.remove(listener);
	}

}
