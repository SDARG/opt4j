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

/**
 * <p>
 * Provides the classes for basic {@link org.opt4j.core.Genotype}s.
 * </p>
 * <p>
 * The basic {@link org.opt4j.core.Genotype} classes are the following:
 * <ul>
 * <li>{@link org.opt4j.genotype.BooleanGenotype} - a list of boolean (binary)
 * values</li>
 * <li>{@link org.opt4j.genotype.DoubleGenotype} - a list of double (real)
 * values</li>
 * <li>{@link org.opt4j.genotype.IntegerGenotype} - a list of integer values</li>
 * <li>{@link org.opt4j.genotype.PermutationGenotype} - a permutation of
 * arbitrary objects</li>
 * <li>{@link org.opt4j.genotype.CompositeGenotype} - a container for arbitrary
 * genotype objects</li>
 * </ul>
 * </p>
 * <p>
 * Try to stick to the predefined genotype objects. Use the {@link
 * org.opt4j.genotype.CompositeGenotype} to assemble complex genotypes. If you define your own
 * genotype, keep in mind that you also have to define the corresponding
 * {@link org.opt4j.core.optimizer.Operator}s.
 * </p>
 * <p>
 * The {@link org.opt4j.genotype.DoubleGenotype} and {@link org.opt4j.genotype.IntegerGenotype} both can be bounded by
 * an upper and lower bound for each value. The corresponding bounds classes are
 * the {@link org.opt4j.genotype.DoubleBounds} and
 * {@link org.opt4j.genotype.IntegerBounds}. These bounds are passed in the
 * constructor of the {@link org.opt4j.genotype.DoubleGenotype} or {@link org.opt4j.genotype.IntegerGenotype},
 * respectively.
 * </p>
 * <p>
 * By default, the {@link org.opt4j.genotype.BooleanGenotype}, the {@link org.opt4j.genotype.DoubleGenotype}, and the {@link
 * org.opt4j.genotype.IntegerGenotype} are pure lists. This means, one has to identify elements by
 * their index. The classes {@link org.opt4j.genotype.BooleanMapGenotype},
 * {@link org.opt4j.genotype.DoubleMapGenotype}, and
 * {@link org.opt4j.genotype.IntegerMapGenotype} extend the basic genotype
 * classes by a map functionality. Therefore, a list of the key elements has to
 * be passed in the constructors. Internally, this list is used for a mapping of
 * key to indices. Therefore, this list must be in the same order with the same
 * elements for corresponding genotype instances.
 * </p>
 * <p>
 * A further extension of the {@link org.opt4j.genotype.IntegerGenotype} is the
 * {@link org.opt4j.genotype.SelectGenotype} and
 * {@link org.opt4j.genotype.SelectMapGenotype}. Here, a specific set of
 * elements is defined such that for each index or key one element is chosen.
 * Internally, this is implemented as an {@link org.opt4j.genotype.IntegerGenotype} such that the
 * elements are encoded as integer values.
 * </p>
 * <p>
 * The classes {@link org.opt4j.genotype.BooleanMapGenotype}, {@link org.opt4j.genotype.DoubleMapGenotype}, {@link
 * org.opt4j.genotype.IntegerMapGenotype}, {@link org.opt4j.genotype.SelectGenotype}, and {@link org.opt4j.genotype.SelectMapGenotype}
 * are extended from {@link org.opt4j.genotype.BooleanGenotype}, {@link org.opt4j.genotype.DoubleGenotype}, and {@link
 * org.opt4j.genotype.IntegerGenotype}, respectively. Therefore, the default operators (in package 
 * {@code org.opt4j.operator}) for the simple {@link org.opt4j.core.Genotype} classes are
 * applied on their {@link org.opt4j.genotype.MapGenotype} and {@link org.opt4j.genotype.SelectGenotype} classes.
 * </p>
 * <p>
 * Custom genotypes have to implement the
 * {@link org.opt4j.core.Genotype} interface. If these classes are not
 * extended from existing {@link org.opt4j.core.Genotype} classes, appropriate operators have
 * to be implemented (see package {@code org.opt4j.operator}).
 * </p>
 * <p>
 * The initialization of the genotypes is done in the
 * {@link org.opt4j.core.problem.Creator}. Each genotype class has an appropriate
 * {@code init} method to simplify the initialization.
 * </p>
 * 
 */
package org.opt4j.genotype;