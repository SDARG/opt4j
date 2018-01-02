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
 

package org.opt4j.optimizers.ea;

import org.opt4j.core.common.archive.Crowding;
import org.opt4j.core.common.random.Rand;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The default selector is the {@link Nsga2} selector with the
 * {@code tournament} value 0.
 * 
 * @see Nsga2
 * @author lukasiewycz
 * 
 */
@Singleton
public class SelectorDefault extends Nsga2 {

	/**
	 * Constructs a new {@link SelectorDefault}.
	 * 
	 * @param random
	 *            the random number generator
	 */
	@Inject
	public SelectorDefault(Rand random) {
		super(random, 0, new Crowding());
	}

}
