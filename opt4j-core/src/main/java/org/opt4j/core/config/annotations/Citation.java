package org.opt4j.core.config.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.DateTimeException;
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
@Documented
public @interface Citation {
	enum PublicationMonth {
		UNKNOWN, JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

		public Month toMonth() {
			switch (this) {
			case JANUARY:
				return Month.JANUARY;
			case FEBRUARY:
				return Month.FEBRUARY;
			case APRIL:
				return Month.APRIL;
			case AUGUST:
				return Month.AUGUST;
			case JULY:
				return Month.JULY;
			case JUNE:
				return Month.JUNE;
			case MARCH:
				return Month.MARCH;
			case MAY:
				return Month.MAY;
			case NOVEMBER:
				return Month.NOVEMBER;
			case OCTOBER:
				return Month.OCTOBER;
			case SEPTEMBER:
				return Month.SEPTEMBER;
			case DECEMBER:
				return Month.DECEMBER;
			default:
				throw new DateTimeException("publication month is unkown");
			}
		}
	}

	/**
	 * The title.
	 * 
	 * @return the title of the citation
	 */
	String title();

	String authors()

	default "";

	int volume()

	default -1;

	int number()

	default -1;

	/**
	 * The month, in which the paper has been published.
	 * 
	 * @return the month
	 */
	PublicationMonth month();

	int year();

	String journal()

	default "";

	int pageFirst()

	default -1;

	int pageLast() default -1;
}
