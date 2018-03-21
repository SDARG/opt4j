/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/

package org.opt4j.optimizers.ea;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.UNKNOWN;

import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.start.Constant;

/**
 * The {@link Spea2Module} configures the {@link Spea2} selector.
 * 
 * @author lukasiewycz
 * 
 */
@Info("The Strength Pareto Evolutionary Algorithm (SPEA II).")
@Citation(title = "SPEA2: Improving the Strength Pareto Evolutionary Algorithm For Multiobjective Optimization", authors = "Eckart Zitzler, Marco Laumanns, and Lothar Thiele", journal = "Evolutionary Methods for Design, Optimisation, and Control", pageFirst = 19, pageLast = 26, year = 2002, month = UNKNOWN)
public class Spea2Module extends SelectorModule {

	@Info("The tournament value (number of opponents for the mating selection).")
	@Constant(value = "tournament", namespace = Spea2.class)
	protected int tournament = 0;

	/**
	 * Returns the number of tournaments.
	 * 
	 * @see #setTournament
	 * @return the number of tournaments
	 */
	public int getTournament() {
		return tournament;
	}

	/**
	 * Sets the number of tournaments.
	 * 
	 * @see #getTournament
	 * @param tournament
	 *            the number of tournaments
	 */
	public void setTournament(int tournament) {
		this.tournament = tournament;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindSelector(Spea2.class);
	}
}
