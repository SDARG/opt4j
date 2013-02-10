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

import org.opt4j.common.archive.FrontDensityIndicator;
import org.opt4j.config.annotations.Info;
import org.opt4j.start.Constant;

/**
 * Module for the S-Metric Selection ({@link Selector}) based on the {@link Hypervolume} contribution.
 * 
 * @see "M. Emmerich, N. Beume, and B. Naujoks. An EMO Algorithm Using the
 *      Hypervolume Measure as Selection Criterion. EMO 2005."
 * @see Hypervolume
 * @author lukasiewycz
 * @author Ramin Etemaadi
 * 
 */
@Info("SMS-EMOA: Multiobjective selection based on dominated hypervolume")
public class SMSModule extends SelectorModule {

	@Info("The offset value")
	@Constant(value = "offset", namespace = Hypervolume.class)
	protected double offset = 1.0;
	
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
	
	/**
	 * Returns the offset value.
	 * 
	 * @return the offset value
	 */
	public double getOffset() {
		return offset;
	}
	
	/**
	 * Sets the offset value.
	 * 
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindSelector(Nsga2.class);
		bind(FrontDensityIndicator.class).to(Hypervolume.class);
	}

}
