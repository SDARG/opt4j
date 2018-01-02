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
import java.util.List;
import java.util.Random;

import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link BooleanMapGenotype} is a {@link BooleanGenotype} with the
 * {@link MapGenotype} functionality.
 * </p>
 * <p>
 * Example problem: Select on/off state of five switches switch<sub>1</sub>,
 * switch<sub>2</sub>, switch<sub>3</sub>, switch<sub>4</sub>,
 * switch<sub>5</sub><br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * BooleanMapGenotype&lt;Switch&gt; genotype = new BooleanMapGenotype&lt;Switch&gt;(Arrays.asList(switch1, switch2, switch3, switch4,
 * 		switch5));
 * genotype.init(new Random());
 * </pre>
 * 
 * </blockquote> Example instance:
 * [switch1=true;switch2=true;switch3=false;switch4=false;switch5=true;] <br/>
 * Example search space size: 2<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * 
 * @param <K>
 *            the type of keys
 */
public class BooleanMapGenotype<K> extends BooleanGenotype implements MapGenotype<K, Boolean> {

	protected final List<K> list;

	/**
	 * Constructs a {@link BooleanMapGenotype}.
	 * 
	 * @param list
	 *            the list of keys
	 */
	public BooleanMapGenotype(List<K> list) {
		super();
		this.list = list;
	}

	/**
	 * Initialize this genotype with random values based on the number of keys.
	 * 
	 * @param random
	 *            the random number generator
	 */
	public void init(Random random) {
		super.init(random, list.size());
	}

	/**
	 * Not supported. Use {@link BooleanMapGenotype#init(Random)} instead.
	 * 
	 * @see org.opt4j.core.genotype.BooleanGenotype#init(java.util.Random, int)
	 */
	@Override
	public void init(Random random, int n) {
		throw new UnsupportedOperationException("Use method init(Random) instead");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.MapGenotype#getValue(java.lang.Object)
	 */
	@Override
	public Boolean getValue(K key) {
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
	public void setValue(K key, Boolean value) {
		int i = list.indexOf(key);
		while (size() <= i) {
			add(false);
		}
		set(i, value);
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
	 * @see org.opt4j.core.Genotype#newInstance()
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends BooleanMapGenotype> cstr = this.getClass().getConstructor(List.class);
			return (G) cstr.newInstance(list);
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
			boolean value = this.get(i);
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
