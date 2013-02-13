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

		Map<Type, ParameterizedType> map = new HashMap<Type, ParameterizedType>();

		for (ParameterizedType p : paramTypes) {
			map.put(p.getRawType(), p);
		}

		return map;
	}

	protected static Collection<Class<?>> getAllClasses(final Class<?> clazz) {
		Collection<Class<?>> set = new HashSet<Class<?>>();
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
		Collection<ParameterizedType> paramTypes = new HashSet<ParameterizedType>();

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
