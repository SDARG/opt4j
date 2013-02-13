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

package org.opt4j.operator;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.opt4j.core.Genotype;
import org.opt4j.core.optimizer.IncompatibilityException;
import org.opt4j.core.optimizer.Operator;
import org.opt4j.genotype.CompositeGenotype;
import org.opt4j.start.Parameters;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Superclass for {@link GenericOperator}s.
 * 
 * @author lukasiewycz
 * 
 * @param <O>
 *            The specified {@link Operator}.
 * @param <Q>
 *            The specified {@link Operator} with a wildcard (?).
 */
public abstract class AbstractGenericOperator<O extends Operator<?>, Q extends Operator<?>> implements
		GenericOperator<O> {

	/**
	 * Comparator for a specific order: Superclasses always are sorted after
	 * subclasses.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	protected static class ClassComparator implements Comparator<Class<? extends Genotype>> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Class<? extends Genotype> arg0, Class<? extends Genotype> arg1) {
			if (arg0.equals(arg1)) {
				return 0;
			} else if (arg0.isAssignableFrom(arg1)) {
				// arg0 is superclass of arg1
				return 1;
			} else if (arg1.isAssignableFrom(arg0)) {
				// arg1 is superclass of arg0
				return -1;
			} else {
				return arg0.getCanonicalName().compareTo(arg1.getCanonicalName());
			}
		}

	}

	protected SortedMap<Class<? extends Genotype>, O> classOperators = new TreeMap<Class<? extends Genotype>, O>(
			new ClassComparator());
	protected Map<OperatorPredicate, O> genericOperators = new HashMap<OperatorPredicate, O>();

	protected List<Class<? extends Q>> cldef = new ArrayList<Class<? extends Q>>();

	/**
	 * Constructs an {@link AbstractGenericOperator} class with the given
	 * clazzes of default operators.
	 * 
	 * @param clazzes
	 *            the default operators
	 */
	public AbstractGenericOperator(Class<? extends Q>... clazzes) {
		for (Class<? extends Q> cl : clazzes) {
			cldef.add(cl);
		}
	}

	/**
	 * Inject and organize the operators.
	 * 
	 * @param holder
	 *            the operator holder
	 */
	@SuppressWarnings("unchecked")
	@Inject
	protected synchronized void inject(OperatorHolder<Q> holder) {
		if (classOperators.isEmpty()) {
			classOperators.put(CompositeGenotype.class, null);
			holder.add(cldef);

			for (Entry<OperatorPredicate, Q> entry : holder.getMap().entrySet()) {
				addOperator(entry.getKey(), (O) entry.getValue());
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.GenericOperator#addOperator(org.opt4j.operator.
	 * AbstractGenericOperator.OperatorPredicate,
	 * org.opt4j.core.optimizer.Operator)
	 */
	@Override
	public void addOperator(OperatorPredicate predicate, O operator) {
		if (predicate instanceof OperatorClassPredicate) {
			Class<? extends Genotype> clazz = ((OperatorClassPredicate) predicate).getClazz();
			classOperators.put(clazz, operator);
		} else {
			genericOperators.put(predicate, operator);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.opt4j.operator.GenericOperator#getOperator(org.opt4j.core.problem
	 * .Genotype)
	 */
	@Override
	public O getOperator(Genotype genotype) {
		if (classOperators.containsKey(genotype.getClass())) {
			return classOperators.get(genotype.getClass());
		} else {
			// Search for a predicate that satisfies the genotype.
			for (Entry<OperatorPredicate, O> predicate : genericOperators.entrySet()) {
				if (predicate.getKey().evaluate(genotype)) {
					return predicate.getValue();
				}
			}

			// Searches for a superclass that is registered as an operator.
			for (Entry<Class<? extends Genotype>, O> entry : classOperators.entrySet()) {
				Class<? extends Genotype> c = entry.getKey();
				if (c.isAssignableFrom(genotype.getClass())) {
					O operator = entry.getValue();
					addOperator(new OperatorClassPredicate(genotype.getClass()), operator);
					return operator;
				}
			}
			throw new IncompatibilityException("No handler found for " + genotype.getClass() + " in " + this.getClass());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.operator.common.GenericOperator#getHandlers()
	 */
	@Override
	public Collection<O> getOperators() {
		Set<O> set = new HashSet<O>();
		set.addAll(classOperators.values());
		set.addAll(genericOperators.values());
		return set;
	}

	/**
	 * Returns the target {@link Genotype} for an operator based on the
	 * {@link Apply} annotation.
	 * 
	 * @param <O>
	 *            the type of operator
	 * @param operator
	 *            the operator
	 * @return the target genotype
	 */
	protected static <O> Class<? extends Genotype> getTarget(O operator) {
		Apply apply = operator.getClass().getAnnotation(Apply.class);

		if (apply != null) {
			return apply.value();
		}

		Type type = Parameters.getType(Operator.class, operator, "G");
		if (type != null) {
			Class<? extends Genotype> target = Parameters.getClass(type).asSubclass(Genotype.class);
			return target;
		}

		throw new IllegalArgumentException("No target specified for the operator " + operator.getClass().getName()
				+ ". Either parameterize the Operator or use the " + Apply.class.getName()
				+ " annotation to specify a target.");
	}

	protected static class OperatorHolder<P> {

		@Inject(optional = true)
		protected Map<OperatorPredicate, P> map = new HashMap<OperatorPredicate, P>();

		@Inject
		protected Injector injector;

		protected Collection<Class<? extends P>> clazzes = new ArrayList<Class<? extends P>>();

		public void add(Collection<Class<? extends P>> clazzes) {
			this.clazzes.addAll(clazzes);
		}

		public Map<OperatorPredicate, P> getMap() {
			Map<OperatorPredicate, P> map = new HashMap<OperatorPredicate, P>();

			for (Class<? extends P> clazz : clazzes) {
				P p = injector.getInstance(clazz);
				map.put(new OperatorClassPredicate(getTarget((Operator<?>) p)), p);
			}

			for (Entry<OperatorPredicate, P> entry : this.map.entrySet()) {
				OperatorPredicate predicate = entry.getKey();
				if (predicate instanceof OperatorVoidPredicate) {
					predicate = new OperatorClassPredicate(getTarget((Operator<?>) entry.getValue()));
				}
				map.put(predicate, entry.getValue());
			}

			return map;
		}
	}

	/**
	 * The {@link OperatorPredicate} interface.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public interface OperatorPredicate {

		/**
		 * Checks whether a {@link Genotype} satisfies the predicate.
		 * 
		 * @param genotype
		 *            the genotype
		 * @return {@code true} if the predicate is satisfied
		 */
		public boolean evaluate(Genotype genotype);
	}

	/**
	 * The {@link OperatorVoidPredicate} interface is used as marker for
	 * {@link Operator}s for which the predicate is not explicitly defined.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public static class OperatorVoidPredicate implements OperatorPredicate {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.opt4j.operator.AbstractGenericOperator.OperatorPredicate#evaluate
		 * (org.opt4j.core.problem.Genotype)
		 */
		@Override
		public boolean evaluate(Genotype genotype) {
			return false;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Predicate Void";
		}

	}

	/**
	 * The {@link OperatorClassPredicate} returns {@code true} for a given
	 * specific class.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	public static class OperatorClassPredicate implements OperatorPredicate {

		protected final Class<? extends Genotype> clazz;

		/**
		 * Creates a new {@link OperatorClassPredicate} for the given
		 * {@link Genotype} class.
		 * 
		 * @param clazz
		 *            the class of the genotype
		 */
		public OperatorClassPredicate(Class<? extends Genotype> clazz) {
			this.clazz = clazz;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.opt4j.operator.AbstractGenericOperator.OperatorPredicate#evaluate
		 * (org.opt4j.core.problem.Genotype)
		 */
		@Override
		public boolean evaluate(Genotype genotype) {
			return clazz.equals(genotype.getClass());
		}

		/**
		 * Returns the genotype class for the operator.
		 * 
		 * @return the genotype class
		 */
		public Class<? extends Genotype> getClazz() {
			return clazz;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Predicate [clazz=" + clazz.getSimpleName() + "]";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
			return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			OperatorClassPredicate other = (OperatorClassPredicate) obj;
			if (clazz == null) {
				if (other.clazz != null) {
					return false;
				}
			} else if (!clazz.equals(other.clazz)) {
				return false;
			}
			return true;
		}
	}

}
