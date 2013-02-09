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
package org.opt4j.optimizer.mopso;

import org.opt4j.core.AbstractIndividualFactory;
import org.opt4j.core.Genotype;
import org.opt4j.core.problem.Creator;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * The {@link ParticleFactory}.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class ParticleFactory extends AbstractIndividualFactory<Particle> {

	/**
	 * Constructs a {@link ParticleFactory}.
	 * 
	 * @param particleProvider
	 *            the provider for particles
	 * @param creator
	 *            the creator
	 */
	@SuppressWarnings("rawtypes")
	@Inject
	public ParticleFactory(Provider<Particle> particleProvider, Creator creator) {
		super(particleProvider, creator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.AbstractIndividualFactory#create()
	 */
	@Override
	public Particle create() {
		return (Particle) super.create();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.AbstractIndividualFactory#create(org.opt4j.core.problem
	 * .Genotype)
	 */
	@Override
	public Particle create(Genotype position) {
		return (Particle) super.create(position);
	}

	/**
	 * Builds a {@link Particle} with the given id, position, and velocity.
	 * 
	 * @param id
	 *            the id
	 * @param position
	 *            the position
	 * @param velocity
	 *            the velocity
	 * @return the particle
	 */
	public Particle create(int id, Genotype position, Genotype velocity) {
		Particle particle = create(position);
		particle.setVelocity(velocity);
		particle.setId(id);
		return particle;
	}
}
