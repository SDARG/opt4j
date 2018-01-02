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


package org.opt4j.optimizers.ea;

import java.util.Collection;

import org.opt4j.core.Individual;
import org.opt4j.core.optimizer.Population;

import com.google.inject.ImplementedBy;

/**
 * The interface {@link Selector} is used to select a certain subset of
 * {@link Individual}s from a {@link Population} by respecting certain metrics
 * like their fitness. The core methods are {@link #getParents}, that returns a
 * set of {@link Individual}s that can be used as parents for the next
 * generation and {@link #getLames}, that returns a list of {@link Individual}s
 * that can be removed from the given individual list in order to form the next
 * generation.
 * 
 * @author glass, lukasiewycz
 * 
 */
@ImplementedBy(SelectorDefault.class)
public interface Selector {

	/**
	 * Selects a subset of {@link Individual}s and returns it as a new
	 * {@link Collection}. These so called parents can be used to form the next
	 * generation.
	 * 
	 * @param mu
	 *            the number of parents to select
	 * @param population
	 *            the list of individuals
	 * @return the parents
	 */
	public Collection<Individual> getParents(int mu, Collection<Individual> population);

	/**
	 * Selects a subset of {@code lambda} {@link Individual}s and returns it as
	 * a new {@link Collection}. These individuals can be erased in the next
	 * generation.
	 * 
	 * @param lambda
	 *            the number of lames to select
	 * @param population
	 *            the list of individuals
	 * @return the worst {@code lambda} individuals
	 */
	public Collection<Individual> getLames(int lambda, Collection<Individual> population);

	/**
	 * Sets the maximal number of {@link Individual}s.
	 * 
	 * @param maxsize
	 *            the number of individuals
	 */
	public void init(int maxsize);

}
