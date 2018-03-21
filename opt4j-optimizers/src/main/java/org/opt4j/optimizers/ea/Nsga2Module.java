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

import org.opt4j.core.common.archive.Crowding;
import org.opt4j.core.common.archive.FrontDensityIndicator;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.start.Constant;

/**
 * Module for the {@link Nsga2} {@link Selector}.
 * 
 * 
 * @see Nsga2
 * @author lukasiewycz
 * 
 */
@Info("A Fast Elitist Non-Dominated Sorting Genetic Algorithm for Multi-Objective Optimization")
@Citation(title = "A Fast Elitist Non-Dominated Sorting Genetic Algorithm for Multi-Objective Optimization: NSGA-II", authors = "Kalyanmoy Deb, Samir Agrawal, Amrit Pratap, and Tanaka Meyarivan", journal = "Parallel MockProblem Solving from Nature", pageFirst = 849, pageLast = 858, year = 2000, doi = "10.1007/3-540-45356-3_83", month = UNKNOWN)
public class Nsga2Module extends SelectorModule {

	@Info("The tournament value")
	@Constant(value = "tournament", namespace = Nsga2.class)
	protected int tournament = 0;

	/**
	 * Returns the tournament value.
	 * 
	 * @see #setTournament
	 * @return the tournament value
	 */
	public int getTournament() {
		return tournament;
	}

	/**
	 * Sets the tournament value.
	 * 
	 * @see #getTournament
	 * @param tournament
	 *            the tournament to set
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
		bindSelector(Nsga2.class);
		bind(FrontDensityIndicator.class).to(Crowding.class);
	}

}
