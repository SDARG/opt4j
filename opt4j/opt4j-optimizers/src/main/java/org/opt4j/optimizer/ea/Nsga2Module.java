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

import org.opt4j.common.archive.Crowding;
import org.opt4j.common.archive.FrontDensityIndicator;
import org.opt4j.config.annotations.Info;
import org.opt4j.start.Constant;

/**
 * Module for the {@link Nsga2} {@link Selector}.
 * 
 * 
 * @see Nsga2
 * @author lukasiewycz
 * 
 */
@Info("A Fast Elitist Non-Dominated Sorting Genetic Algorithm for Multi-Objective Optimization")
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
