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
 

package org.opt4j.operators.crossover;

/**
 * The {@link Pair} groups two objects of the same type in a given order.
 * 
 * @author glass
 * @param <A>
 *            the type of the paired objects
 */
public class Pair<A> {

	protected final A first;

	protected final A second;

	/**
	 * Constructs a {@link Pair} with a first and a second element.
	 * 
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 */
	public Pair(A first, A second) {
		super();
		this.first = first;
		this.second = second;
	}

	/**
	 * Returns the first element.
	 * 
	 * @return the first element
	 */
	public A getFirst() {
		return first;
	}

	/**
	 * Returns the second element.
	 * 
	 * @return the second element
	 */
	public A getSecond() {
		return second;
	}
}
