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

import org.opt4j.config.annotations.Info;
import org.opt4j.config.annotations.Order;
import org.opt4j.config.annotations.Parent;
import org.opt4j.config.annotations.Required;
import org.opt4j.start.Constant;

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
	 * Constructs a {@link CoolingSchedulesModule}.
	 * 
	 */
	public CoolingSchedulesModule() {
		super();
	}

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
