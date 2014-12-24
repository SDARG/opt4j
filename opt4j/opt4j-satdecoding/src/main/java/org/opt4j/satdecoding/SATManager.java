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

import java.util.List;
import java.util.Map;

import org.opt4j.core.Genotype;

import com.google.inject.ImplementedBy;

/**
 * Classes with this interface manage the creation of {@link Genotype}s and the
 * decoding to a {@link Model}.
 * 
 * @author lukasiewycz
 * 
 */
@ImplementedBy(MixedSATManager.class)
public interface SATManager {

	/**
	 * Returns the {@link Solver}.
	 * 
	 * @return the solver
	 */
	public Solver getSolver();

	/**
	 * Creates a new {@link Genotype} from the variables, priorities, and
	 * phases.
	 * 
	 * @param variables
	 *            the variables
	 * @param lowerBounds
	 *            the lower bounds for the priority for the variables
	 * @param upperBounds
	 *            the upper bounds for the priority for the variables
	 * @param priorities
	 *            the priorities
	 * @param phases
	 *            the phases
	 * @return the new genotype
	 */
	public Genotype createSATGenotype(List<Object> variables, Map<Object, Double> lowerBounds,
			Map<Object, Double> upperBounds, Map<Object, Double> priorities, Map<Object, Boolean> phases);

	/**
	 * Decodes the {@link Genotype} into a {@link Model}.
	 * 
	 * @param variables
	 *            the variables of the problem
	 * @param genotype
	 *            the genotype
	 * @return the model
	 */
	public Model decodeSATGenotype(List<Object> variables, Genotype genotype);

}
