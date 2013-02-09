/**
 * Opt4J is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with Opt4J. If not, see http://www.gnu.org/licenses/. 
 */

package org.opt4j.benchmark;

import java.util.Random;

import org.opt4j.common.random.Rand;
import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;

/**
 * The {@link BinaryCreator} creates {@link BinaryString}s with the length
 * {@code n*bits}.
 * 
 * @author lukasiewycz
 * 
 */
public class BinaryCreator implements Creator<BinaryString> {

	protected final Random random;

	protected final int length;

	/**
	 * Constructs a {@link BinaryCreator}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the n value
	 * @param bits
	 *            the number of bits per double value
	 */
	@Inject
	public BinaryCreator(Rand random, @N int n, @Bits int bits) {
		super();
		this.random = random;

		length = calcLength(bits, n);
	}

	protected final int calcLength(int bits, int n) {
		return bits * n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Creator#create()
	 */
	@Override
	public BinaryString create() {
		BinaryString string = new BinaryString();
		string.init(random, length);
		return string;
	}

}
