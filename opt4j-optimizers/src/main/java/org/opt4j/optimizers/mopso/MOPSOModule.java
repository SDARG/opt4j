/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/

package org.opt4j.optimizers.mopso;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import org.opt4j.core.IndividualFactory;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Name;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

/**
 * The {@link MOPSOModule} for the {@link MOPSO} optimizer.
 * 
 * @author lukasiewycz
 */
@Info("Multi-objective particle swarm optimizer. Mostly based on the OMOPSO. Works only with real-valued problems.")
@Citation(title = "Improving PSO-based Multi-Objective Optimization using Crowding, Mutation and ∈-Dominance", authors = "M. Reyes Sierra and C. A. Coello Coello", journal = "Proceedings of Evolutionary Multi-Criterion Optimization", month = UNKNOWN, year = 2005)
public class MOPSOModule extends OptimizerModule {

	@Info("The number of particles.")
	@Order(1)
	@Constant(value = "size", namespace = MOPSO.class)
	protected int particles = 100;

	@Info("The archive size of the global leaders.")
	@Name("archive size")
	@Order(2)
	@Constant(value = "archiveSize", namespace = MOPSO.class)
	protected int archiveSize = 100;

	@Info("The number of iterations.")
	@Order(0)
	@MaxIterations
	protected int iterations = 1000;

	@Info("The perturbation index for the mutation.")
	@Order(3)
	protected double perturbation = 0.5;

	/**
	 * Returns the perturbation.
	 * 
	 * @see #setPerturbation
	 * @return the perturbation
	 */
	public double getPerturbation() {
		return perturbation;
	}

	/**
	 * Sets the perturbation.
	 * 
	 * @see #getPerturbation
	 * @param perturbation
	 *            the perturbation to set
	 */
	public void setPerturbation(double perturbation) {
		this.perturbation = perturbation;
	}

	/**
	 * Returns the number of iterations.
	 * 
	 * @see #setIterations
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * Sets the the number of iterations.
	 * 
	 * @see #getIterations
	 * @param iterations
	 *            the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * Returns the number of particles.
	 * 
	 * @see #setParticles
	 * @return the particles
	 */
	public int getParticles() {
		return particles;
	}

	/**
	 * Sets the number of particles.
	 * 
	 * @see #getParticles
	 * @param particles
	 *            the particles to set
	 */
	public void setParticles(int particles) {
		this.particles = particles;
	}

	/**
	 * Returns the leader archive size.
	 * 
	 * @see #setArchiveSize
	 * @return the archiveSize
	 */
	public int getArchiveSize() {
		return archiveSize;
	}

	/**
	 * Sets the leader archive size.
	 * 
	 * @see #getArchiveSize
	 * @param archiveSize
	 *            the archiveSize to set
	 */
	public void setArchiveSize(int archiveSize) {
		this.archiveSize = archiveSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	protected void config() {
		bindIterativeOptimizer(MOPSO.class);
		bind(IndividualFactory.class).to(ParticleFactory.class);

		bindConstant("perturbation", MutateDoubleNonUniform.class).to(perturbation);
		bindConstant("perturbation", MutateDoubleUniform.class).to(perturbation);
	}

}
