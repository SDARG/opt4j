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
 * The {@link IntegerMapGenotype} is a {@link IntegerGenotype} with the
 * {@link MapGenotype} functionality.
 * </p>
 * <p>
 * Example problem: Select the outcome of throwing five dice die<sub>1</sub>,
 * die<sub>2</sub>, die<sub>3</sub>, die<sub>4</sub>, die<sub>5</sub><br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * IntegerGenotype&lt;Die&gt; genotype = new IntegerMapGenotype&lt;Die&gt;(Arrays.asList(die1, die2, die3, die4, die5), 1, 6);
 * genotype.init(new Random());
 * </pre>
 * 
 * </blockquote> Example instance: [die1=3, die2=5, die3=6, die4=1, die5=3]<br/>
 * Example search space size: 6<sup>5</sup>
 * </p>
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
		Set<K> uniqueKeys = new HashSet<K>();
		for (K k : list) {
			uniqueKeys.add(k);
		}
		if (uniqueKeys.size() < list.size()) {
			throw new IllegalArgumentException("The provided key objects have to be unique");
		}
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
		if (!list.contains(key)) {
			throw new IllegalArgumentException("Invalid key");
		}
		int i = list.indexOf(key);
		while (size() <= i) {
			add(bounds.getLowerBound(size()));
		}
		if (bounds.getLowerBound(i) > value || bounds.getUpperBound(i) < value) {
			throw new IllegalArgumentException(
					"The provided value does not lie within the bounds for the provided key");
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
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for (int i = 0; i < size(); i++) {
			K key = list.get(i);
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
		if (!list.contains(key)) {
			throw new IllegalArgumentException("Invalid key");
		}
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
