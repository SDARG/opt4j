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


package org.opt4j.satdecoding;

import org.opt4j.core.Genotype;
import org.opt4j.core.genotype.BooleanGenotype;
import org.opt4j.core.genotype.CompositeGenotype;
import org.opt4j.core.genotype.DoubleGenotype;

/**
 * Specialized {@link Genotype} for the {@link AbstractSATDecoder}. The
 * {@link SATGenotype} consists of two components: a binary and a double vector.
 * 
 * @author lukasiewycz
 * 
 */
public class SATGenotype extends CompositeGenotype<Integer, Genotype> {

	/**
	 * Constructs a {@link SATGenotype} with empty vectors.
	 */
	public SATGenotype() {
		this(new BooleanGenotype(), new DoubleGenotype());
	}

	/**
	 * Constructs a {@link SATGenotype} with the given vectors.
	 * 
	 * @param booleanVector
	 *            the boolean vector
	 * @param doubleVector
	 *            the double vector
	 */
	public SATGenotype(BooleanGenotype booleanVector, DoubleGenotype doubleVector) {
		put(0, booleanVector);
		put(1, doubleVector);
	}

	/**
	 * Returns the boolean vector.
	 * 
	 * @see #setBooleanVector
	 * @return the booleany vector
	 */
	public BooleanGenotype getBooleanVector() {
		return get(0);
	}

	/**
	 * Sets the boolean vector.
	 * 
	 * @see #getBooleanVector
	 * @param booleanVector
	 *            the binary vector to set
	 */
	public void setBooleanVector(BooleanGenotype booleanVector) {
		put(0, booleanVector);
	}

	/**
	 * Returns the double vector.
	 * 
	 * @see #setDoubleVector
	 * @return the double vector
	 */
	public DoubleGenotype getDoubleVector() {
		return get(1);
	}

	/**
	 * Sets the double vector.
	 * 
	 * @see #getDoubleVector
	 * @param doubleVector
	 *            the double vector to set
	 */
	public void setDoubleVector(DoubleGenotype doubleVector) {
		put(1, doubleVector);
	}

}
