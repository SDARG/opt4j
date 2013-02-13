/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

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

	protected File file = null;

	protected final FileChooser fileChooser;

	protected final ModuleRegister moduleRegister;

	protected final SelectedModules selectedModules;

	protected final Set<FileOperationsListener> listeners = new CopyOnWriteArraySet<FileOperationsListener>();

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
