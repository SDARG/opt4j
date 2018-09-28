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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Module;

/**
 * The {@link Property} contains information about a single property of a
 * {@link PropertyModule}.
 * 
 * @author lukasiewycz
 * 
 */
public class Property {

	protected final Module module;

	protected final String name;

	protected final Class<?> type;

	protected final Method getter;

	protected final Method setter;

	protected String info = "";

	protected int order = 10000;

	protected final Collection<Requirement> requirements = new ArrayList<>();

	protected final Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();

	/**
	 * Constructs a {@link Property} .
	 * 
	 * @param module
	 *            the module
	 * @param name
	 *            the name
	 * @param type
	 *            the type
	 * @param getter
	 *            the getter method
	 * @param setter
	 *            the setter method
	 * 
	 * @param annotations
	 *            the annotations
	 */
	public Property(Module module, String name, Class<?> type, Method getter, Method setter,
			Iterable<Annotation> annotations) {
		super();
		this.module = module;
		this.name = name;
		this.type = type;
		this.getter = getter;
		this.setter = setter;

		for (Annotation annotation : annotations) {
			Class<? extends Annotation> clazz = annotation.annotationType();
			this.annotations.put(clazz, annotation);
		}
	}

	/**
	 * Returns the info.
	 * 
	 * @see #setInfo
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Sets the info.
	 * 
	 * @see #getInfo
	 * @param info
	 *            the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Returns the order.
	 * 
	 * @see #setOrder
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order.
	 * 
	 * @see #getOrder
	 * @param order
	 *            the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Returns the getter method.
	 * 
	 * @return the getter
	 */
	public Method getGetter() {
		return getter;
	}

	/**
	 * Returns the setter method.
	 * 
	 * @return the setter
	 */
	public Method getSetter() {
		return setter;
	}

	/**
	 * Adds a {@link Requirement}.
	 * 
	 * @param requirement
	 *            the requirement to add
	 */
	public void addRequirement(Requirement requirement) {
		requirements.add(requirement);
	}

	/**
	 * Returns {@code true} if the property is active, i.e., all
	 * {@link Requirement}s must be fulfilled.
	 * 
	 * @return {@code true} if the property is active
	 */
	public boolean isActive() {

		for (Requirement requirement : requirements) {
			if (!requirement.isFulfilled()) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Returns the requirements.
	 * 
	 * @return the requirements
	 */
	public Collection<Requirement> getRequirements() {
		return requirements;
	}

	/**
	 * Returns the value of the property.
	 * 
	 * @see #setValue
	 * @return the value of the property
	 */
	public Object getValue() {
		try {
			return getter.invoke(module);
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
		return null;
	}

	/**
	 * Sets the value of the property.
	 * 
	 * @see #getValue
	 * @param value
	 *            the value to set
	 * @throws InvocationTargetException
	 *             thrown if the value cannot be assigned
	 */
	public void setValue(Object value) throws InvocationTargetException {
		setValueObject(value);
	}

	/**
	 * Sets the value of the property. The property has first to be converted to
	 * the corresponding type.
	 * 
	 * @param value
	 *            the value to set
	 * @throws InvocationTargetException
	 *             thrown if the value cannot be assigned
	 */
	public void setValue(String value) throws InvocationTargetException {
		Class<?> type = this.type;
		String valueToSet = value;

		if (type.equals(Integer.TYPE)) {
			type = Integer.class;
		} else if (type.equals(Long.TYPE)) {
			type = Long.class;
		} else if (type.equals(Double.TYPE)) {
			type = Double.class;
		} else if (type.equals(Float.TYPE)) {
			type = Float.class;
		} else if (type.equals(Boolean.TYPE)) {
			type = Boolean.class;
		} else if (type.equals(Byte.TYPE)) {
			type = Byte.class;
		} else if (type.equals(Short.TYPE)) {
			type = Short.class;
		} else if (type.equals(String.class) && valueToSet == null) {
			valueToSet = "";
		}

		Object object = null;
		try {
			if (Character.TYPE.equals(type) || Character.class.equals(type)) {
				object = valueToSet.toCharArray()[0];
			} else if (type.isEnum()) {
				object = PropertyModule.toEnumElement(valueToSet, type.asSubclass(Enum.class));
			} else if (type.equals(Class.class)) {
				if (valueToSet != null && !valueToSet.equals("")) {
					Class<?> c = Class.forName(valueToSet);

					Type gtype = getter.getGenericReturnType();
					Type actual = getEnclosingType(gtype);

					if (isAssignable(actual, c)) {
						object = c;
					} else {
						System.err.println("Can not assign " + c + " to Class<" + actual + ">");
					}
				}
			} else {
				// for Byte/Integer/Long/Float/Double/String/Boolean
				// and all objects that have a constructor that accepts one
				// string
				Constructor<?> constructor = type.getConstructor(String.class);
				object = constructor.newInstance(valueToSet.trim());
			}
		} catch (Exception e) {
			Throwable t = e;
			while (t.getCause() != null) {
				t = t.getCause();
			}
			String message = t.getLocalizedMessage();

			throw new InvocationTargetException(e,
					"Failed assignment: module=" + module.getClass().getName() + " property=\"" + getName()
							+ "\" value=\"" + valueToSet + "\" (" + t.getClass().getName()
							+ (message != null ? ": " + message : "") + ")");
		}
		setValueObject(object);
	}

	/**
	 * Returns {@code true} if the property represents a number.
	 * 
	 * @return {@code true} if the property represents a number
	 */
	public boolean isNumber() {
		boolean isNumber = false;
		if (type.equals(Integer.TYPE)) {
			isNumber = true;
		} else if (type.equals(Long.TYPE)) {
			isNumber = true;
		} else if (type.equals(Double.TYPE)) {
			isNumber = true;
		} else if (type.equals(Float.TYPE)) {
			isNumber = true;
		} else if (type.equals(Byte.TYPE)) {
			isNumber = true;
		} else if (type.equals(Short.TYPE)) {
			isNumber = true;
		}
		return isNumber;
	}

	/**
	 * Returns the enclosing actual type of a class.
	 * 
	 * @param gtype
	 *            the type of the class
	 * @return the enclosing actual type
	 */
	private Type getEnclosingType(Type gtype) {
		if (gtype instanceof ParameterizedType) {
			ParameterizedType ptype = (ParameterizedType) gtype;
			Type rtype = ptype.getRawType();
			if (rtype.equals(Class.class)) {
				Type actual = ptype.getActualTypeArguments()[0];
				return actual;
			}
		}
		return null;
	}

	/**
	 * Verifies if a class is assignable to a type.
	 * 
	 * @param type
	 *            the type
	 * @param clazz
	 *            the class
	 * @return {@code true} if assignable
	 */
	private boolean isAssignable(Type type, Class<?> clazz) {
		if (type instanceof WildcardType) {
			WildcardType wildcard = (WildcardType) type;
			for (Type lower : wildcard.getLowerBounds()) {
				if (lower instanceof Class<?>) {
					Class<?> lowerClass = (Class<?>) lower;
					if (!clazz.isAssignableFrom(lowerClass)) {
						return false;
					}
				}
			}
			for (Type upper : wildcard.getUpperBounds()) {
				if (upper instanceof Class<?>) {
					Class<?> upperClass = (Class<?>) upper;
					if (!upperClass.isAssignableFrom(clazz)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Sets the value of the property.
	 * 
	 * @param value
	 *            the value to set
	 * @throws InvocationTargetException
	 *             thrown if the value cannot be assigned
	 */
	protected void setValueObject(Object value) throws InvocationTargetException {
		try {
			setter.invoke(module, value);
		} catch (Exception e) {
			throw new InvocationTargetException(e);
		}
	}

	/**
	 * Returns the annotations of this property.
	 * 
	 * @return the annotations
	 */
	public Collection<Annotation> getAnnotations() {
		return annotations.values();
	}

	/**
	 * Returns the annotation of the specified class or null if not existent.
	 * 
	 * @param <A>
	 *            the annotation type
	 * @param clazz
	 *            the specific annotation class
	 * @return the annotation
	 */
	@SuppressWarnings("unchecked")
	public <A extends Annotation> A getAnnotation(Class<? extends A> clazz) {
		return (A) annotations.get(clazz);
	}

}
