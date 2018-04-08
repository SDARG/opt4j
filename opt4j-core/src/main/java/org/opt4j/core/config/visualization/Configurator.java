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

import java.io.File;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.opt4j.core.config.Task;

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
			throw new ClassNotFoundException("Specify the task class");
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
