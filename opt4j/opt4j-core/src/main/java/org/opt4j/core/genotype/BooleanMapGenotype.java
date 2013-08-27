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
