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
 

package org.opt4j.optimizers.ea;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Parent;

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
