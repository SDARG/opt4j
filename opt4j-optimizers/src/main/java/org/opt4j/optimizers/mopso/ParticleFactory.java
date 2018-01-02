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
 
package org.opt4j.optimizers.mopso;

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
	@Inject
	public ParticleFactory(Provider<Particle> particleProvider, Creator<Genotype> creator) {
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
