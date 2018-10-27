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

package org.opt4j.core.config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Multi;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * The {@link PropertyModule} is a decorator for a {@link Module} that enables
 * property methods.
 * 
 * @author lukasiewycz
 * 
 */
@Ignore
@SuppressWarnings("serial")
public final class PropertyModule implements Module, Serializable, Comparable<PropertyModule> {

	protected final Module module;

	protected final List<Property> properties = new ArrayList<>();

	protected static int c = 0;

	public final int id;

	/**
	 * Converts a String to an element of a given enumeration.
	 * 
	 * @param name
	 *            the name of the element
	 * @param type
	 *            the enumeration type
	 * @return the element of the enumeration
	 */
	@SuppressWarnings("rawtypes")
	public static Object toEnumElement(String name, Class<? extends Enum> type) {
		for (Enum e : type.getEnumConstants()) {
			if (e.name().equalsIgnoreCase(name)) {
				return e;
			}
		}
		return null;
	}

	/**
	 * Constructs a {@link PropertyModule} that decorates a {@link Module}.
	 * 
	 * @param module
	 *            the decorated module
	 */
	public PropertyModule(Module module) {
		super();

		synchronized (this) {
			id = c++;
		}

		this.module = module;

		if (module instanceof PropertyModule) {
			throw new IllegalArgumentException("PropertyModule can not decorate a PropertyModule");
		}

		// search all bean methods and their annotations
		Map<String, Method> getters = new HashMap<>();
		Map<String, Method> setters = new HashMap<>();
		Map<String, Class<?>> types = new HashMap<>();
		Map<String, Collection<Annotation>> annotations = new HashMap<>();

		for (Method method : module.getClass().getMethods()) {
			String name = method.getName();

			boolean get = name.startsWith("get");
			boolean is = name.startsWith("is");

			try {

				if (get || is) {

					if (get) {
						name = name.substring(3);
					} else if (is) {
						name = name.substring(2);
					}

					// this is a get method, search for a corresponding setter
					Class<?> type = method.getReturnType();
					Method setter = module.getClass().getMethod("set" + name, type);

					// first letter to lower case
					char[] letters = name.toCharArray();
					letters[0] = Character.toLowerCase(letters[0]);
					name = new String(letters);

					Collection<Annotation> a = new HashSet<>();

					Class<?> clazz = module.getClass();
					while (!clazz.equals(Object.class)) {
						try {
							Field field = clazz.getDeclaredField(name);
							for (Annotation annotation : field.getAnnotations()) {
								a.add(annotation);
							}
							break;
						} catch (NoSuchFieldException e) {
						}
						clazz = clazz.getSuperclass();
					}

					for (Annotation annotation : method.getAnnotations()) {
						a.add(annotation);
					}
					for (Annotation annotation : setter.getAnnotations()) {
						a.add(annotation);
					}

					getters.put(name, method);
					setters.put(name, setter);
					types.put(name, type);
					annotations.put(name, a);
				}

			} catch (SecurityException e) {
				// if the setter is not public
			} catch (NoSuchMethodException e) {
				// if the setter does not exist
			}
		}

		// construct the properties
		for (String name : getters.keySet()) {
			Method getter = getters.get(name);
			Method setter = setters.get(name);
			Class<?> type = types.get(name);
			Collection<Annotation> anno = annotations.get(name);

			Property property = new Property(module, name, type, getter, setter, anno);
			properties.add(property);
		}

		// interpret the annotations of the properties
		for (String name : getters.keySet()) {
			Collection<Annotation> a = annotations.get(name);
			Property property = getProperty(name);

			for (Annotation annotation : a) {

				if (annotation instanceof Info) {

					Info info = (Info) annotation;
					property.setInfo(info.value());

				} else if (annotation instanceof Order) {

					Order order = (Order) annotation;
					property.setOrder(order.value());

				} else if (annotation instanceof Required) {

					Required required = (Required) annotation;

					Property p = getProperty(required.property());
					if (p == null) {
						throw new IllegalArgumentException("Unknown property " + required.property() + " in annotation "
								+ required + " in module " + this.getClass());
					}

					Class<?> type = p.getType();

					if (type.isEnum()) {

						String[] values = required.elements();
						Collection<Object> elements = new HashSet<>();

						for (String value : values) {
							elements.add(toEnumElement(value, type.asSubclass(Enum.class)));
						}

						Requirement requirement = new EnumRequirement(p, elements);
						property.addRequirement(requirement);

					} else if (type.equals(Boolean.TYPE)) {

						boolean value = required.value();

						Requirement requirement = new BooleanRequirement(p, value);
						property.addRequirement(requirement);

					}

				}
			}
		}

		// sort the properties
		final Map<Property, List<Property>> hierarchy = new HashMap<>();

		for (Property property : properties) {
			Property parent = null;
			for (Requirement requirement : property.getRequirements()) {
				parent = requirement.getProperty();
			}
			assert (parent != property);

			List<Property> level = hierarchy.get(parent);
			if (level == null) {
				level = new ArrayList<>();
				hierarchy.put(parent, level);
			}
			level.add(property);
		}

		final class PropertyComparator implements Comparator<Property> {
			@Override
			public int compare(final Property o1, final Property o2) {
				Integer value1 = o1.getOrder();
				Integer value2 = o2.getOrder();

				if (!value1.equals(value2)) {
					return value1.compareTo(value2);
				}

				String s1 = o1.getName();
				String s2 = o2.getName();
				return s1.compareTo(s2);
			}
		}

		for (List<Property> level : hierarchy.values()) {
			Collections.sort(level, new PropertyComparator());
		}

		properties.clear();
		LinkedList<Property> added = new LinkedList<>();
		added.add(null);

		while (!added.isEmpty()) {
			Property property = added.pop();
			if (hierarchy.containsKey(property)) {
				List<Property> level = hierarchy.get(property);
				if (property == null) {
					properties.addAll(level);
				} else {
					int index = properties.indexOf(property);
					properties.addAll(index + 1, level);
				}
				added.addAll(level);
			}
		}

		// remove ignored properties
		for (Iterator<Property> it = properties.iterator(); it.hasNext();) {
			Property property = it.next();
			Ignore ignore = property.getAnnotation(Ignore.class);
			if (ignore != null) {
				it.remove();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.Module#configure(com.google.inject.Binder)
	 */
	@Override
	public void configure(Binder binder) {
		module.configure(binder);
	}

	/**
	 * Returns the decorated {@link Module}.
	 * 
	 * @return the module
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * Returns the {@link Property} with the specified {@code name}.
	 * 
	 * @param name
	 *            the name of the property
	 * @return the property with the specified {@code name}
	 */
	public Property getProperty(String name) {
		for (Property property : properties) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}

	/**
	 * Returns the properties.
	 * 
	 * @return the properties
	 */
	public List<Property> getProperties() {
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Module " + module.getClass().getName() + " ";
		for (Property property : properties) {
			s += property.getName() + "=\"" + property.getValue() + "\" ";
		}

		return s;
	}

	/**
	 * Configures a {@link PropertyModule} with an XML {@link Node}.
	 * 
	 * @see #getConfiguration
	 * @param node
	 *            the configuration as XML node
	 */
	public void setConfiguration(Node node) {
		JNode xode = new JNode(node);

		Map<String, String> values = new HashMap<>();

		List<JNode> propertyNodes = xode.getChildren("property");
		for (JNode propertyNode : propertyNodes) {
			String key = propertyNode.getAttribute("name");
			String value = null;
			if (propertyNode.hasText()) {
				value = propertyNode.getText();
			} else if (propertyNode.hasAttribute("value")) {
				value = propertyNode.getAttribute("value");
			}
			values.put(key, value);
		}

		for (Property property : properties) {
			String key = property.getName();
			if (values.containsKey(key)) {
				String value = values.get(key);
				try {
					property.setValue(value);
				} catch (InvocationTargetException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Returns the configuration of the {@link PropertyModule} as XML
	 * {@link Node}.
	 * 
	 * @see #setConfiguration
	 * @param document
	 *            the XML document
	 * @return the configuration as XML node
	 */
	public Node getConfiguration(Document document) {
		JNode node = new JNode(document, "module");
		node.setAttribute("class", module.getClass().getName());

		for (Property property : properties) {
			try {
				JNode p = node.appendChild("property");

				String name = property.getName();
				Object value = property.getValue();

				p.setAttribute("name", name);
				if (value != null) {
					if (value instanceof Class<?>) {
						p.setText(((Class<?>) value).getCanonicalName());
					} else {
						p.setText(value.toString());
					}
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}
		return node.getNode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PropertyModule clone() {
		try {
			Module module = this.module.getClass().newInstance();
			PropertyModule propertyModule = new PropertyModule(module);
			for (Property p : getProperties()) {
				Property property = propertyModule.getProperty(p.getName());
				property.setValue(p.getValue());
			}
			return propertyModule;
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Writes the object information to the {@link ObjectOutputStream}.
	 * 
	 * @param out
	 *            the output stream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(module.getClass().toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(PropertyModule other) {
		Class<? extends Module> clazz1 = this.getModule().getClass();
		Class<? extends Module> clazz2 = other.getModule().getClass();

		String classname1 = clazz1.getName();
		String classname2 = clazz2.getName();

		if (!clazz1.equals(clazz2)) {
			return classname1.compareTo(classname2);
		} else {
			Multi multi = clazz1.getAnnotation(Multi.class);
			if (multi == null) {
				return 0;
			} else {
				return this.id - other.id;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return module.getClass().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PropertyModule other = (PropertyModule) obj;
		return this.compareTo(other) == 0;
	}

}
