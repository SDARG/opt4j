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

package org.opt4j.optimizer.de;

import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.start.Constant;

/**
 * The {@link DifferentialEvolutionModule}.
 * 
 * @author lukasiewycz
 * 
 */
@Info("A population based optimization heuristic using vector differences.")
public class DifferentialEvolutionModule extends OptimizerModule {

	@Info("The number of generations.")
	@Order(0)
	@MaxIterations
	protected int generations = 1000;

	@Info("The size of the population.")
	@Order(1)
	@Constant(value = "alpha", namespace = DifferentialEvolution.class)
	protected int alpha = 100;

	@Info("The scaling factor F (0 <= F <= 2.0).")
	@Order(2)
	@Constant(value = "scalingFactor", namespace = DifferentialEvolution.class)
	protected double scalingFactor = 0.5;

	/**
	 * Returns the alpha.
	 * 
	 * @see #setAlpha
	 * @return the alpha
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha.
	 * 
	 * @see #getAlpha
	 * @param alpha
	 *            the alpha to set
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the generations.
	 * 
	 * @see #setGenerations
	 * @return the generations
	 */
	public int getGenerations() {
		return generations;
	}

	/**
	 * Sets the generations.
	 * 
	 * @see #getGenerations
	 * @param generations
	 *            the generations to set
	 */
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	/**
	 * Returns the scaling factor.
	 * 
	 * @see #setScalingFactor
	 * @return the scalingFactor
	 */
	public double getScalingFactor() {
		return scalingFactor;
	}

	/**
	 * Sets the scaling factor.
	 * 
	 * @see #getScalingFactor
	 * @param scalingFactor
	 *            the scalingFactor to set
	 */
	public void setScalingFactor(double scalingFactor) {
		this.scalingFactor = scalingFactor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindIterativeOptimizer(DifferentialEvolution.class);
	}
}
