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
