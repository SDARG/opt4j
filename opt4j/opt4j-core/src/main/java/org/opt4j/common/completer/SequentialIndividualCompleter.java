/**
 * Opt4J is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with Opt4J. If not, see
 * http://www.gnu.org/licenses/.
 */

package org.opt4j.common.completer;

import java.util.Arrays;
import java.util.List;

import org.opt4j.core.Genotype;
import org.opt4j.core.Individual;
import org.opt4j.core.Individual.State;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.Control;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link SequentialIndividualCompleter} completes the {@link Individual}s sequentially.
 * </p>
 * <p>
 * It updates the {@link State} of the {@link Individual} according to the state of the completion process. It uses
 * {@link Control} between the different (possibly time consuming) completion steps to allow the user to control the
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
	public SequentialIndividualCompleter(Control control, Decoder<Genotype, Object> decoder,
			Evaluator<Object> evaluator) {
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
	 * @see org.opt4j.core.optimizer.Completer#complete(org.opt4j.core.Individual[])
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

}
