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

import org.opt4j.core.Genotype;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Opt4JModule;
import org.opt4j.operators.AbstractGenericOperator.OperatorClassPredicate;
import org.opt4j.operators.AbstractGenericOperator.OperatorPredicate;
import org.opt4j.operators.AbstractGenericOperator.OperatorVoidPredicate;

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
