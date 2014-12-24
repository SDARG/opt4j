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


package org.opt4j.core;

import com.google.inject.ImplementedBy;

/**
 * The {@link IndividualFactory} is a creator for {@link Individual}s.
 * 
 * @author lukasiewycz, glass
 * 
 */
@ImplementedBy(DefaultIndividualFactory.class)
public interface IndividualFactory {

	/**
	 * Creates a new {@link Individual}.
	 * 
	 * @return the built individual
	 */
	public Individual create();

	/**
	 * Creates a new {@link Individual} with a specified {@link Genotype}.
	 * 
	 * @param genotype
	 *            the genotype of the individual
	 * @return the built individual
	 */
	public Individual create(Genotype genotype);

	/**
	 * Adds an {@link IndividualStateListener} to each {@link Individual} that
	 * is created by this class. This listener is invoked if the state of the
	 * {@link Individual} changes.
	 * 
	 * @see #removeIndividualStateListener
	 * @param listener
	 *            the listener that is invoked if the state of any individual
	 *            changes
	 */
	public void addIndividualStateListener(IndividualStateListener listener);

	/**
	 * Removes an {@link IndividualStateListener} that is invoked if the state
	 * of any {@link Individual} changes. All {@link Individual}s created after
	 * this method call by this {@link IndividualFactory} do no longer use the
	 * listener.
	 * 
	 * @see #addIndividualStateListener
	 * @param listener
	 *            the listener that is invoked if the state of any individual
	 *            changes
	 */
	public void removeIndividualStateListener(IndividualStateListener listener);

}
