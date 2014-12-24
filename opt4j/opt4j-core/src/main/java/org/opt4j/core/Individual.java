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


package org.opt4j.core;

import java.util.Set;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link Individual} class forms a single solution for the given
 * optimization problem.
 * </p>
 * <p>
 * An {@link Individual} contains the {@link Genotype}, phenotype {@link Object}
 * , and {@link Objectives}: Initially, the {@link Individual} contains only a
 * {@link Genotype}. The {@link org.opt4j.core.problem.Decoder} decodes the
 * {@link Genotype} into a phenotype and adds it to the {@link Individual}.
 * Finally, the phenotype is evaluated and the resulting {@link Objectives} are
 * added to the {@link Individual}.
 * </p>
 * 
 * @see Genotype
 * @see Objectives
 * @author glass, lukasiewycz
 */
public class Individual {

	protected Genotype genotype;

	protected Object phenotype;

	protected Objectives objectives;

	protected Set<IndividualStateListener> individualStateListeners;

	protected State state = State.EMPTY;

	/**
	 * The possible states of an {@link Individual}.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum State {
		/**
		 * Initial state.
		 */
		EMPTY("empty", false, false, false),
		/**
		 * Individual has a {@link Genotype}.
		 */
		GENOTYPED("genotyped", false, false, false),
		/**
		 * Individual is currently being decoded.
		 */
		DECODING("decoding", false, false, true),
		/**
		 * Individual has a Phenotype, i.e. it is decoded.
		 */
		PHENOTYPED("phenotyped", true, false, false),
		/**
		 * Individual is currently being evaluated.
		 */
		EVALUATING("evaluating", true, false, true),
		/**
		 * Individual is evaluated.
		 */
		EVALUATED("evaluated", true, true, false);

		private final String name;

		private final boolean decoded;

		private final boolean evaluated;

		private final boolean processing;

		/**
		 * Defines a new State
		 * 
		 * @param name
		 *            the full name of the state
		 * @param decoded
		 *            {@code true} if the individual is decoded in this state
		 * @param evaluated
		 *            {@code true} if the individual is evaluated in this state
		 * @param processing
		 *            {@code true} if the individual is processing in this state
		 */
		State(String name, boolean decoded, boolean evaluated, boolean processing) {
			this.name = name;
			this.decoded = decoded;
			this.evaluated = evaluated;
			this.processing = processing;
		}

		/**
		 * Returns the specific name of the state.
		 * 
		 * @return the specific name of the state
		 */
		@Override
		public String toString() {
			return name;
		}

		/**
		 * Returns {@code true} if the individual is decoded in the current
		 * state.
		 * 
		 * @return {@code true} if the individual is decoded in the current
		 *         state
		 */
		public boolean isDecoded() {
			return decoded;
		}

		/**
		 * Returns {@code true} if the individual is evaluated in the current
		 * state.
		 * 
		 * @return {@code true} if the individual is evaluated in the current
		 *         state
		 */
		public boolean isEvaluated() {
			return evaluated;
		}

		/**
		 * Returns {@code true} if the individual is processing in the current
		 * state.
		 * 
		 * @return {@code true} if the individual is processing in the current
		 *         state
		 */
		public boolean isProcessing() {
			return processing;
		}

	}

	/**
	 * Constructs an {@link Individual}.
	 */
	@Inject
	protected Individual() {
		super();
	}

	/**
	 * Returns the phenotype.
	 * 
	 * @see #setPhenotype
	 * @return the phenotype
	 */
	public Object getPhenotype() {
		return phenotype;
	}

	/**
	 * Returns the objectives.
	 * 
	 * @see #setObjectives
	 * @return the objectives
	 */
	public Objectives getObjectives() {
		return objectives;
	}

	/**
	 * Returns the genotype.
	 * 
	 * @see #setGenotype
	 * @return the genotype
	 */
	public Genotype getGenotype() {
		return genotype;
	}

	/**
	 * Sets the genotype.
	 * 
	 * @see #getGenotype
	 * @param genotype
	 *            the genotype to be set
	 */
	public void setGenotype(Genotype genotype) {
		this.genotype = genotype;
		setState(State.GENOTYPED);
	}

	/**
	 * Sets the phenotype.
	 * 
	 * @see #getPhenotype
	 * @param phenotype
	 *            the phenotype to be set
	 */
	public void setPhenotype(Object phenotype) {
		this.phenotype = phenotype;
		setState(State.PHENOTYPED);
	}

	/**
	 * Sets the objectives.
	 * 
	 * @see #getObjectives
	 * @param objectives
	 *            the objectives to be set
	 */
	public void setObjectives(Objectives objectives) {
		this.objectives = objectives;
		setState(State.EVALUATED);
	}

	/**
	 * Indicates whether this {@link Individual} is already decoded.
	 * 
	 * @return {@code true} if this individual is decoded
	 */
	public boolean isDecoded() {
		return state.isDecoded();
	}

	/**
	 * Indicates whether this {@link Individual} is already evaluated.
	 * 
	 * @return {@code true} if this inividual is evaluated
	 */
	public boolean isEvaluated() {
		return state.isEvaluated();
	}

	/**
	 * Sets the state of the {@link Individual}.
	 * 
	 * @see #getState
	 * @param state
	 *            the new status
	 */
	public void setState(State state) {
		if (this.state != state) {
			this.state = state;

			if (individualStateListeners != null) {
				for (IndividualStateListener listener : individualStateListeners) {
					listener.inidividualStateChanged(this);
				}
			}
		}
	}

	/**
	 * Returns the {@link State} of the {@link Individual}.
	 * 
	 * @see #setState
	 * @return the current state
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the list of {@link IndividualStateListener}s that are called if the
	 * {@link State} of this individual changes.
	 * 
	 * @param individualStateListeners
	 *            the listener for a changing status.
	 */
	protected void setIndividualStatusListeners(Set<IndividualStateListener> individualStateListeners) {
		this.individualStateListeners = individualStateListeners;
	}
}
