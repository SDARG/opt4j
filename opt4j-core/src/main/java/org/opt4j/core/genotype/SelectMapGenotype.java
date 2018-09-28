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

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link SelectMapGenotype} selects for each key an element from a given
 * list.
 * </p>
 * <p>
 * Example problem: Choose for each of five balls ball<sub>1</sub>,
 * ball<sub>2</sub>, ball<sub>3</sub>, ball<sub>4</sub>, ball<sub>5</sub> a
 * color. Available colors are blue, green, and red.<br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * SelectMapGenotype&lt;Ball, Color&gt; genotype = new SelectMapGenotype&lt;Ball, Color&gt;(
 * 		Arrays.asList(ball1, ball2, ball3, ball4, ball5), Arrays.asList(Color.BLUE, Color.GREEN, Color.RED));
 * genotype.init(new Random());
 * </pre>
 * 
 * </blockquote> Example instance: [ball1=green, ball2=red, ball3=red,
 * ball4=blue, ball5=blue]<br/>
 * Example search space size: 3<sup>5</sup>
 * </p>
 * 
 * 
 * @author lukasiewycz
 * 
 * @param <K>
 *            the type of keys
 * @param <V>
 *            the type of elements
 */
public class SelectMapGenotype<K, V> extends IntegerGenotype implements MapGenotype<K, V> {

	private static final long serialVersionUID = 1L;

	protected final List<K> keys;
	protected final Map<K, List<V>> values;

	protected static class SelectBounds<O, P> implements Bounds<Integer> {
		protected List<O> list;
		protected Map<O, List<P>> map;

		public SelectBounds(List<O> list, Map<O, List<P>> map) {
			this.list = list;
			this.map = map;
			for (Entry<O, List<P>> entry : map.entrySet()) {
				if (entry.getValue().isEmpty()) {
					throw new IllegalArgumentException("Empty value map provided for key " + entry.getKey());
				}
			}
		}

		@Override
		public Integer getLowerBound(int index) {
			return 0;
		}

		@Override
		public Integer getUpperBound(int index) {
			return map.get(list.get(index)).size() - 1;
		}
	}

	/**
	 * Constructs a {@link SelectMapGenotype}.
	 * 
	 * @param keys
	 *            the keys
	 * @param values
	 *            the values
	 */
	public SelectMapGenotype(List<K> keys, Map<K, List<V>> values) {
		super(new SelectBounds<>(keys, values));
		if (!keys.containsAll(values.keySet()) || !values.keySet().containsAll(keys)) {
			throw new IllegalArgumentException("The provided list does not match the provided map");
		}
		if (new HashSet<>(keys).size() < keys.size()) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_NON_UNIQUE_KEYS);
		}
		this.keys = keys;
		this.values = values;
	}

	private static <K, V> Map<K, List<V>> toMap(List<K> keys, List<V> values) {
		if (new HashSet<>(keys).size() < keys.size()) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_NON_UNIQUE_KEYS);
		}
		Map<K, List<V>> map = new HashMap<>();
		for (K key : keys) {
			map.put(key, values);
		}
		return map;
	}

	/**
	 * Constructs a {@link SelectMapGenotype}. Here, each key has the same
	 * target list of element values.
	 * 
	 * @param keys
	 *            the keys
	 * @param values
	 *            the values
	 */
	public SelectMapGenotype(List<K> keys, List<V> values) {
		super(new SelectBounds<>(keys, toMap(keys, values)));
		this.keys = keys;
		this.values = toMap(keys, values);
	}

	/**
	 * Initialize this genotype with random values based on the size of the
	 * {@code key} set.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public void init(Random random) {
		super.init(random, keys.size());
	}

	/**
	 * Not supported. Use {@link SelectMapGenotype#init(Random)} instead.
	 * 
	 * @see org.opt4j.core.genotype.IntegerGenotype#init(java.util.Random, int)
	 */
	@Override
	public void init(Random random, int n) {
		throw new UnsupportedOperationException(MapGenotype.ERROR_MESSAGE_UNSUPPORTED_INIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(K key) {
		return keys.contains(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getIndexOf(java.lang.Object)
	 */
	@Override
	public int getIndexOf(K key) {
		if (!containsKey(key)) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_INVALID_KEY);
		}
		return keys.indexOf(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getValue(java.lang.Object)
	 */
	@Override
	public V getValue(K key) {
		int i = getIndexOf(key);
		int v = get(i);
		List<V> valueList = values.get(key);
		return valueList.get(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#setValue(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void setValue(K key, V value) {
		int i = getIndexOf(key);
		while (size() <= i) {
			add(bounds.getLowerBound(i));
		}
		List<V> valueList = values.get(key);
		if (!valueList.contains(value)) {
			throw new IllegalArgumentException(
					"The list provided for key " + key + " does not contain the value " + value);
		}
		int v = valueList.indexOf(value);
		set(i, v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends SelectMapGenotype> cstr = this.getClass().getConstructor(List.class, Map.class);
			return (G) cstr.newInstance(keys, values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		String s = "[";
		for (int i = 0; i < size(); i++) {
			K key = keys.get(i);
			int v = this.get(i);
			V value = values.get(key).get(v);
			s += key + "=" + value + ";";
		}
		return s + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getKeys()
	 */
	@Override
	public Collection<K> getKeys() {
		return Collections.unmodifiableList(keys);
	}
}
