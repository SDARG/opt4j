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

/**
 * <p>
 * Provides the classes for the random number generators.
 * </p>
 * <p>
 * Meta-heuristic algorithms rely on a certain degree of randomness. This
 * package provides an interface (an abstract class) Rand for random number
 * generators. The default random number generator is the MersenneTwister.
 * </p>
 * <p>
 * This interface should be used in all operators and algorithms such that an
 * optimization run with the same seed of the random number generator should
 * produce the same result. Keep in mind that this reproducibility might be
 * violated by, e.g., multi-threading or non-deterministic objects like Sets.
 * </p>
 */
package org.opt4j.common.random;