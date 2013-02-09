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

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * The {@link DoubleMapGenotype} is a {@link DoubleGenotype} with the
 * {@link MapGenotype} functionality.
 * 
 * @author lukasiewycz
 * 
 * @param <K>
 *            the type of keys
 */
public class DoubleMapGenotype<K> extends DoubleGenotype implements MapGenotype<K, Double> {

	protected final List<K> keys;

	/**
	 * Constructs a {@link DoubleMapGenotype}.
	 * 
	 * @param keys
	 *            the list of keys
	 * @param bounds
	 *            the lower and upper bounds
	 */
	public DoubleMapGenotype(List<K> keys, Bounds<Double> bounds) {
		super(bounds);
		this.keys = keys;
	}

	/**
	 * Initialize this genotype with random values based on the size of the
	 * {@code keys}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public void init(Random random) {
		super.init(random, keys.size());
	}

	/**
	 * Not supported. Use {@link DoubleMapGenotype#init(Random)} instead.
	 * 
	 * @see org.opt4j.genotype.DoubleGenotype#init(java.util.Random, int)
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
	 * @see org.opt4j.genotype.MapGenotype#getValue(java.lang.Object)
	 */
	@Override
	public Double getValue(K key) {
		int i = keys.indexOf(key);
		return get(i);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#setValue(java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void setValue(K key, Double value) {
		int i = keys.indexOf(key);
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
			Constructor<? extends DoubleMapGenotype> cstr = this.getClass().getConstructor(List.class, Bounds.class);
			return (G) cstr.newInstance(keys, bounds);
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
		return keys.indexOf(key);
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
