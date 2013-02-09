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
package org.opt4j.core.optimizer;

/**
 * The {@link OptimizerStateListener} is used to monitor the state of the
 * {@link Optimizer}. To add an {@link OptimizerStateListener} use the method
 * {@link org.opt4j.start.Opt4JModule#addOptimizerStateListener(Class)}.
 * 
 * @author lukasiewycz
 * 
 */
public interface OptimizerStateListener {

	/**
	 * Invoked if the {@link Optimizer} starts the optimization process.
	 * 
	 * @param optimizer
	 *            the optimizer
	 */
	public void optimizationStarted(Optimizer optimizer);

	/**
	 * Invoked if the {@link Optimizer} stops the optimization process.
	 * 
	 * @param optimizer
	 *            the optimizer
	 */
	public void optimizationStopped(Optimizer optimizer);

}
