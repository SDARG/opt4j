/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.common.random;

import java.util.Random;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Icon;
import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Required;
import org.opt4j.start.Opt4JModule;

/**
 * The {@link RandomModule} is used to configure the used random number
 * generator.
 * 
 * @author helwig, lukasiewycz
 */
@Icon(Icons.PUZZLE_BLUE)
@Info("Global random number generator for the optimization process.")
public class RandomModule extends Opt4JModule {

	@Info("Seed of the random number generator.")
	@Required(property = "usingSeed", elements = { "true" })
	protected long seed = 0;

	@Info("Use a specific seed.")
	protected boolean usingSeed = true;

	@Info("Type of the random number generator.")
	protected RandType type = RandType.MERSENNE_TWISTER;

	/**
	 * The {@link RandType}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum RandType {
		/**
		 * Use the standard Java random number generator.
		 * 
		 * @see RandomJava
		 */
		@Info("Use the standard java Random")
		JAVA,

		/**
		 * Use the mersenne twister random number generator.
		 * 
		 * @see RandomMersenneTwister
		 */
		@Info("Use the mersenne twister random number generator")
		MERSENNE_TWISTER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		Class<? extends Rand> randomClass;
		switch (type) {
		case JAVA:
			randomClass = RandomJava.class;
			break;
		default: // MERSENNE TWISTER
			randomClass = RandomMersenneTwister.class;
			break;
		}

		long seed = this.seed;
		if (!usingSeed) {
			seed = System.currentTimeMillis();
		}
		bindConstant("seed", Random.class).to(seed);

		bind(Rand.class).to(randomClass).in(SINGLETON);
	}

	/**
	 * Sets the seed that is used by the random number generator.
	 * 
	 * @see #getSeed
	 * @param seed
	 *            the seed that is used by the random number generator
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Returns the seed the is used by the random number generator.
	 * 
	 * @see #setSeed
	 * @return the seed the is used by the random number generator
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Sets the type of the random number generator to the specified value.
	 * 
	 * @see #getType
	 * @param type
	 *            the type of the random number generator
	 */
	public void setType(RandType type) {
		this.type = type;
	}

	/**
	 * Returns the type of the random number generator.
	 * 
	 * @see #setType
	 * @return the type of the random number generator
	 */
	public RandType getType() {
		return type;
	}

	/**
	 * Returns {@code true} if a specific seed is given.
	 * 
	 * @see #setUsingSeed
	 * @return the useSeed
	 */
	public boolean isUsingSeed() {
		return usingSeed;
	}

	/**
	 * Select if a specific seed should be used.
	 * 
	 * @see #isUsingSeed
	 * @param value
	 *            {@code true} if a seed shall be used, {@code false} otherwise
	 */
	public void setUsingSeed(boolean value) {
		this.usingSeed = value;
	}

}
