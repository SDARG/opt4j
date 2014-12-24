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


package org.opt4j.core.common.random;

import java.util.Random;

import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Opt4JModule;

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
