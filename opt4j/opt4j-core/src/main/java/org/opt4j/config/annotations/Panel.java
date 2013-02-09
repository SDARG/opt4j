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

package org.opt4j.config.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.opt4j.config.visualization.PropertyPanel;

/**
 * The {@link Panel} annotation allows the definition of a custom panel for a
 * module.
 * 
 * @author lukasiewycz
 * 
 */
@Retention(RUNTIME)
@Target({ TYPE })
public @interface Panel {

	/**
	 * Returns the parent class.
	 * 
	 * @return the parent class
	 */
	Class<? extends PropertyPanel> value();
}
