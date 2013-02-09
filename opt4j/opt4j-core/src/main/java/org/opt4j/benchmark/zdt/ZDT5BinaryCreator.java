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

package org.opt4j.benchmark.zdt;

import java.util.Random;

import org.opt4j.benchmark.BinaryString;
import org.opt4j.benchmark.N;
import org.opt4j.common.random.Rand;
import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;

/**
 * The {@link ZDT5BinaryCreator} for the ZDT5 problem.
 * 
 * @author lukasiewycz
 * 
 */
public class ZDT5BinaryCreator implements Creator<BinaryString> {

	protected final Random random;

	protected final int length;

	/**
	 * Constructs a {@link ZDT5BinaryCreator}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the n value
	 */
	@Inject
	public ZDT5BinaryCreator(Rand random, @N int n) {
		super();
		this.random = random;

		length = ((n > 0) ? 30 : 0) + (n - 1) * 5;
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
