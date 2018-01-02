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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.google.inject.Module;

/**
 * Helper class for loading {@link PropertyModule} configurations from files or
 * retrieving these from XML {@link Node}s.
 * 
 * @author lukasiewycz
 * 
 */
public class ModuleLoader {

	protected final ModuleRegister moduleRegister;

	/**
	 * Constructs a {@link ModuleLoader}.
	 * 
	 * @param moduleRegister
	 *            the register of all found modules
	 */
	public ModuleLoader(ModuleRegister moduleRegister) {
		super();
		this.moduleRegister = moduleRegister;
	}

	/**
	 * Loads all modules from a file (as filename).
	 * 
	 * @param filename
	 *            the file (as filename)
	 * @return the modules
	 */
	public Collection<? extends Module> load(String filename) {
		File file = new File(filename);
		return load(file);
	}

	/**
	 * Loads all modules from a {@link File}.
	 * 
	 * @param file
	 *            the input file
	 * @return a list of the modules
	 */
	public Collection<? extends Module> load(File file) {

		List<Module> modules = new ArrayList<Module>();

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);

			modules.addAll(get(document.getFirstChild()));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return modules;
	}

	/**
	 * Loads all modules from an XML {@link Node}.
	 * 
	 * @param node
	 *            the XML node
	 * @return a list of the modules
	 */
	public Collection<? extends Module> get(Node node) {
		List<Module> modules = new ArrayList<Module>();

		JNode application = new JNode(node);

		for (JNode child : application.getChildren("module")) {
			String name = child.getAttribute("class");

			PropertyModule module = null;
			try {
				Class<? extends Module> clazz = Class.forName(name).asSubclass(Module.class);
				module = moduleRegister.get(clazz);
			} catch (ClassNotFoundException e) {
				System.err.println("Class " + name + " not found.");
			}

			if (module != null) {
				module.setConfiguration(child.getNode());
				modules.add(module.getModule());
			}
		}

		return modules;
	}

}
