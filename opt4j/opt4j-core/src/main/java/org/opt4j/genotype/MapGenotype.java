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

package org.opt4j.genotype;

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

	/**
	 * Return all keys.
	 * 
	 * @return all keys
	 */
	public Collection<K> getKeys();

	/**
	 * Returns the value for the specified key.
	 * 
	 * @see #setValue
	 * @param key
	 *            the key
	 * @return the value
	 */
	public V getValue(K key);

	/**
	 * Sets the value for the specified key.
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
	 * Returns the index of the key.
	 * 
	 * @param key
	 *            the key
	 * @return the index
	 */
	public int getIndexOf(K key);

}
