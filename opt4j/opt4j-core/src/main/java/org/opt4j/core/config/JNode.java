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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Wrapper for {@link org.w3c.dom.Node} elements. This class integrates Java 5
 * functionality with generics.
 * 
 * @author lukasiewycz
 * 
 */
public class JNode {

	protected final Node node;

	/**
	 * Creates a Wrapper {@link JNode} for a {@link Node} element.
	 * 
	 * @param node
	 *            the node to wrap
	 */
	public JNode(Node node) {
		this.node = node;
	}

	/**
	 * Constructs a new {@link JNode} for a {@link Document}.
	 * 
	 * @param document
	 *            the XML document
	 * @param name
	 *            the specified name of the node
	 */
	public JNode(Document document, String name) {
		node = document.createElement(name);
	}

	/**
	 * Retrieves the {@link Node} element.
	 * 
	 * @return the XML node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return node.getNodeName();
	}

	/**
	 * Returns a {@link Map} of all attributes.
	 * 
	 * @return a map of all attributes
	 */
	public Map<String, String> getAttributes() {

		Map<String, String> map = new HashMap<String, String>();

		try {

			NamedNodeMap attributes = node.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Node attribute = attributes.item(i);
				map.put(attribute.getNodeName(), attribute.getNodeValue());
			}

		} catch (ClassCastException e) {
		}

		return map;
	}

	/**
	 * Returns the value of an attribute.
	 * 
	 * @see #setAttribute
	 * @param attribute
	 *            the name of the attribute
	 * @return the value of the attribute
	 */
	public String getAttribute(String attribute) {

		try {
			Element element = (Element) node;
			return element.getAttribute(attribute);
		} catch (ClassCastException e) {
		}

		return null;

	}

	/**
	 * Returns {@code true} if the attribute is existent.
	 * 
	 * @param attribute
	 *            the name of the attribute
	 * @return the value of the attribute
	 */
	public boolean hasAttribute(String attribute) {

		try {
			Element element = (Element) node;
			if (element.hasAttribute(attribute)) {
				return true;
			}
		} catch (ClassCastException e) {
		}
		return false;

	}

	/**
	 * Returns the first child.
	 * 
	 * @return the first child
	 */
	public JNode getChild() {
		return new JNode(node.getFirstChild());
	}

	/**
	 * Returns the first child with a specified name.
	 * 
	 * @param name
	 *            the name of the child
	 * @return the first child with the specified name
	 */
	public JNode getChild(String name) {
		List<JNode> list = getChildren();

		for (JNode node : list) {
			if (node.getName().equalsIgnoreCase(name)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Returns all children.
	 * 
	 * @return a list of all children.
	 */
	public List<JNode> getChildren() {

		List<JNode> list = new LinkedList<JNode>();
		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			list.add(new JNode(child));
		}

		return list;
	}

	/**
	 * Returns all children with a specified name.
	 * 
	 * @param name
	 *            the name of the children
	 * @return a list of all children with the specified name
	 */
	public List<JNode> getChildren(String name) {

		List<JNode> list = getChildren();
		List<JNode> namedList = new LinkedList<JNode>();

		for (JNode node : list) {
			if (node.getName().equalsIgnoreCase(name)) {
				namedList.add(node);
			}
		}
		return namedList;
	}

	/**
	 * Returns the strings content.
	 * 
	 * @see #setText
	 * @return the strings content
	 */
	public String getText() {
		return node.getTextContent();
	}

	/**
	 * Returns {@code true} if this node has a strings content.
	 * 
	 * @return {@code true} if this node has a strings content
	 */
	public boolean hasText() {
		return (node.getTextContent() != null && !node.getTextContent().equals(""));
	}

	/**
	 * Sets the strings content.
	 * 
	 * @see #getText
	 * @param text
	 *            the strings content to set
	 */
	public void setText(String text) {
		node.setTextContent(text);
	}

	/**
	 * Sets an attribute to a value.
	 * 
	 * @see #getAttribute
	 * @param attribute
	 *            the name of the attribute
	 * @param value
	 *            the value
	 */
	public void setAttribute(String attribute, String value) {
		((Element) node).setAttribute(attribute, value);
	}

	/**
	 * Append a new {@link JNode} with a given name.
	 * 
	 * @param name
	 *            the specified name
	 * @return the appended JNode
	 */
	public JNode appendChild(String name) {
		Document document = node.getOwnerDocument();
		Node n = document.createElement(name);
		return appendChild(n);
	}

	/**
	 * Append an XML {@link Node}.
	 * 
	 * @param node
	 *            the XML node
	 * @return the appended JNode
	 */
	public JNode appendChild(Node node) {
		return appendChild(new JNode(node));
	}

	/**
	 * Append a {@link JNode}.
	 * 
	 * @param jNode
	 *            the node to append
	 * @return the appended JNode
	 */
	public JNode appendChild(JNode jNode) {
		this.node.appendChild(jNode.getNode());
		return jNode;
	}

	/**
	 * Returns the owner {@link Document}.
	 * 
	 * @return the owner XML document
	 */
	public Document getDocument() {
		return node.getOwnerDocument();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return node.toString();
	}
}
