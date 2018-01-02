 

/**
 * <p>
 * The tutorial provides three examples for user-defined problems, one example
 * for an {@link org.opt4j.core.optimizer.Optimizer} implementation, and one
 * example for a new {@link org.opt4j.core.optimizer.Operator}.
 * </p>
 * <p>
 * The {@code org.opt4j.tutorial.helloworld} example is a simple problem which
 * shows the implementation of the {@link org.opt4j.core.problem.Decoder},
 * {@link org.opt4j.core.problem.Evaluator} and
 * {@link org.opt4j.core.problem.Creator} interfaces which is the minimum
 * requirement for own optimization problems.
 * </p>
 * <p>
 * The {@code org.opt4j.tutorial.salesman} example implements the <a
 * href="http://en.wikipedia.org/wiki/Traveling_salesman_problem">Traveling
 * Salesman Problem</a>. A problem-specific GUI is added.
 * </p>
 * <p>
 * The {@code org.opt4j.tutorial.minones} example is a simple problem which uses
 * SAT decoding to hand infeasible individuals.
 * </p>
 * <p>
 * The {@code org.opt4j.tutorial.optimizer} example shows the implementation of
 * a simple optimizer which uses the {@link org.opt4j.operators.mutate.Mutate}
 * operator.
 * </p>
 * <p>
 * The {@code org.opt4j.tutorial.operator} example shows how to add own
 * {@link org.opt4j.core.optimizer.Operator}s for the modification of
 * {@link org.opt4j.core.Genotype}s.
 * </p>
 */
package org.opt4j.tutorial;

