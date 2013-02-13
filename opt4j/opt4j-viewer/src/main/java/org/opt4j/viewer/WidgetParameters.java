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

package org.opt4j.viewer;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The {@link WidgetParameters}.
 * 
 * @see Widget
 * @author lukasiewycz
 * 
 */
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface WidgetParameters {

	/**
	 * Returns the title of the frame.
	 * 
	 * @return the title of the frame
	 */
	public String title() default "Widget";

	/**
	 * Returns the icon of the frame.
	 * 
	 * @return the icon of the frame
	 */
	public String icon() default "";

	/**
	 * Returns {@code true} if the widget is resizable.
	 * 
	 * @return {@code true} if the widget is resizable.
	 */
	public boolean resizable() default true;

	/**
	 * Returns {@code true} if the widget is closable.
	 * 
	 * @return {@code true} if the widget is closable
	 */
	public boolean closable() default true;

	/**
	 * Returns {@code true} if the widget is maximizable.
	 * 
	 * @return {@code true} if the widget is maximizable
	 */
	public boolean maximizable() default true;

	/**
	 * Returns {@code true} if the widget is iconifiable.
	 * 
	 * @return {@code true} if the widget is iconifiable
	 */
	public boolean iconifiable() default true;

}
