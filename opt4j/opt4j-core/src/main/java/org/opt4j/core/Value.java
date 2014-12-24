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
 * The {@link Value} represents the result for an {@link Objective}. Each
 * {@link Value} has to be {@link Comparable} and have a {@link Double}
 * representation which is used by some optimization algorithms. Thus, an
 * appropriate {@link Double} representation is not necessary if the
 * optimization algorithm does not require it.
 * 
 * @see DoubleValue
 * @see IntegerValue
 * @see Objectives#add(Objective, Value)
 * @author lukasiewycz
 * 
 * @param <V>
 *            the type of the value
 */
public interface Value<V> extends Comparable<Value<V>> {

	/**
	 * Returns the value.
	 * 
	 * @see #setValue
	 * @return the value
	 */
	public V getValue();

	/**
	 * Sets the value.
	 * 
	 * @see #getValue
	 * @param value
	 *            the value to set
	 */
	public void setValue(V value);

	/**
	 * Returns a double value.
	 * 
	 * @return double value
	 */
	public Double getDouble();

}
