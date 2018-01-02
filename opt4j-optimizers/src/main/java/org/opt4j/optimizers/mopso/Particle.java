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

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;

import com.google.inject.Inject;

/**
 * The {@link Particle} extends the {@link Individual} by an id, a velocity
 * {@link Genotype}, a personal best {@link Genotype} and the corresponding best
 * {@link Objectives}.
 * 
 * @author lukasiewycz
 * 
 */
public class Particle extends Individual {

	protected int id;

	protected Genotype velocity = null;

	protected Genotype best = null;

	protected Objectives objectivesBest = null;

	/**
	 * Constructs a {@link Particle}.
	 */
	@Inject
	public Particle() {
		super();
	}

	/**
	 * Returns the velocity.
	 * 
	 * @see #setVelocity
	 * @return the velocity
	 */
	public Genotype getVelocity() {
		return velocity;
	}

	/**
	 * Sets the velocity.
	 * 
	 * @see #getVelocity
	 * @param velocity
	 *            the velocity to set
	 */
	public void setVelocity(Genotype velocity) {
		this.velocity = velocity;
	}

	/**
	 * Returns the best position.
	 * 
	 * @see #setBest
	 * @return the best
	 */
	public Genotype getBest() {
		return best;
	}

	/**
	 * Returns the best objectives.
	 * 
	 * @see #setBest
	 * @return the best objectives
	 */
	public Objectives getBestObjectives() {
		return objectivesBest;
	}

	/**
	 * Sets the best position.
	 * 
	 * @see #getBest
	 * @param best
	 *            the best to set
	 * @param objectives
	 *            the corresponding best objectives
	 */
	public void setBest(Genotype best, Objectives objectives) {
		this.best = best;
		this.objectivesBest = objectives;
	}

	/**
	 * Returns the id.
	 * 
	 * @see #setId
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @see #getId
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
