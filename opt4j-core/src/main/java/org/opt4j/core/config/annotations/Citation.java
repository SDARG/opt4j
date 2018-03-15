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
 * The {@link Citation} can be used in {@link Opt4JModule}s to add citations to
 * scientific works.
 * 
 * Example:
 * 
 * <pre>
 * &#64;Citation(title     = "Opt4J: A Modular Framework for Meta-heuristic Optimization",
 *           authors   = "Martin Lukasiewycz, Michael Gla&szlig;, Felix Reimann, and J&uuml;rgen Teich",
 *           journal   = "Proceedings of the 13th Annual Conference on Genetic and Evolutionary Computation",
 *           pageFirst = 1723
 *           pageLast  = 1730
 *           volume    = 13
 *           number    = 1
 *           month     = PublicationMonth.JULY,
 *           year      = 2011,
 *           doi       = "10.1145/2001576.2001808")
 * </pre>
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
			case MARCH:
				return Month.MARCH;
			case APRIL:
				return Month.APRIL;
			case MAY:
				return Month.MAY;
			case JUNE:
				return Month.JUNE;
			case JULY:
				return Month.JULY;
			case AUGUST:
				return Month.AUGUST;
			case SEPTEMBER:
				return Month.SEPTEMBER;
			case OCTOBER:
				return Month.OCTOBER;
			case NOVEMBER:
				return Month.NOVEMBER;
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

	/**
	 * The authors of the work.
	 * 
	 * @return the authors
	 */
	String authors() default "";

	/**
	 * The volume of the journal.
	 * 
	 * @return the volume
	 */
	int volume() default -1;

	/**
	 * The number of the journal issue.
	 * 
	 * @return the number
	 */
	int number() default -1;

	/**
	 * The month, in which the paper has been published.
	 * 
	 * @return the month
	 */
	PublicationMonth month();

	/**
	 * The year, in which the paper has been published.
	 * 
	 * @return the year
	 */
	int year();

	/**
	 * The journal, in which the paper has been published.
	 * 
	 * @return the title of the journal
	 */
	String journal() default "";

	/**
	 * The number of the page, where the paper starts.
	 * 
	 * @return the first page
	 */
	int pageFirst() default -1;

	/**
	 * The number of the page, where the paper ends.
	 * 
	 * @return the last page
	 */
	int pageLast() default -1;

	/**
	 * The Digital Object Identifier of the work.
	 * 
	 * @return the doi
	 */
	String doi() default "";
}
