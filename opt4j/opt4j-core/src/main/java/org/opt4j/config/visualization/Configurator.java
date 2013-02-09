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

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.opt4j.config.Task;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * The {@link Configurator} configures the modules and starts the specific
 * {@link ApplicationFrame}.
 * 
 * @author lukasiewycz
 * @see ApplicationFrame
 * 
 */
@Singleton
public class Configurator {

	/**
	 * Starts the {@link Configurator}.
	 * 
	 * @param args
	 *            specify one or more input configuration files to be loaded
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Configurator configurator = new Configurator();
		configurator.start(args);
	}

	/**
	 * Starts the {@link Configurator}.
	 * 
	 * @param args
	 *            the input configuration files to be loaded
	 * @throws ClassNotFoundException
	 */
	public void start(String[] args) throws ClassNotFoundException {
		String filename = null;
		Class<? extends Task> taskClass = null;
		if (args.length < 1) {
			throw new RuntimeException("Specify the task class");
		}

		String taskname = args[0];
		taskClass = Class.forName(taskname).asSubclass(Task.class);
		if (args.length > 1) {
			filename = args[1];
		}

		main(taskClass, filename);
	}

	/**
	 * Returns the module for the {@link Configurator}.
	 * 
	 * @param taskClass
	 *            the task class
	 * @return the module
	 */
	public Module getModule(final Class<? extends Task> taskClass) {
		Module module = new Module() {
			@Override
			public void configure(Binder b) {
				b.bind(Task.class).to(taskClass);
			}
		};
		return module;
	}

	/**
	 * Starts the {@link Configurator} with the specified task class and the
	 * file to be loaded.
	 * 
	 * @param taskClass
	 *            the task class
	 * @param filename
	 *            the filename of the configuration file to be loaded
	 */
	public void main(final Class<? extends Task> taskClass, final String filename) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Module module = getModule(taskClass);

		final Injector injector = Guice.createInjector(module);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ApplicationFrame frame = injector.getInstance(ApplicationFrame.class);
				frame.startup();
				if (filename != null) {
					File file = new File(filename);
					FileOperations fo = injector.getInstance(FileOperations.class);
					fo.load(file);
				}
			}
		});
	}

}
