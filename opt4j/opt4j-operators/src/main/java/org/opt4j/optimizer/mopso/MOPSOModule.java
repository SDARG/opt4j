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
package org.opt4j.optimizer.mopso;

import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Name;
import org.opt4j.config.annotations.Order;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.start.Constant;

/**
 * The {@link MOPSOModule} for the {@link MOPSO} optimizer.
 * 
 * @author lukasiewycz
 * 
 */
@Info("Multi-objective particle swarm optimizer. Mostly based on the OMOPSO. Works only with real-valued problems.")
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
