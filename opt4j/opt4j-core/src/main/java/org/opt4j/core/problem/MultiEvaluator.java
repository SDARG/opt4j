package org.opt4j.core.problem;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.opt4j.core.Objectives;
import org.opt4j.core.Phenotype;

import com.google.inject.Inject;

/**
 * The {@link MultiEvaluator} takes all registered {@link Evaluator}s and uses them to evaluate the {@link Phenotype}.
 * Use {@link ProblemModule#addEvaluator(Class)} to add additional {@link Evaluator}s.
 * 
 * The order of the {@link Evaluator}s can be controlled using the {@link Priority} annotation for the {@link Evaluator}
 * classes.
 * 
 * 
 * @author reimann
 * 
 */
@SuppressWarnings("rawtypes")
public class MultiEvaluator implements Evaluator {
	protected final Set<Evaluator> evaluators = new TreeSet<Evaluator>(new PriorityComparator());

	/**
	 * Creates a new {@link MultiEvaluator}.
	 * 
	 * @param evaluators
	 *            the registered evaluators
	 */
	@Inject
	public MultiEvaluator(Set<Evaluator> evaluators) {
		this.evaluators.addAll(evaluators);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.problem.Evaluator#evaluate(org.opt4j.core.Phenotype)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Objectives evaluate(Phenotype phenotype) {
		Objectives objectives = new Objectives();
		for (Evaluator evaluator : evaluators) {
			Objectives obj = evaluator.evaluate(phenotype);
			objectives.addAll(obj);
		}
		return objectives;
	}

	private static class PriorityComparator implements Comparator<Evaluator> {

		@Override
		public int compare(Evaluator o1, Evaluator o2) {
			int p1 = getPriority(o1);
			int p2 = getPriority(o2);
			if (p1 == p2) {
				return o1.hashCode() - o2.hashCode();
			}
			return p1 - p2;
		}

		protected int getPriority(Evaluator evaluator) {
			if (evaluator.getClass().isAnnotationPresent(Priority.class)) {
				Priority priority = evaluator.getClass().getAnnotation(Priority.class);
				return priority.value();
			}
			return 0;
		}
	}
}
