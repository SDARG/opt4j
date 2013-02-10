/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.optimizer.rs;

import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link RandomSearch} simply generates random {@link Individual}s and evaluates them.
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
