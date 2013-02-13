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

package org.opt4j.benchmark;

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
