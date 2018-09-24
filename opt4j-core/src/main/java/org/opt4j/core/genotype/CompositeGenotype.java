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

package org.opt4j.core.genotype;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opt4j.core.Genotype;

/**
 * The {@link CompositeGenotype} is a base class for {@link Genotype} classes
 * that consist of multiple {@link Genotype}s. The method
 * {@link CompositeGenotype#size()} returns the sum of the sizes of the
 * contained {@link Genotype}s.
 * <p>
 * A specific {@link CompositeGenotype} has to add each contained
 * {@link Genotype} by calling the method {@link #put(Object, Genotype)} where
 * {@link Object} is an arbitrary identifier.
 * <p>
 * Example:
 * 
 * <pre>
 * 	SpecificGenotype extends CompositeGenotype&lt;Integer, Genotype&gt; {
 * 		public void setDoubleVector(DoubleGenotype genotype) {
 * 			put(0, genotype);
 * 		}
 * 
 * 		public DoubleGenotype getDoubleVector() { 
 * 			return get(0);
 * 		}
 * 
 * 		public void setBinaryVector(BooleanGenotype genotype) {
 * 			put(1, genotype);
 * 		}
 * 
 * 		public BooleanGenotype getBinaryVector() {
 * 			return get(1); 
 * 		}
 * 	}
 * </pre>
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
		StringBuilder s = new StringBuilder("[");
		for (Entry<K, V> entry : this) {
			K key = entry.getKey();
			V value = entry.getValue();
			s.append(key).append("=").append(value).append(";");
		}
		return s.append("]").toString();
	}
}
