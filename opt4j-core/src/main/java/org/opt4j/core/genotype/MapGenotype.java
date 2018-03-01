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

package org.opt4j.core.genotype;

import java.util.Collection;
import java.util.Map;

/**
 * The {@link MapGenotype} extends a {@link org.opt4j.core.Genotype} with
 * {@link Map} functionalities.
 * 
 * @author lukasiewycz
 * 
 * @param <K>
 *            the type of keys
 * @param <V>
 *            the type of values
 */
public interface MapGenotype<K, V> {

	static final String ERROR_MESSAGE_NON_UNIQUE_KEYS = "The provided key objects have to be unique";
	static final String ERROR_MESSAGE_INVALID_KEY = "Invalid key";
	static final String ERROR_MESSAGE_OUT_OF_BOUNDS = "The provided value does not lie within the bounds for the provided key";
	static final String ERROR_MESSAGE_UNSUPPORTED_INIT = "Use method init(Random) instead";

	/**
	 * Return all keys.
	 * 
	 * @return all keys
	 */
	public Collection<K> getKeys();

	/**
	 * Returns the value for the specified key. Throws an exception if the key
	 * is not contained.
	 * 
	 * @see #setValue
	 * @param key
	 *            the key
	 * @return the value
	 */
	public V getValue(K key);

	/**
	 * Sets the value for the specified key. Throws an exception if the key is
	 * not contained.
	 * 
	 * @see #getValue
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void setValue(K key, V value);

	/**
	 * Returns {@code true} if the key is contained.
	 * 
	 * @param key
	 *            the key
	 * @return {@code true} if the key is contained
	 */
	public boolean containsKey(K key);

	/**
	 * Returns the index of the key. Throws an exception if the key is not
	 * contained.
	 * 
	 * @param key
	 *            the key
	 * @return the index
	 */
	public int getIndexOf(K key);

}
