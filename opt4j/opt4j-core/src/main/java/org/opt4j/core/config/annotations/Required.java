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

package org.opt4j.core.config.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The {@link Required} annotation defines dependencies between the properties.
 * 
 * @author lukasiewycz
 * 
 */
@Retention(RUNTIME)
@Target({ METHOD, FIELD })
public @interface Required {

	/**
	 * The addressed property. This property has to be an {@code enum} type.
	 * 
	 * @return the required property
	 */
	String property() default "";

	/**
	 * A list of the elements that are allowed to activate the current property.
	 * 
	 * @return allowed elements
	 */
	String[] elements() default {};

	/**
	 * The boolean value that is necessary to activate the current property.
	 * 
	 * @return boolean value
	 */
	boolean value() default true;
}
