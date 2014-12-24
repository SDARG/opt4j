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

import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The {@link DefaultIndividualFactory} is a creator for standard
 * {@link Individual}s.
 * 
 * @author lukasiewycz, glass
 * 
 */
@Singleton
public class DefaultIndividualFactory extends AbstractIndividualFactory<Individual> {

	/**
	 * Constructs a {@link DefaultIndividualFactory} with a {@link Provider} for
	 * {@link Individual}s.
	 * 
	 * @param individualProvider
	 *            the provider that creates Individuals
	 * @param creator
	 *            the creator
	 */
	@Inject
	public DefaultIndividualFactory(Provider<Individual> individualProvider, Creator<Genotype> creator) {
		super(individualProvider, creator);
	}

}
