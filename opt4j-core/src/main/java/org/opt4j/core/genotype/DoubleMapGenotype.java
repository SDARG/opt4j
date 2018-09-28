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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link DoubleMapGenotype} is a {@link DoubleGenotype} with the
 * {@link MapGenotype} functionality.
 * </p>
 * <p>
 * Example problem: Select filling level of five bottles bottle<sub>1</sub>,
 * bottle<sub>2</sub>, bottle<sub>3</sub>, bottle<sub>4</sub>,
 * bottle<sub>5</sub><br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * DoubleMapGenotype&lt;Bottle&gt; genotype = new DoubleMapGenotype&lt;Bottle&gt;(
 * 		Arrays.asList(bottle1, bottle2, bottle3, bottle4, bottle5));
 * genotype.init(new Random());
 * </pre>
 * 
 * </blockquote> Example instance:
 * [bottle1=0.5035947840006195,bottle2=0.9693492473483428
 * ,bottle3=0.12786372316728167
 * ,bottle4=0.5299369900029843,bottle5=0.8055193291478467]<br/>
 * Example search space size: [0;1]<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * @param <K>
 *            the type of keys
 */
public class DoubleMapGenotype<K> extends DoubleGenotype implements MapGenotype<K, Double> {

	protected final List<K> keys;

	private static final long serialVersionUID = 1L;

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
		Set<K> uniqueKeys = new HashSet<>(keys);
		if (uniqueKeys.size() < keys.size()) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_NON_UNIQUE_KEYS);
		}
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
	 * @see org.opt4j.core.genotype.DoubleGenotype#init(java.util.Random, int)
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
		if (!containsKey(key)) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_INVALID_KEY);
		}
		int i = keys.indexOf(key);
		if (value < bounds.getLowerBound(i) || value > bounds.getUpperBound(i)) {
			throw new IllegalArgumentException(MapGenotype.ERROR_MESSAGE_OUT_OF_BOUNDS);
		}
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < size(); i++) {
			K key = keys.get(i);
			double value = this.get(i);
			stringBuilder.append(key + "=" + value + ";");
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
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
	 * @see org.opt4j.genotype.MapGenotype#getKeys()
	 */
	@Override
	public Collection<K> getKeys() {
		return Collections.unmodifiableList(keys);
	}

}
