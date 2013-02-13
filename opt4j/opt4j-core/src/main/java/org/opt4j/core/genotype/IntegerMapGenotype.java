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
import java.util.List;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * The {@link IntegerMapGenotype} is a {@link IntegerGenotype} with the
 * {@link MapGenotype} functionality.
 * 
 * @author lukasiewycz
 * 
 * @param <K>
 *            the type of keys
 */
public class IntegerMapGenotype<K> extends IntegerGenotype implements MapGenotype<K, Integer> {

	protected final List<K> list;

	/**
	 * Constructs a {@link IntegerMapGenotype}.
	 * 
	 * @param list
	 *            the list of keys
	 * @param bounds
	 *            the lower and upper bounds
	 */
	public IntegerMapGenotype(List<K> list, Bounds<Integer> bounds) {
		super(bounds);
		this.list = list;
	}

	/**
	 * Constructs a {@link IntegerMapGenotype} with fixed bounds.
	 * 
	 * @param list
	 *            the list of keys
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 */
	public IntegerMapGenotype(List<K> list, int lowerBound, int upperBound) {
		super(lowerBound, upperBound);
		this.list = list;
	}

	/**
	 * Initialize this genotype with random values based on the size of the
	 * {@code list}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public void init(Random random) {
		super.init(random, list.size());
	}

	/**
	 * Not supported. Use {@link IntegerMapGenotype#init(Random)} instead.
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
		return list.contains(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getValue(java.lang.Object)
	 */
	@Override
	public Integer getValue(K key) {
		int i = list.indexOf(key);
		return get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#setValue(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void setValue(K key, Integer value) {
		int i = list.indexOf(key);
		while (size() <= i) {
			add(bounds.getLowerBound(i));
		}
		set(i, value);
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
			Constructor<? extends IntegerMapGenotype> cstr = this.getClass().getConstructor(List.class, Bounds.class);
			return (G) cstr.newInstance(list, bounds);
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
			K key = list.get(i);
			double value = this.get(i);
			s += key + "=" + value + ";";
		}
		return s + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getIndexOf(java.lang.Object)
	 */
	@Override
	public int getIndexOf(K key) {
		return list.indexOf(key);
	}

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getKeys()
	 */
	@Override
	public Collection<K> getKeys() {
		return Collections.unmodifiableList(list);
	}

}
