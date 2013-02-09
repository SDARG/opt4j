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
 * The {@link StopException} is thrown if the optimization is stopped. Note that
 * this exception is not thrown manually, instead the {@link Control#doStop()}
 * method ensures that a {@link StopException} is thrown at the next checkpoint
 * (holds only for {@link Control#checkpointStop()}).
 * 
 * @see Control
 * @author lukasiewycz
 * 
 */
@SuppressWarnings("serial")
public class StopException extends Exception {

}
