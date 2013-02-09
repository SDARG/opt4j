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

package org.opt4j.optimizer.ea;

import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * The {@link ConstantCrossoverRate} represents a crossover rate that is
 * constant during the whole optimization.
 * 
 * @author glass
 * 
 */
public class ConstantCrossoverRate implements CrossoverRate {
	protected final double crossoverRate;

	/**
	 * Constructs a {@link ConstantCrossoverRate} with a given crossover rate.
	 * 
	 * @param crossoverRate
	 *            the crossover rate
	 */
	@Inject
	public ConstantCrossoverRate(@Constant(value = "rate", namespace = ConstantCrossoverRate.class) double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.crossover.CrossoverRate#get()
	 */
	@Override
	public double get() {
		return crossoverRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.crossover.CrossoverRate#set(double)
	 */
	@Override
	public void set(double value) {
		// the crossover rate should remain constant
	}

}
