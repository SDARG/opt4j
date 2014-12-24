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
 

package org.opt4j.benchmarks;

import org.opt4j.core.genotype.Bounds;
import org.opt4j.core.genotype.DoubleGenotype;

/**
 * The {@link DoubleString}.
 * 
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class DoubleString extends DoubleGenotype {

	static class DefaultBounds implements Bounds<Double> {
		@Override
		public Double getLowerBound(int index) {
			return 0.0;
		}

		@Override
		public Double getUpperBound(int index) {
			return 1.0;
		}
	}

	/**
	 * Constructs a {@link DoubleString} with {@code 0.0} lower bounds and
	 * {@code 1.0} upper bounds.
	 * 
	 */
	public DoubleString() {
		this(new DefaultBounds());
	}

	/**
	 * Constructs a {@link DoubleString}.
	 * 
	 * @param bounds
	 *            the bounds
	 */
	public DoubleString(Bounds<Double> bounds) {
		super(bounds);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		return DoubleString.class.getSimpleName() + " size=" + this.size();
	}

}
