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
