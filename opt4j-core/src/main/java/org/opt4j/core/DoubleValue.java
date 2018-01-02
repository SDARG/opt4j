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
 

package org.opt4j.core;

/**
 * The {@link DoubleValue} is a {@link Value} with a {@link Double} as value.
 * The {@link Objectives} contains a convenience method that allows to add a
 * {@link DoubleValue} without creating a new instance:
 * {@link Objectives#add(Objective, double)}.
 * 
 * @see Objectives#add(Objective, double)
 * @author lukasiewycz
 * 
 */
public class DoubleValue implements Value<Double> {

	protected Double value = null;

	/**
	 * Constructs a {@link DoubleValue}.
	 * 
	 * @param value
	 *            the specified value
	 */
	public DoubleValue(Double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Value#getDouble()
	 */
	@Override
	public Double getDouble() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Value#getValue()
	 */
	@Override
	public Double getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Value#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Double value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Value<Double> arg) {
		if (arg == null) {
			return -1;
		}
		Double other = arg.getValue();
		if (value == null) {
			if (other == null) {
				return 0;
			}
			return 1;
		} else if (other == null) {
			return -1;
		}
		return value.compareTo(other);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
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
		if (!(obj instanceof DoubleValue)) {
			return false;
		}
		DoubleValue other = (DoubleValue) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value == null ? "null" : value.toString();
	}

}
