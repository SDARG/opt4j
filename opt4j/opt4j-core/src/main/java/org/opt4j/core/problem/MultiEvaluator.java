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

package org.opt4j.core.problem;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.opt4j.core.Objectives;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * The {@link MultiEvaluator} takes all registered {@link Evaluator}s and uses
 * them to evaluate the phenotype. Use {@link ProblemModule#addEvaluator(Class)}
 * to add additional {@link Evaluator} s.
 * 
 * The order of the {@link Evaluator}s can be controlled using the
 * {@link Priority} annotation for the {@link Evaluator} classes.
 * 
 * 
 * @author reimann, lukasiewycz
 * 
 */
public class MultiEvaluator implements Evaluator<Object> {

	protected final Set<Evaluator<Object>> evaluators = new TreeSet<Evaluator<Object>>(new PriorityComparator());
	protected final Provider<Objectives> objectivesProvider;

	/**
	 * Creates a new {@link MultiEvaluator}.
	 * 
	 * @param evaluators
	 *            the registered evaluators
	 * @param objectivesProvider
	 *            the objectives provider
	 */
	@Inject
	public MultiEvaluator(Set<Evaluator<Object>> evaluators, Provider<Objectives> objectivesProvider) {
		this.evaluators.addAll(evaluators);
		this.objectivesProvider = objectivesProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@Override
	public Objectives evaluate(Object phenotype) {
		Objectives objectives = objectivesProvider.get();
		for (Evaluator<Object> evaluator : evaluators) {
			Objectives obj = evaluator.evaluate(phenotype);
			objectives.addAll(obj);
		}
		return objectives;
	}

	private static class PriorityComparator implements Comparator<Evaluator<Object>> {

		@Override
		public int compare(Evaluator<Object> o1, Evaluator<Object> o2) {
			int p1 = getPriority(o1);
			int p2 = getPriority(o2);
			if (p1 == p2) {
				return o1.hashCode() - o2.hashCode();
			}
			return p1 - p2;
		}

		protected int getPriority(Evaluator<Object> evaluator) {
			if (evaluator.getClass().isAnnotationPresent(Priority.class)) {
				Priority priority = evaluator.getClass().getAnnotation(Priority.class);
				return priority.value();
			}
			return 0;
		}
	}
}
