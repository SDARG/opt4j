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

import java.util.HashSet;
import java.util.Set;

import org.opt4j.config.Icons;
import org.opt4j.config.annotations.Category;
import org.opt4j.config.annotations.Icon;
import org.opt4j.core.Genotype;
import org.opt4j.start.Opt4JModule;

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
	@SuppressWarnings("unchecked")
	protected void bindProblem(final Class<? extends Creator<? extends Genotype>> creator,
			final Class<? extends Decoder<? extends Genotype, ? extends Object>> decoder,
			final Class<? extends Evaluator<? extends Object>> evaluator) {

		Set<Class<?>> classes = new HashSet<Class<?>>();
		if (creator != null) {
			classes.add(creator);
		}
		if (decoder != null) {
			classes.add(decoder);
		}

		for (Class<?> clazz : classes) {
			bind(clazz).in(SINGLETON);
		}

		if (creator != null) {
			bind(new TypeLiteral<Creator<Genotype>>() {
			}).to((Class<? extends Creator<Genotype>>) creator);
		}
		if (decoder != null) {
			bind(new TypeLiteral<Decoder<Genotype, Object>>() {
			}).to((Class<? extends Decoder<Genotype, Object>>) decoder);
		}
		if (evaluator != null) {
			addEvaluator(evaluator);
		}
	}

	/**
	 * Binds an additional {@link Evaluator}.
	 * 
	 * @param evaluator
	 *            the evaluator to use
	 */
	@SuppressWarnings({ "unchecked" })
	protected void addEvaluator(final Class<? extends Evaluator<? extends Object>> evaluator) {
		Multibinder<Evaluator<Object>> multibinder = Multibinder.newSetBinder(binder(),
				new TypeLiteral<Evaluator<Object>>() {
				});
		multibinder.addBinding().to((Class<? extends Evaluator<Object>>) evaluator);
	}
}
