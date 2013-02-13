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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.inject.Module;

/**
 * Helper class for saving the {@link PropertyModule} configurations or adding
 * these to XML {@link Node}s.
 * 
 * @author lukasiewycz
 * 
 */
public class ModuleSaver {

	/**
	 * Save the module configurations to a file (as filename).
	 * 
	 * @param filename
	 *            the name of the file
	 * @param modules
	 *            the modules
	 * @return {@code true} if successful
	 */
	public boolean save(String filename, Collection<? extends Module> modules) {
		File file = new File(filename);
		return save(file, modules);
	}

	/**
	 * Save the module configurations to a {@link File}.
	 * 
	 * @param file
	 *            the file
	 * @param modules
	 *            the modules
	 * @return {@code true} if successful
	 */
	public boolean save(File file, Collection<? extends Module> modules) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			return write(new FileOutputStream(file), modules);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Save the modules configuration to an {@link OutputStream}.
	 * 
	 * @param out
	 *            the output stream
	 * @param modules
	 *            the modules
	 * @return {@code true} if successful
	 */
	public boolean write(OutputStream out, Collection<? extends Module> modules) {
		try {
			Document document = getEmptyDocument();

			Node configuration = document.createElement("configuration");
			document.appendChild(configuration);
			for (Module module : modules) {
				configuration.appendChild(getNode(module, document));
			}
			StreamResult result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
			DOMSource source = new DOMSource(document);
			getStandardTransformer().transform(source, result);

			return true;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Save the module configuration to an {@link OutputStream}.
	 * 
	 * @param out
	 *            the output stream}
	 * @param module
	 *            the module
	 * @return {@code true} if successful
	 */
	public boolean write(OutputStream out, Module module) {
		try {
			Document document = getEmptyDocument();
			document.appendChild(getNode(module, document));
			StreamResult result = new StreamResult(new OutputStreamWriter(out, "utf-8"));
			DOMSource source = new DOMSource(document);
			getStandardTransformer().transform(source, result);
			return true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Returns the xml representation of the {@link Module}.
	 * 
	 * @param module
	 *            the module
	 * @return the xml representation
	 */
	public String toXMLString(Module module) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		write(out, module);
		return out.toString();
	}

	/**
	 * Returns the xml representation of the {@link Module}s.
	 * 
	 * @param modules
	 *            the modules
	 * @return the xml representation
	 */
	public String toXMLString(Collection<? extends Module> modules) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		write(out, modules);
		return out.toString();
	}

	protected Document getEmptyDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		return document;
	}

	protected Transformer getStandardTransformer() throws TransformerConfigurationException {
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute("indent-number", 2);
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		return transformer;
	}

	protected Node getNode(Module module, Document document) {
		PropertyModule pModule;
		if (module instanceof PropertyModule) {
			pModule = (PropertyModule) module;
		} else {
			pModule = new PropertyModule(module);
		}
		Node node = pModule.getConfiguration(document);
		return node;
	}

}
