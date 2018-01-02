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
 

package org.opt4j.core.config;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Module;

/**
 * The {@link Starter} executes configuration files.
 * 
 * @author lukasiewycz.
 * 
 */
public class Starter {

	/**
	 * Start configuration files. Using multiple configuration files: Execute
	 * the {@link Task}s successively.
	 * 
	 * @param args
	 *            the configuration files
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Starter starter = new Starter();
		starter.execute(args);
	}

	/**
	 * Executor method.
	 * 
	 * @param args
	 *            the configuration files
	 * @throws Exception
	 */
	public void execute(String[] args) throws Exception {
		addPlugins();

		String taskname = args[0];
		Class<? extends Task> taskClass = Class.forName(taskname).asSubclass(Task.class);
		String[] filenames = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			filenames[i - 1] = args[i];
		}

		execute(taskClass, filenames);
	}

	/**
	 * Executor method.
	 * 
	 * @param taskClass
	 *            the class of the task to use
	 * @param filenames
	 *            the configuration files
	 * @throws Exception
	 */
	public void execute(Class<? extends Task> taskClass, String[] filenames) throws Exception {
		ModuleLoader loader = new ModuleLoader(new ModuleRegister(new ModuleAutoFinder()));

		for (String filename : filenames) {
			Set<Module> modules = new HashSet<Module>();
			modules.addAll(loader.load(filename));
			Task task = taskClass.newInstance();
			task.init(modules);
			task.call();
		}

	}

	/**
	 * Adds {@code jar} files to the classpath. Searches in folder
	 * {@code plugins}.
	 * 
	 * @return all found and added {@code jar} files
	 */
	public Collection<File> addPlugins() {

		List<File> files = new ArrayList<File>();

		try {

			List<String> dirs = new ArrayList<String>();
			dirs.add("plugins");
			dirs.add("../plugins");
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			URI uri = new URI(url.toString());
			File parent = new File(uri);
			while (parent.isFile()) {
				parent = parent.getParentFile();
			}

			for (String filename : dirs) {
				File plugins = new File(parent, filename);
				try {
					if (plugins.exists()) {
						for (File jar : plugins.listFiles()) {
							if (jar.getName().endsWith(".jar")) {
								ClassPath.addFile(jar);
								files.add(jar);
							}
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return files;

	}

}
