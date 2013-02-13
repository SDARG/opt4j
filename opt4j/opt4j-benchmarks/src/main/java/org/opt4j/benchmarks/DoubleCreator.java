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

package org.opt4j.benchmarks;

import java.util.Random;

import org.opt4j.core.common.random.Rand;
import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;

/**
 * The {@link DoubleCreator} creates {@link DoubleString}s with the length
 * {@code n}.
 * 
 * @author lukasiewycz
 * 
 */
public class DoubleCreator implements Creator<DoubleString> {

	protected final int n;

	protected final Random random;

	/**
	 * Constructs a {@link DoubleCreator}.
	 * 
	 * @param random
	 *            the random number generator
	 * @param n
	 *            the n value
	 */
	@Inject
	public DoubleCreator(Rand random, @N int n) {
		super();
		this.random = random;
		this.n = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Creator#create()
	 */
	@Override
	public DoubleString create() {
		DoubleString string = new DoubleString();
		string.init(random, n);
		return string;
	}
}
