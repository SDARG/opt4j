/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.config;

import java.util.Collection;

/**
 * The {@link EnumRequirement} is a {@link Requirement} that depends on a
 * enumeration valued {@link Property}.
 * 
 * @author lukasiewycz
 * 
 */
public class EnumRequirement implements Requirement {

	private final Property property;

	private final Collection<Object> elements;

	/**
	 * Constructs a {@link EnumRequirement}.
	 * 
	 * @param property
	 *            the monitored property
	 * @param elements
	 *            the allowed elements
	 */
	public EnumRequirement(Property property, Collection<Object> elements) {
		this.property = property;
		this.elements = elements;

		Class<?> type = property.getType();

		if (!type.isEnum()) {
			throw new IllegalArgumentException(property + " is not an enum.");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.Requirement#getProperty()
	 */
	@Override
	public Property getProperty() {
		return property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.conf.Requirement#isFulfilled()
	 */
	@Override
	public boolean isFulfilled() {
		if (property.isActive()) {
			Object obj = property.getValue();
			return elements.contains(obj);
		}
		return false;
	}

}
