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
 * Provides the classes for basic {@link org.opt4j.core.Genotype}s.
 * </p>
 * <p>
 * The basic {@link org.opt4j.core.Genotype} classes are the following:
 * <ul>
 * <li>{@link org.opt4j.core.genotype.BooleanGenotype} - a list of boolean (binary)
 * values</li>
 * <li>{@link org.opt4j.core.genotype.DoubleGenotype} - a list of double (real)
 * values</li>
 * <li>{@link org.opt4j.core.genotype.IntegerGenotype} - a list of integer values</li>
 * <li>{@link org.opt4j.core.genotype.PermutationGenotype} - a permutation of
 * arbitrary objects</li>
 * <li>{@link org.opt4j.core.genotype.CompositeGenotype} - a container for arbitrary
 * genotype objects</li>
 * </ul>
 * </p>
 * <p>
 * Try to stick to the predefined genotype objects. Use the
 * {@link org.opt4j.core.genotype.CompositeGenotype} to assemble complex genotypes.
 * If you define your own genotype, keep in mind that you also have to define
 * the corresponding {@link org.opt4j.core.optimizer.Operator}s.
 * </p>
 * <p>
 * The {@link org.opt4j.core.genotype.DoubleGenotype} and
 * {@link org.opt4j.core.genotype.IntegerGenotype} both can be bounded by an upper
 * and lower bound for each value. The corresponding bounds classes are the
 * {@link org.opt4j.core.genotype.DoubleBounds} and
 * {@link org.opt4j.core.genotype.IntegerBounds}. These bounds are passed in the
 * constructor of the {@link org.opt4j.core.genotype.DoubleGenotype} or
 * {@link org.opt4j.core.genotype.IntegerGenotype}, respectively.
 * </p>
 * <p>
 * By default, the {@link org.opt4j.core.genotype.BooleanGenotype}, the
 * {@link org.opt4j.core.genotype.DoubleGenotype}, and the
 * {@link org.opt4j.core.genotype.IntegerGenotype} are pure lists. This means, one
 * has to identify elements by their index. The classes
 * {@link org.opt4j.core.genotype.BooleanMapGenotype},
 * {@link org.opt4j.core.genotype.DoubleMapGenotype}, and
 * {@link org.opt4j.core.genotype.IntegerMapGenotype} extend the basic genotype
 * classes by a map functionality. Therefore, a list of the key elements has to
 * be passed in the constructors. Internally, this list is used for a mapping of
 * key to indices. Therefore, this list must be in the same order with the same
 * elements for corresponding genotype instances.
 * </p>
 * <p>
 * A further extension of the {@link org.opt4j.core.genotype.IntegerGenotype} is the
 * {@link org.opt4j.core.genotype.SelectGenotype} and
 * {@link org.opt4j.core.genotype.SelectMapGenotype}. Here, a specific set of
 * elements is defined such that for each index or key one element is chosen.
 * Internally, this is implemented as an
 * {@link org.opt4j.core.genotype.IntegerGenotype} such that the elements are encoded
 * as integer values.
 * </p>
 * <p>
 * The classes {@link org.opt4j.core.genotype.BooleanMapGenotype},
 * {@link org.opt4j.core.genotype.DoubleMapGenotype},
 * {@link org.opt4j.core.genotype.IntegerMapGenotype},
 * {@link org.opt4j.core.genotype.SelectGenotype}, and
 * {@link org.opt4j.core.genotype.SelectMapGenotype} are extended from
 * {@link org.opt4j.core.genotype.BooleanGenotype},
 * {@link org.opt4j.core.genotype.DoubleGenotype}, and
 * {@link org.opt4j.core.genotype.IntegerGenotype}, respectively. Therefore, the
 * default operators (in package {@code org.opt4j.operator}) for the simple
 * {@link org.opt4j.core.Genotype} classes are applied on their
 * {@link org.opt4j.core.genotype.MapGenotype} and
 * {@link org.opt4j.core.genotype.SelectGenotype} classes.
 * </p>
 * <p>
 * Custom genotypes have to implement the {@link org.opt4j.core.Genotype}
 * interface. If these classes are not extended from existing
 * {@link org.opt4j.core.Genotype} classes, appropriate operators have to be
 * implemented (see package {@code org.opt4j.operator}).
 * </p>
 * <p>
 * The initialization of the genotypes is done in the
 * {@link org.opt4j.core.problem.Creator}. Each genotype class has an
 * appropriate {@code init} method to simplify the initialization.
 * </p>
 * 
 */
package org.opt4j.core.genotype;