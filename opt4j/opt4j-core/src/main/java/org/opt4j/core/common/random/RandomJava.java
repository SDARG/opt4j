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


package org.opt4j.core.common.random;

import java.util.Random;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * The {@link RandomJava} is the default java {@link Random}.
 * 
 * @author helwig, lukasiewycz
 */
@SuppressWarnings("serial")
@Singleton
public class RandomJava extends Rand {

	/**
	 * Constructs a {@link RandomJava} with the specified seed.
	 * 
	 * @param seed
	 *            the seed value (using namespace {@link Random})
	 */
	@Inject
	public RandomJava(@Constant(value = "seed", namespace = Random.class) long seed) {
		super(seed);
	}

}
