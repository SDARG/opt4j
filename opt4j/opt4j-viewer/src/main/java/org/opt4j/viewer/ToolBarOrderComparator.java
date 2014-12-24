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
 
package org.opt4j.viewer;

import java.util.Comparator;

/**
 * The {@link ToolBarOrderComparator} is a comparator that orders objects
 * increasing by the value given in the {@link ToolBarOrder} annotation.
 * 
 * @author lukasiewycz
 * 
 * @param <T>
 *            the type of ordered elements
 */
class ToolBarOrderComparator<T> implements Comparator<T> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(T s0, T s1) {
		ToolBarOrder p0 = s0.getClass().getAnnotation(ToolBarOrder.class);
		ToolBarOrder p1 = s1.getClass().getAnnotation(ToolBarOrder.class);

		if (p0 != null && p1 != null) {
			return p0.value() - p1.value();
		} else if (p0 != null) {
			return -1;
		} else if (p1 != null) {
			return 1;
		} else {
			return s0.getClass().getName().compareTo(s1.getClass().getName());
		}
	}
}
