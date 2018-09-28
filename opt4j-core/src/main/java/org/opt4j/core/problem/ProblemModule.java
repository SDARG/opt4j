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
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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

import java.util.HashSet;
import java.util.Set;

import org.opt4j.core.Genotype;
import org.opt4j.core.config.Icons;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.start.Opt4JModule;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

/**
 * The {@link ProblemModule} is an abstract module class for the binding of the
 * {@link Creator}, {@link Decoder}, and {@link Evaluator}.
 * 
 * @see Creator
 * @see Decoder
 * @see Evaluator
 * @author lukasiewycz
 * 
 */
@Icon(Icons.PROBLEM)
@Category
public abstract class ProblemModule extends Opt4JModule {

	/**
	 * <p>
	 * Binds a problem. A value {@code null} is allowed. In this case, the
	 * corresponding interface is not bound. Therefore, the binding for the
	 * omitted interfaces has to be done in other modules.
	 * </p>
	 * <p>
	 * Additional {@link Evaluator}s can be bound using
	 * {@link #addEvaluator(Class)}.
	 * </p>
	 * 
	 * 
	 * @param creator
	 *            the creator
	 * @param decoder
	 *            the decoder
	 * @param evaluator
	 *            the evaluator
	 */
	protected void bindProblem(final Class<? extends Creator<? extends Genotype>> creator,
			final Class<? extends Decoder<? extends Genotype, ? extends Object>> decoder,
			final Class<? extends Evaluator<? extends Object>> evaluator) {
		bindProblem(binder(), creator, decoder, evaluator);
	}

	/**
	 * Binds an additional {@link Evaluator}.
	 * 
	 * @param evaluator
	 *            the evaluator to use
	 */
	protected void addEvaluator(final Class<? extends Evaluator<? extends Object>> evaluator) {
		addEvaluator(binder(), evaluator);
	}

	/**
	 * Binds an additional {@link Evaluator} to a given {@link Binder}.
	 * 
	 * @param binder
	 *            the guice binder to use
	 * @param evaluator
	 *            the evaluator to use
	 * @see #binder()
	 */
	@SuppressWarnings({ "unchecked" })
	public static void addEvaluator(Binder binder, final Class<? extends Evaluator<? extends Object>> evaluator) {
		Multibinder<Evaluator<Object>> multibinder = Multibinder.newSetBinder(binder,
				new TypeLiteral<Evaluator<Object>>() {
				});
		multibinder.addBinding().to((Class<Evaluator<Object>>) evaluator);
	}

	/**
	 * <p>
	 * Binds a problem. A value {@code null} is allowed. In this case, the
	 * corresponding interface is not bound. Therefore, the binding for the
	 * omitted interfaces has to be done in other modules.
	 * </p>
	 * <p>
	 * Additional {@link Evaluator}s can be bound using
	 * {@link #addEvaluator(Class)}.
	 * </p>
	 * 
	 * @param binder
	 *            the guice binder
	 * @param creator
	 *            the creator
	 * @param decoder
	 *            the decoder
	 * @param evaluator
	 *            the evaluator
	 * @see #binder()
	 */
	@SuppressWarnings("unchecked")
	public static void bindProblem(Binder binder, final Class<? extends Creator<? extends Genotype>> creator,
			final Class<? extends Decoder<? extends Genotype, ? extends Object>> decoder,
			final Class<? extends Evaluator<? extends Object>> evaluator) {

		Set<Class<?>> classes = new HashSet<>();
		if (creator != null) {
			classes.add(creator);
		}
		if (decoder != null) {
			classes.add(decoder);
		}

		for (Class<?> clazz : classes) {
			binder.bind(clazz).in(SINGLETON);
		}

		if (creator != null) {
			binder.bind(new TypeLiteral<Creator<Genotype>>() {
			}).to((Class<? extends Creator<Genotype>>) creator);
		}
		if (decoder != null) {
			binder.bind(new TypeLiteral<Decoder<Genotype, Object>>() {
			}).to((Class<? extends Decoder<Genotype, Object>>) decoder);
		}
		if (evaluator != null) {
			addEvaluator(binder, evaluator);
		}
	}
}
