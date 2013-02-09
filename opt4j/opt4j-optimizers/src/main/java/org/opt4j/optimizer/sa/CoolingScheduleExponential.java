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

package org.opt4j.optimizer.sa;

import org.opt4j.start.Constant;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link CoolingScheduleExponential} is a {@link CoolingSchedule} for the
 * {@link SimulatedAnnealing}.
 * </p>
 * <p>
 * {@code tn - final temperature}<br />
 * {@code t0 - initial temperature}<br />
 * {@code i - current iteration}<br />
 * {@code n - maximal number of iterations} <br />
 * {@code a - alpha value}<br />
 * </p>
 * <p>
 * The current temperature is calculated by {@code tn + t0 * a^i}.
 * </p>
 * 
 * 
 * 
 * @author lukasiewycz
 * 
 */
public class CoolingScheduleExponential implements CoolingSchedule {

	protected final double t0;

	protected final double tn;

	protected final double alpha;

	/**
	 * Constructs a new {@link CoolingScheduleExponential}.
	 * 
	 * @param t0
	 *            the initial temperature (using namespace
	 *            {@link CoolingSchedule})
	 * @param tn
	 *            the final temperature (using namespace {@link CoolingSchedule}
	 *            ) )
	 * @param alpha
	 *            the alpha value
	 */
	@Inject
	public CoolingScheduleExponential(@Constant(value = "t0", namespace = CoolingSchedule.class) double t0,
			@Constant(value = "tn", namespace = CoolingSchedule.class) double tn,
			@Constant(value = "alpha", namespace = CoolingScheduleExponential.class) double alpha) {
		this.t0 = t0;
		this.tn = tn;
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.sa.CoolingSchedule#getTemperature(int, int)
	 */
	@Override
	public double getTemperature(int i, int n) {
		return tn + t0 * Math.pow(alpha, i);
	}

}
