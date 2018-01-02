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


package org.opt4j.optimizers.de;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

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
