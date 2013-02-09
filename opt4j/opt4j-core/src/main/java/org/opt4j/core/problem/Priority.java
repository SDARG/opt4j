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
