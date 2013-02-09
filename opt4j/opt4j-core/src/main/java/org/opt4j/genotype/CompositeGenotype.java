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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link CompositeGenotype} is a base class for {@link Genotype} classes
 * that consist of multiple {@link Genotype}s. The method
 * {@link CompositeGenotype#size()} returns the sum of the sizes of the
 * contained {@link Genotype}s.
 * </p>
 * A specific {@link CompositeGenotype} has to add each contained
 * {@link Genotype} by calling the method {@link #put(Object, Genotype)} where
 * {@link Object} is an arbitrary identifier.
 * 
 * <p>
 * Example:
 * 
 * <pre>
 * 	SpecificGenotype extends CompositeGenotype&lt;Integer, Genotype&gt; {
 * 		public void setDoubleVector(DoubleGenotype genotype){
 * 			put(0, genotype);
 * 	}
 * 		public DoubleGenotype getDoubleVector(){ 
 * 			return get(0);
 * 	}
 * 
 * 		public void setBinaryVector(BooleanGenotype genotype){
 * 			put(1, genotype);
 * 		}
 * 		public BooleanGenotype getDoubleVector(){
 * 			return get(1); 
 * 		}
 * }
 * </pre>
 * 
 * </p>
 * 
 * @param <K>
 *            the type of key for the mapping
 * @param <V>
 *            the type of {@link Genotype}
 * 
 * @author lukasiewycz
 */
public class CompositeGenotype<K, V extends Genotype> implements Genotype, Iterable<Entry<K, V>> {

	protected final Map<K, V> map = new HashMap<K, V>();

	/**
	 * Constructs a {@link CompositeGenotype}.
	 */
	public CompositeGenotype() {
		super();
	}

	/**
	 * Constructs a {@link CompositeGenotype} with values from a given map.
	 * 
	 * @param map
	 *            initial values
	 */
	public CompositeGenotype(Map<K, V> map) {
		this();
		this.map.putAll(map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#size()
	 */
	@Override
	public int size() {
		int sum = 0;
		for (Genotype element : map.values()) {
			sum += element.size();
		}
		return sum;
	}

	/**
	 * Returns the {@link Genotype} for the given key with an implicit cast to
	 * the specific {@link Genotype} type.
	 * 
	 * @param <G>
	 *            the type of genotype
	 * @param key
	 *            the key
	 * @return the addressed genotype
	 */
	@SuppressWarnings("unchecked")
	public <G> G get(Object key) {
		V value = map.get(key);
		return (G) value;
	}

	/**
	 * Adds a {@code key}, {@code value} pair.
	 * 
	 * @param key
	 *            the key
	 * @param value
	 *            the value ({@code Genotype})
	 */
	public void put(K key, V value) {
		map.put(key, value);
	}

	/**
	 * Removes all {@code key}, {@code value} pairs.
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * Returns all {@code keys}.
	 * 
	 * @return all {@code keys}
	 */
	public Set<K> keySet() {
		return map.keySet();
	}

	/**
	 * Returns all {@code values} which are the contained {@link Genotype}
	 * objects.
	 * 
	 * @return all {@code values}
	 */
	public Collection<V> values() {
		return map.values();
	}

	/**
	 * Returns the {@link Iterator} over the {@link Entry} pairs.
	 * 
	 * @return the iterator over the entry pairs
	 */
	@Override
	public Iterator<Entry<K, V>> iterator() {
		return map.entrySet().iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <G extends Genotype> G newInstance() {
		try {
			return (G) this.getClass().newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "[";
		for (Entry<K, V> entry : this) {
			K key = entry.getKey();
			V value = entry.getValue();
			s += key + "=" + value + ";";
		}
		return s + "]";
	}
}
