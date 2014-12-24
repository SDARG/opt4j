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


package org.opt4j.core.common.completer;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.Objective;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link SequentialIndividualCompleter} completes the {@link Individual}s
 * sequentially.
 * </p>
 * <p>
 * It updates the {@link State} of the {@link Individual} according to the state
 * of the completion process. It uses {@link Control} between the different
 * (possibly time consuming) completion steps to allow the user to control the
 * completion process.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class SequentialIndividualCompleter implements IndividualCompleter {

	protected final Decoder<Genotype, Object> decoder;
	protected final Evaluator<Object> evaluator;
	protected final Control control;

	/**
	 * Constructs a {@link SequentialIndividualCompleter}.
	 * 
	 * @param control
	 *            the optimization control
	 * @param decoder
	 *            the decoder
	 * @param evaluator
	 *            the evaluator
	 */
	@Inject
	public SequentialIndividualCompleter(Control control, Decoder<Genotype, Object> decoder, Evaluator<Object> evaluator) {
		super();
		this.control = control;
		this.decoder = decoder;
		this.evaluator = evaluator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.Completer#complete(java.lang.Iterable)
	 */
	@Override
	public void complete(Iterable<? extends Individual> iterable) throws TerminationException {
		for (Individual individual : iterable) {
			if (!individual.isEvaluated()) {
				control.checkpoint();
				decode(individual);
				control.checkpoint();
				evaluate(individual);
				control.checkpoint();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.core.optimizer.Completer#complete(org.opt4j.core.Individual[])
	 */
	@Override
	public void complete(Individual... individuals) throws TerminationException {
		List<Individual> list = Arrays.asList(individuals);
		complete(list);
	}

	protected void evaluate(Individual individual) {
		State state = individual.getState();

		if (state == State.PHENOTYPED) {
			individual.setState(State.EVALUATING);
			Object phenotype = individual.getPhenotype();

			Objectives objectives = evaluator.evaluate(phenotype);
			assert isSameLength(objectives.getKeys()) : "Objectives changed: " + objectives.getKeys();

			individual.setObjectives(objectives);
		} else {
			throw new IllegalStateException("Cannot evaluate Individual, current state: " + state);
		}
	}

	protected void decode(Individual individual) {
		State state = individual.getState();

		if (state == State.GENOTYPED) {
			individual.setState(State.DECODING);
			Genotype genotype = individual.getGenotype();
			Object phenotype = decoder.decode(genotype);
			individual.setPhenotype(phenotype);
		} else {
			throw new IllegalStateException("Cannot decode Individual, current state: " + state);
		}
	}

	private Set<Objective> objectives = null;

	/**
	 * Check if the given {@link Objectives} have the same length as prior
	 * evaluated ones.
	 * 
	 * @param objectives
	 *            the objectives to check
	 * @return true if the number of objectives is constant
	 */
	private boolean isSameLength(Collection<Objective> objectives) {
		Set<Objective> set = new HashSet<Objective>(objectives);
		if (this.objectives == null) {
			this.objectives = set;
			return true;
		}
		return this.objectives.equals(set);
	}
}
