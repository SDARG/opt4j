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
