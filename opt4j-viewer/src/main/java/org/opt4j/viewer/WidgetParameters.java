/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
 

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
