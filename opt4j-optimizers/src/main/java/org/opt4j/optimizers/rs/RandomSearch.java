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
 

package org.opt4j.optimizers.rs;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link RandomSearch} simply generates random {@link Individual}s and
 * evaluates them.
 * 
 * @author lukasiewycz
 * 
 */
public class RandomSearch implements IterativeOptimizer {

	protected final int batchsize;
	private final IndividualFactory individualFactory;
	private final Population population;

	/**
	 * Constructs a {@link RandomSearch}.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual creator
	 * @param batchsize
	 *            the size of the batch for an evaluation
	 */
	@Inject
	public RandomSearch(Population population, IndividualFactory individualFactory,
			@Constant(value = "batchsize", namespace = RandomSearch.class) int batchsize) {
		this.population = population;
		this.batchsize = batchsize;
		this.individualFactory = individualFactory;
	}

	@Override
	public void initialize() {
	}

	@Override
	public void next() throws TerminationException {
		population.clear();
		for (int i = 0; i < batchsize; i++) {
			Individual individual = individualFactory.create();
			population.add(individual);
		}
	}

}
