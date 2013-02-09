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

import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Parent;

/**
 * The {@link BasicMatingModule} is the basic property module for the
 * {@link Mating} and {@link Coupler}.
 * 
 * @author glass
 * 
 */
@Parent(EvolutionaryAlgorithmModule.class)
@Info("Basic strategies to determine couples from the set of parents for a crossover.")
public class BasicMatingModule extends MatingModule {

	@Info("The type of couple operation")
	protected CouplerType type = CouplerType.DEFAULT;

	/**
	 * The {@link CouplerType} determines the coupler operator to use.
	 * 
	 * @author glass
	 * 
	 */
	public enum CouplerType {
		/**
		 * Use the {@link CouplerDefault} operator.
		 */
		DEFAULT,
		/**
		 * Use the {@link CouplerRandom} operator.
		 */
		RANDOM,
		/**
		 * Use the {@link CouplerUnique} operator.
		 */
		UNIQUE;
	}

	/**
	 * Constructs a {@link BasicMatingModule}.
	 * 
	 */
	public BasicMatingModule() {
		super();
	}

	/**
	 * Returns the type of {@link Coupler} operator to use.
	 * 
	 * @see #setType
	 * @return the the type of coupler operator to use
	 */
	public CouplerType getType() {
		return type;
	}

	/**
	 * Sets the the type of {@link Coupler} operator to use.
	 * 
	 * @see #getType
	 * @param type
	 *            the type to set
	 */
	public void setType(CouplerType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		switch (type) {
		case RANDOM:
			bind(Coupler.class).to(CouplerRandom.class).in(SINGLETON);
			break;
		case UNIQUE:
			bind(Coupler.class).to(CouplerUnique.class).in(SINGLETON);
			break;
		default: // DEFAULT
			bind(Coupler.class).to(CouplerDefault.class).in(SINGLETON);
			break;
		}
	}
}
