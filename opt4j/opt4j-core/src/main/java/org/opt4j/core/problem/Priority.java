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

package org.opt4j.core.problem;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The lower {@link Priority#value()} of an {@link Evaluator}, the earlier the
 * {@link MultiEvaluator} will call it.
 * 
 * If two {@link Evaluator}s have the same priority, the order of their
 * execution is unspecified.
 * 
 * @author reimann
 * 
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Priority {
	/**
	 * Returns the priority of the {@link Evaluator}.
	 * 
	 * @return the priority of the evaluator
	 */
	int value() default 0;
}
