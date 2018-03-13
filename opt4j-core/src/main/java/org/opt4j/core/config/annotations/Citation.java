package org.opt4j.core.config.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.Month;

import org.opt4j.core.start.Opt4JModule;

/**
 * The {@link Citation} can be used in {@link Opt4JModule}s to add citations to scientific works.
 * 
 * @author Felix Reimann
 *
 */
@Retention(RUNTIME)
@Target({ METHOD, TYPE, FIELD })
public @interface Citation {
	/**
	 * The title.
	 * 
	 * @return the title of the citation
	 */
	String title();

	String authors() default "";

	int volume() default -1;

	int number() default -1;

	/**
	 * The month, in which the paper has been published.
	 * 
	 * @return
	 */
	Month month();

	/**
	 * Set to {@code true} if the month the paper has been published is unknown.
	 * 
	 * @return true, if {@link #month()} is invalid.
	 */
	boolean noMonth() default false;

	int year();

	String journal() default "";

	int pageFirst() default -1;

	int pageLast() default -1;
}
