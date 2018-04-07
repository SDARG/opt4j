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

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.start.Constant;

/**
 * This module provides several common {@link CoolingSchedule}s for the
 * {@link SimulatedAnnealing}.
 * 
 * @author lukasiewycz
 * 
 */
@Info("Common Cooling Schedules")
@Parent(SimulatedAnnealingModule.class)
public class CoolingSchedulesModule extends CoolingScheduleModule {

	@Constant(value = "t0", namespace = CoolingSchedule.class)
	protected double initialTemperature = 10.0;

	@Constant(value = "tn", namespace = CoolingSchedule.class)
	protected double finalTemperature = 1.0;

	@Required(property = "type", elements = { "EXPONENTIAL" })
	@Constant(value = "alpha", namespace = CoolingScheduleExponential.class)
	protected double alpha = 0.995;

	@Order(0)
	protected Type type = Type.LINEAR;

	/**
	 * Type of {@link CoolingSchedule} to use.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public enum Type {

		/**
		 * Use the {@link CoolingScheduleLinear}.
		 */
		LINEAR,
		/**
		 * Use the {@link CoolingScheduleHyperbolic}.
		 */
		HYPERBOLIC,
		/**
		 * Use the {@link CoolingScheduleExponential}.
		 */
		EXPONENTIAL;
	}

	/**
	 * Returns the final temperature.
	 * 
	 * @see #setFinalTemperature
	 * @return the final temperature
	 */
	@Info("The temperature at the end of the optimization")
	public double getFinalTemperature() {
		return finalTemperature;
	}

	/**
	 * Sets the final temperature.
	 * 
	 * @see #getFinalTemperature
	 * @param finalTemperature
	 *            the final temperature
	 */
	public void setFinalTemperature(double finalTemperature) {
		this.finalTemperature = finalTemperature;
	}

	/**
	 * Returns the initial temperature.
	 * 
	 * @see #setInitialTemperature
	 * @return the initial temperature
	 */
	@Info("The temperature at the beginning of the optimization")
	public double getInitialTemperature() {
		return initialTemperature;
	}

	/**
	 * Sets the initial temperature.
	 * 
	 * @see #getInitialTemperature
	 * @param initialTemperature
	 *            the initial temperature
	 */
	public void setInitialTemperature(double initialTemperature) {
		this.initialTemperature = initialTemperature;
	}

	/**
	 * Returns the type of cooling schedule.
	 * 
	 * @see #setType
	 * @return the type of cooling schedule
	 */
	@Info("The type of the cooling schedule")
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type of cooling schedule.
	 * 
	 * @see #getType
	 * @param type
	 *            the type of cooling schedule
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Returns the alpha value.
	 * 
	 * @see #setAlpha
	 * @return the alpha value
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha value.
	 * 
	 * @see #getAlpha
	 * @param alpha
	 *            the alpha value
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {

		switch (type) {
		case LINEAR:
			bindCoolingSchedule(CoolingScheduleLinear.class);
			break;
		case HYPERBOLIC:
			bindCoolingSchedule(CoolingScheduleHyperbolic.class);
			break;
		default: // EXPONENTIAL
			bindCoolingSchedule(CoolingScheduleExponential.class);
			break;
		}
	}

}
