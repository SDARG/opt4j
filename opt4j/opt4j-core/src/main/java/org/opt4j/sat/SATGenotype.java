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

package org.opt4j.sat;

import org.opt4j.core.Genotype;
import org.opt4j.genotype.BooleanGenotype;
import org.opt4j.genotype.CompositeGenotype;
import org.opt4j.genotype.DoubleGenotype;

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
