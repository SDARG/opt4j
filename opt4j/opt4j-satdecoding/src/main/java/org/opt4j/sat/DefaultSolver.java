/**
 * Opt4J is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * Opt4J is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Opt4J. If not, see http://www.gnu.org/licenses/.
 */

package org.opt4j.sat;

import org.opt4j.sat.sat4j.SAT4JSolver;

import com.google.inject.Singleton;

/**
 * The default SAT/PB solver is the {@link SAT4JSolver} with timeout 3600
 * seconds, fixed length learning with learning size 10, and MiniSAT restarts.
 * 
 * @author lukasiewycz
 * 
 */
@Singleton
public class DefaultSolver extends SAT4JSolver {

	/**
	 * Constructs a default solver.
	 */
	public DefaultSolver() {
		super(3600, 10, Learning.FIXEDLENGTH, Restarts.MINISAT);
	}
}
