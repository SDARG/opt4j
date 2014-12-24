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

import java.util.List;

import org.opt4j.core.problem.Decoder;

import com.google.inject.Inject;

/**
 * The {@link BinaryToDoubleDecoder}.
 * 
 * @author lukasiewycz
 * 
 */
public class BinaryToDoubleDecoder implements Decoder<BinaryString, DoubleString> {

	protected final int n;

	protected final int bits;

	/**
	 * Constructs a {@link BinaryToDoubleDecoder}.
	 * 
	 * @param n
	 *            the n value
	 * @param bits
	 *            the number of bits per double value
	 */
	@Inject
	public BinaryToDoubleDecoder(@N int n, @Bits int bits) {
		super();
		this.n = n;
		this.bits = bits;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Decoder#decode(org.opt4j.core.Genotype)
	 */
	@Override
	public DoubleString decode(BinaryString genotype) {
		DoubleString phenotype = new DoubleString();
		for (int i = 0; i < n; i++) {
			int begin = i * bits;
			int end = begin + bits;
			double x = toDouble(genotype, begin, end);
			phenotype.add(x);
		}

		return phenotype;
	}

	/**
	 * Converts a sublist of boolean values to an integer in [0;1).
	 * 
	 * @param list
	 *            the list of booleans
	 * @param begin
	 *            the begin of the sublist
	 * @param end
	 *            the end of the sublist
	 * @return the double value
	 */
	private double toDouble(List<Boolean> list, int begin, int end) {
		// this is very efficient since only integers and bit operations are
		// used
		int b = 1;
		int sum = 0;
		for (int i = end - 1; i >= begin; i--) {
			if (list.get(i)) {
				sum |= b;
			}
			b <<= 1;
		}

		return (double) sum / b;
	}
}
