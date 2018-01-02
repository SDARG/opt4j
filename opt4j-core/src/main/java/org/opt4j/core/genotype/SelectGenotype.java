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
import java.util.Arrays;
import java.util.List;
import org.opt4j.core.Genotype;

/**
 * <p>
 * The {@link SelectGenotype} selects for each index an element from the given
 * list.
 * </p>
 * <p>
 * Example problem: Draw five times from an urn which contains three balls and
 * return the ball after each draw.<br/>
 * Example usage: <blockquote>
 * 
 * <pre>
 * SelectGenotype&lt;Ball&gt; genotype = new SelectGenotype&lt;Ball&gt;(Arrays.asList(ball1, ball2, ball3));
 * genotype.init(new Random(), 5);
 * </pre>
 * 
 * </blockquote> Example instance: [0=ball3, 1=ball2, 2=ball2, 3=ball1, 4=ball3]
 * <br/>
 * Example search space size: 3<sup>5</sup>
 * </p>
 * 
 * @author lukasiewycz
 * 
 * @param <V>
 *            the type of elements
 */
public class SelectGenotype<V> extends IntegerGenotype {

	private static final long serialVersionUID = 1L;

	protected final List<V> values;

	/**
	 * Constructs a {@link SelectGenotype}.
	 * 
	 * @param values
	 *            the elements to be selected
	 */
	public SelectGenotype(List<V> values) {
		super(0, values.size() - 1);
		this.values = values;
	}

	/**
	 * Constructs a {@link SelectGenotype}.
	 * 
	 * @param values
	 *            the elements to be selected
	 */
	public SelectGenotype(V[] values) {
		this(Arrays.asList(values));
	}

	/**
	 * Returns the element value of the index.
	 * 
	 * @param index
	 *            the index
	 * @return the element
	 */
	public V getValue(int index) {
		return values.get(get(index));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.genotype.IntegerGenotype#newInstance()
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <G extends Genotype> G newInstance() {
		try {
			Constructor<? extends SelectGenotype> cstr = this.getClass().getConstructor(List.class);
			return (G) cstr.newInstance(values);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
