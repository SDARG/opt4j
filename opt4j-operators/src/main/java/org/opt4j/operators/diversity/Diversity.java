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
 

package org.opt4j.operators.diversity;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Operator;

import com.google.inject.ImplementedBy;

/**
 * The {@link Diversity} determines the genetic diversity of two
 * {@link Individual}s. The genetic diversity is 0 if both {@link Genotype}s are
 * equal and 1 of they are of maximum diversity.
 * 
 * @author glass, lukasiewycz
 * 
 * @param <G>
 *            the type of genotype
 */
@ImplementedBy(DiversityGenericImplementation.class)
public interface Diversity<G extends Genotype> extends Operator<G> {

	/**
	 * Returns the genetic diversity of two {@link Genotype}s.
	 * 
	 * @param a
	 *            the first genotype
	 * @param b
	 *            the second genotype
	 * @return the diversity of two genotypes
	 */
	public double diversity(G a, G b);

}
