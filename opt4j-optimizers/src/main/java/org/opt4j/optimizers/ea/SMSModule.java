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

import org.opt4j.core.common.archive.FrontDensityIndicator;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.start.Constant;

/**
 * Module for the S-Metric Selection ({@link Selector}) based on the {@link Hypervolume} contribution, see Emmerich et
 * al. 2005.
 * 
 * @see Hypervolume
 * @author lukasiewycz
 * @author Ramin Etemaadi
 * 
 */
@Info("SMS-EMOA: Multiobjective selection based on dominated hypervolume")
@Citation(authors = "Michael Emmerich, Nicola Beume, and Boris Naujoks", title = "An EMO Algorithm Using the Hypervolume Measure as Selection Criterion", journal = "Evolutionary Multi-Criterion Optimization (EMO)", pageFirst = 62, pageLast = 76, year = 2005, month = UNKNOWN)
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
