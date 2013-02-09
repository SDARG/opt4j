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
package org.opt4j.operator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.Operator;

/**
 * The {@link Apply} annotation is used for each {@link Operator} to indicate on
 * which {@link Genotype} it is applied.
 * 
 * @author lukasiewycz
 * 
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface Apply {

	/**
	 * Returns the type of {@link Genotype} on which the specific
	 * {@link Operator} is applied.
	 * 
	 * @return the genotype class
	 */
	Class<? extends Genotype> value();

}
