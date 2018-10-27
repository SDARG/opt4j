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

package org.opt4j.core.start;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The {@link Parameters} is a class for the identification of types/classes of
 * a generic parameter.
 * 
 * @author lukasiewycz
 * 
 */
public class Parameters {

	/**
	 * Returns the class of a type.
	 * 
	 * @param type
	 *            the type
	 * @return the class
	 */
	public static Class<?> getClass(Type type) {
		if (type instanceof Class<?>) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) type).getRawType();
		} else {
			return null;
		}
	}

	/**
	 * Returns the type of the generic of the interface or class of an class for
	 * an object.
	 * 
	 * @param clazz
	 *            the interface with the undefined generic
	 * @param object
	 *            the current implementation of the interface
	 * @param variable
	 *            the identifier
	 * @return the implemented type of the generic
	 */
	public static Type getType(Class<?> clazz, Object object, String variable) {
		return getType(clazz, variable, getTypes(object.getClass()));
	}

	protected static Type getType(Class<?> clazz, String variable, Map<Type, ParameterizedType> map) {

		// get first
		ParameterizedType ptype = map.get(clazz);
		if (ptype == null) {
			return null;
		}

		Type type = null;
		for (int j = 0; j < ptype.getActualTypeArguments().length; j++) {
			TypeVariable<?> v = clazz.getTypeParameters()[j];
			if (v.getName().equals(variable)) {
				type = ptype.getActualTypeArguments()[j];
			}
		}

		while (type instanceof TypeVariable<?>) {
			TypeVariable<?> var = (TypeVariable<?>) type;
			ParameterizedType paramType = map.get(var.getGenericDeclaration());
			Class<?> freeType = (Class<?>) paramType.getRawType();

			int size = freeType.getTypeParameters().length;

			for (int j = 0; j < size; j++) {
				TypeVariable<?> f = freeType.getTypeParameters()[j];
				if (f.equals(var)) {
					type = paramType.getActualTypeArguments()[j];
					break;
				}
			}
		}

		return type;
	}

	protected static Map<Type, ParameterizedType> getTypes(Class<?> clazz) {
		Collection<Class<?>> classes = getAllClasses(clazz);
		Collection<ParameterizedType> paramTypes = getParameterizedTypes(classes);

		Map<Type, ParameterizedType> map = new HashMap<>();

		for (ParameterizedType p : paramTypes) {
			map.put(p.getRawType(), p);
		}

		return map;
	}

	protected static Collection<Class<?>> getAllClasses(final Class<?> clazz) {
		Collection<Class<?>> set = new HashSet<>();
		if (clazz != null && !clazz.equals(Object.class)) {
			set.add(clazz);

			Class<?> superclass = clazz.getSuperclass();
			set.addAll(getAllClasses(superclass));

			for (Class<?> inf : clazz.getInterfaces()) {
				set.addAll(getAllClasses(inf));
			}
		}
		return set;
	}

	protected static Collection<ParameterizedType> getParameterizedTypes(Collection<Class<?>> classes) {
		Collection<ParameterizedType> paramTypes = new HashSet<>();

		for (Class<?> clazz : classes) {

			Type type = clazz.getGenericSuperclass();

			if (type instanceof ParameterizedType) {
				ParameterizedType p = (ParameterizedType) type;
				paramTypes.add(p);
			}

			for (Type t : clazz.getGenericInterfaces()) {
				if (t instanceof ParameterizedType) {
					ParameterizedType p = (ParameterizedType) t;
					paramTypes.add(p);
				}
			}
		}

		return paramTypes;
	}

}
