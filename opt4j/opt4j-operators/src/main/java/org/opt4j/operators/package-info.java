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
 

/**
 * <p>
 * Provides the classes for general (generic) operators.
 * </p>
 * <p>
 * The {@link org.opt4j.core.Genotype} objects are changed within the
 * optimization process in order to find better solutions. The variation is
 * performed by the {@link org.opt4j.core.optimizer.Operator} classes. The
 * framework already contains several operators:
 * <ul>
 * <li>{@link org.opt4j.operators.algebra.Algebra} - Vector-based operator with
 * terms (restricted to {@link org.opt4j.core.genotype.DoubleGenotype})</li>
 * <li>{@link org.opt4j.operators.copy.Copy} - Copy operator</li>
 * <li>{@link org.opt4j.operators.crossover.Crossover} - Crossover operator that
 * always creates two offspring from two parents</li>
 * <li>{@link org.opt4j.operators.diversity.Diversity} - Diversity operator that
 * determines the differences between two genotypes</li>
 * <li>{@link org.opt4j.operators.mutate.Mutate} - Mutate operator that changes
 * one genotype</li>
 * <li>{@link org.opt4j.operators.neighbor.Neighbor} - Neighbor operator that
 * changes one genotype</li>
 * </ul>
 * Each {@link org.opt4j.core.optimizer.Operator} is parameterized with the
 * corresponding target {@link org.opt4j.core.Genotype}. Adding custom
 * operators, e.g., a new {@link org.opt4j.operators.crossover.Crossover}
 * operator for the {@link org.opt4j.core.genotype.DoubleGenotype}, is quite simple.
 * The new operator has to be extended from
 * {@link org.opt4j.operators.crossover.Crossover} with the parameter
 * {@link org.opt4j.core.genotype.DoubleGenotype} or alternatively directly from the
 * {@link org.opt4j.operators.crossover.CrossoverDouble}. The corresponding
 * crossover method has to be implemented. Finally, the operator has to be added
 * with the
 * {@link org.opt4j.operators.crossover.CrossoverModule#addOperator(Class)}
 * method. Therefore, you can extend a custom module from
 * {@link org.opt4j.operators.crossover.CrossoverModule}.
 * </p>
 * <p>
 * The appropriate operator is determined at runtime by the framework by
 * checking the parameter of the operator. Alternatively, the
 * {@link org.opt4j.operators.Apply} annotation can be used to specify a
 * different target {@link org.opt4j.core.Genotype} class.
 * </p>
 * <p>
 * Creating completely new operators is done by extending the
 * {@link org.opt4j.core.optimizer.Operator} interface to the new operator with
 * the specific method. The new operator implementation can be bound directly
 * within a module in the
 * {@link org.opt4j.operators.OperatorModule#configure(com.google.inject.Binder)}
 * method by
 * {@code bind(CustomOperator.class).to(CustomOperatorImplmenentation.class)} or
 * using the {@code addOperator} methods in the respective
 * {@link org.opt4j.operators.OperatorModule}. Note that this operator will only
 * handle exactly its target {@link org.opt4j.core.Genotype} classes. To
 * indicate a specific target, use the {@link org.opt4j.operators.Apply}
 * annotation at the respective operator class.
 * </p>
 * <p>
 * A new generic operator should be instantiated from the
 * {@link org.opt4j.operators.AbstractGenericOperator}. See the existing generic
 * operators.
 * </p>
 * <A NAME="custom_operator"><!-- --></A><h2>Integration of a Custom Operator</h2>
 * To add new operators, two tasks have to be performed:
 * <ul>
 * <li>The implementation of the custom operator</li>
 * <li>The binding of the operator</li>
 * </ul>
 * In the following, a custom {@link org.opt4j.operators.crossover.Crossover}
 * operator for the {@link org.opt4j.core.genotype.BooleanGenotype} shall be
 * implemented that performs a bitwise or for one offspring and a bitwise and
 * for the other offspring.
 * 
 * <pre>
 * public class MyOperator implements Crossover&lt;BooleanGenotype&gt; {
 * 	public Pair&lt;BooleanGenotype&gt; crossover(BooleanGenotype parent1, BooleanGenotype parent2) {
 * 		BooleanGenotype g1 = parent1.newInstance();
 * 		BooleanGenotype g2 = parent1.newInstance();
 * 
 * 		for (int i = 0; i &lt; parent1.size(); i++) {
 * 			g1.add(i, parent1.get(i) || parent2.get(i));
 * 			g2.add(i, parent1.get(i) &amp;&amp; parent2.get(i));
 * 		}
 * 
 * 		return new Pair&lt;BooleanGenotype&gt;(g1, g2);
 * 	}
 * }
 * </pre>
 * 
 * To tell the framework to use this operator, implement a
 * {@link org.opt4j.operators.crossover.CrossoverModule} and add your custom
 * operator.
 * 
 * <pre>
 * public class MyOperatorModule extends CrossoverModule {
 * 	protected void config() {
 * 		addOperator(MyOperator.class);
 * 	}
 * }
 * </pre>
 * 
 */
package org.opt4j.operators;

