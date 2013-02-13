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

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Category;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Parent;
import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.operator.AbstractGenericOperator.OperatorClassPredicate;
import org.opt4j.operator.AbstractGenericOperator.OperatorPredicate;
import org.opt4j.operator.AbstractGenericOperator.OperatorVoidPredicate;
import org.opt4j.start.Opt4JModule;

import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

/**
 * Module class for an {@link Operator}.
 * 
 * @author lukasiewycz
 * 
 * @param <P>
 *            The specific operator with a wildcard (?).
 */
@SuppressWarnings("rawtypes")
@Icon(Icons.OPERATOR)
@Category
@Parent(OptimizerModule.class)
public abstract class OperatorModule<P extends Operator> extends Opt4JModule {

	/**
	 * Add an {@link Operator}.
	 * 
	 * @param operator
	 *            the operator to be added
	 */
	public void addOperator(Class<? extends P> operator) {
		addOperator(new OperatorVoidPredicate(), operator);
	}

	/**
	 * Add an {@link Operator} and apply it to each {@link Genotype} that
	 * satisfies the predicate.
	 * 
	 * @param predicate
	 *            the predicate
	 * @param operator
	 *            the operator
	 */
	public void addOperator(OperatorPredicate predicate, Class<? extends P> operator) {
		MapBinder<OperatorPredicate, P> map = MapBinder.newMapBinder(binder(), new TypeLiteral<OperatorPredicate>() {
		}, getOperatorTypeLiteral());
		map.addBinding(predicate).to(operator);
	}

	protected abstract TypeLiteral<P> getOperatorTypeLiteral();

	/**
	 * Add an {@link Operator} and apply it to each {@link Genotype} that equals
	 * the given class.
	 * 
	 * @param genotype
	 *            the genotype class
	 * @param operator
	 *            the operator
	 */
	public void addOperator(Class<? extends Genotype> genotype, Class<? extends P> operator) {
		addOperator(new OperatorClassPredicate(genotype), operator);
	}

}
