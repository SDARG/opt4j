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
