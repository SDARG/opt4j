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


package org.opt4j.core;

/**
 * The {@link Genotype} represents a marker interface. A {@link Genotype}
 * represents the genetic encoding of a solution of the optimization problem.
 * Thus, a {@link Genotype} can be decoded to a phenotype with an appropriate
 * {@link org.opt4j.core.problem.Decoder}.
 * 
 * @author glass, lukasiewycz
 * 
 */
public interface Genotype {

	/**
	 * The number of atomic elements of the {@link Genotype}.
	 * 
	 * @return number of atomic elements of the genotype
	 */
	public int size();

	/**
	 * Constructs a new (empty) instance of this {@link Genotype}.
	 * 
	 * @param <G>
	 *            the type of genotype for an implicit cast
	 * @return new instance of the genotype
	 */
	public <G extends Genotype> G newInstance();

}
