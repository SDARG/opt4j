/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */
package org.opt4j.core.genotype;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * SelectMapGenotype&lt;Ball, Color&gt; genotype = new SelectMapGenotype&lt;Ball, Color&gt;(Arrays.asList(ball1, ball2, ball3, ball4,
 * 		ball5), Arrays.asList(Color.BLUE, Color.GREEN, Color.RED));
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

	protected final List<K> keys;
	protected final Map<K, List<V>> values;

	protected static class SelectBounds<O, P> implements Bounds<Integer> {
		protected List<O> list;
		protected Map<O, List<P>> map;

		public SelectBounds(List<O> list, Map<O, List<P>> map) {
			this.list = list;
			this.map = map;
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
		super(new SelectBounds<K, V>(keys, values));
		this.keys = keys;
		this.values = values;
	}

	private static <K, V> Map<K, List<V>> toMap(List<K> keys, List<V> values) {
		Map<K, List<V>> map = new HashMap<K, List<V>>();
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
		super(new SelectBounds<K, V>(keys, toMap(keys, values)));
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
		throw new UnsupportedOperationException("Use method init(Random) instead");
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
		assert v <= getUpperBound(i);
		assert v >= getLowerBound(i);
		List<V> valueList = values.get(key);

		assert valueList.size() > v : "index " + v + " unavailable for list of key " + key + ": " + valueList;
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
		int i = keys.indexOf(key);
		while (size() <= i) {
			add(bounds.getLowerBound(i));
		}

		List<V> valueList = values.get(key);
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

	private static final long serialVersionUID = 1L;

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
