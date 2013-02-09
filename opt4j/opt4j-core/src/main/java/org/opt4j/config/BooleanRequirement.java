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

/**
 * The {@link BooleanRequirement} is a {@link Requirement} that depends on a
 * boolean valued {@link Property}.
 * 
 * @author lukasiewycz
 * 
 */
public class BooleanRequirement implements Requirement {

	private final Boolean value;

	private final Property property;

	/**
	 * Constructs a {@link BooleanRequirement}.
	 * 
	 * @param property
	 *            the monitored property
	 * @param value
	 *            the boolean value
	 */
	public BooleanRequirement(Property property, boolean value) {
		this.property = property;
		this.value = value;

		Class<?> type = property.getType();

		if (!type.equals(Boolean.TYPE)) {
			throw new IllegalArgumentException(property + " is not a boolean property.");
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
			return value.equals(obj);
		}
		return false;
	}

}
