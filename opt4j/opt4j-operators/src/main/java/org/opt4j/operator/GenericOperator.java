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
package org.opt4j.operator;

import java.util.Collection;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.operator.AbstractGenericOperator.OperatorPredicate;

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
