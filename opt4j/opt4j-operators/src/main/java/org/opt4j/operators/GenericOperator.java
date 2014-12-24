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
 
package org.opt4j.operators;

import java.util.Collection;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.operators.AbstractGenericOperator.OperatorPredicate;

/**
 * The {@link GenericOperator} is an interface for generic operators.
 * 
 * @author lukasiewycz
 * 
 * @param <O>
 *            the operator
 */
public interface GenericOperator<O extends Operator<?>> {
	/**
	 * Adds an operator.
	 * 
	 * @param predicate
	 *            the operator predicate
	 * @param operator
	 *            The {@link Operator}
	 */
	public void addOperator(OperatorPredicate predicate, O operator);

	/**
	 * Returns the {@link Operator} for a specific {@link Genotype}.
	 * 
	 * @param genotype
	 *            the genotype
	 * @return the operator for this genotype
	 */
	public O getOperator(Genotype genotype);

	/**
	 * Returns all classOperators.
	 * 
	 * @return all classOperators
	 */
	public Collection<O> getOperators();

}
