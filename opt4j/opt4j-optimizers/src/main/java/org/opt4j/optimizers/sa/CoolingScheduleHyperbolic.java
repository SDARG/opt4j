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
 

package org.opt4j.optimizers.sa;

import org.opt4j.core.start.Constant;

import com.google.inject.Inject;

/**
 * <p>
 * The {@link CoolingScheduleHyperbolic} is a {@link CoolingSchedule} for the
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
 * The current temperature is calculated by {@code t0 * (tn / t0)^(i/n)}.
 * </p>
 * 
 * @author lukasiewycz
 * 
 */
public class CoolingScheduleHyperbolic implements CoolingSchedule {

	protected final double t0;

	protected final double tn;

	/**
	 * Constructs a new {@link CoolingScheduleHyperbolic}.
	 * 
	 * @param t0
	 *            the initial temperature (using namespace
	 *            {@link CoolingSchedule})
	 * @param tn
	 *            the final temperature (using namespace {@link CoolingSchedule}
	 *            )
	 */
	@Inject
	public CoolingScheduleHyperbolic(@Constant(value = "t0", namespace = CoolingSchedule.class) double t0,
			@Constant(value = "tn", namespace = CoolingSchedule.class) double tn) {
		this.t0 = t0;
		this.tn = tn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.optimizer.sa.CoolingSchedule#getTemperature(int, int)
	 */
	@Override
	public double getTemperature(int i, int n) {
		return t0 * Math.pow((tn / t0), (double) i / (double) n);
	}

}
