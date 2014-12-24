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
 
package org.opt4j.core.start;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.opt4j.core.IndividualStateListener;
import org.opt4j.core.config.Property;
import org.opt4j.core.config.PropertyModule;
import org.opt4j.core.config.annotations.Category;
import org.opt4j.core.config.annotations.File;
import org.opt4j.core.config.annotations.Icon;
import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Multi;
import org.opt4j.core.config.annotations.Name;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Panel;
import org.opt4j.core.config.annotations.Parent;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.optimizer.ControlListener;
import org.opt4j.core.optimizer.OptimizerIterationListener;
import org.opt4j.core.optimizer.OptimizerStateListener;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.ConfigurationException;
import com.google.inject.Scope;
import com.google.inject.Scopes;
import com.google.inject.binder.ConstantBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.spi.Message;

/**
 * The {@link Opt4JModule} is the superclass for all modules. The class and its
 * fields might be extended with certain annotations. See {@link Constant} and the
 * {@code org.opt4j.core.config.annotations} package.
 * 
 * @see Constant
 * @see Category
 * @see File
 * @see Icon
 * @see Ignore
 * @see Info
 * @see Multi
 * @see Name
 * @see Order
 * @see Panel
 * @see Parent
 * @see Required
 * @author lukasiewycz
 * 
 * 
 */
public abstract class Opt4JModule extends AbstractModule {

	/**
	 * The singleton scope.
	 */
	public static Scope SINGLETON = Scopes.SINGLETON;

	/**
	 * Bind a value.
	 * 
	 * @param annotation
	 *            the type of annotation of the value
	 * @return the constant binding builder that allows a binding
	 */
	public ConstantBindingBuilder bindConstant(Class<? extends Annotation> annotation) {
		return bindConstant().annotatedWith(annotation);
	}

	/**
	 * Bind a value.
	 * 
	 * @param annotation
	 *            the annotation of the value
	 * @return the constant binding builder that allows a binding
	 */
	public ConstantBindingBuilder bindConstant(Annotation annotation) {
		return bindConstant().annotatedWith(annotation);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void configure() {

		/**
		 * Configure injected constants.
		 */
		PropertyModule module = new PropertyModule(this);
		for (Property property : module.getProperties()) {
			for (Annotation annotation : property.getAnnotations()) {
				if (annotation.annotationType().getAnnotation(BindingAnnotation.class) != null) {
					Class<?> type = property.getType();
					Object value = property.getValue();

					ConstantBindingBuilder builder = bindConstant(annotation);
					if (type.equals(Integer.TYPE)) {
						builder.to((Integer) value);
					} else if (type.equals(Long.TYPE)) {
						builder.to((Long) value);
					} else if (type.equals(Double.TYPE)) {
						builder.to((Double) value);
					} else if (type.equals(Float.TYPE)) {
						builder.to((Float) value);
					} else if (type.equals(Byte.TYPE)) {
						builder.to((Byte) value);
					} else if (type.equals(Short.TYPE)) {
						builder.to((Short) value);
					} else if (type.equals(Boolean.TYPE)) {
						builder.to((Boolean) value);
					} else if (type.equals(Character.TYPE)) {
						builder.to((Character) value);
					} else if (type.equals(String.class)) {
						builder.to((String) value);
					} else if (type.equals(Class.class)) {
						builder.to((Class<?>) value);
					} else if (value instanceof Enum<?>) {
						builder.to((Enum) value);
					} else {
						String message = "Constant type not bindable: " + type + " of field " + property.getName() + " in module "
								+ this.getClass().getName();
						throw new ConfigurationException(Arrays.asList(new Message(message)));
					}
				}
			}
		}

		multi(OptimizerStateListener.class);
		multi(OptimizerIterationListener.class);
		multi(IndividualStateListener.class);

		config();
	}

	protected void multi(Class<?> clazz) {
		Multibinder.newSetBinder(binder(), clazz);
	}

	/**
	 * Configure the module.
	 * 
	 * Bind constants, listeners, and bind arbitrary classes.
	 * 
	 * @see Binder
	 */
	protected abstract void config();

	/**
	 * Returns the implementation of the {@link Constant} annotation.
	 * 
	 * @param value
	 *            the value
	 * @param namespace
	 *            the namespace
	 * @return the constant annotation
	 */
	public static ConstantImpl constant(String value, Class<?> namespace) {
		return new ConstantImpl(value, namespace);
	}

	/**
	 * The {@link ConstantImpl} is the implementation of the {@link Constant}
	 * annotation.
	 * 
	 * @author lukasiewycz
	 * 
	 */
	@SuppressWarnings("all")
	static class ConstantImpl implements Serializable, Constant {

		private final String value;
		private final Class<?> namespace;

		/**
		 * Constructs a {@link ConstantImpl}.
		 * 
		 * @param value
		 *            the value
		 * @param namespace
		 *            the namespace
		 */
		ConstantImpl(String value, Class<?> namespace) {
			this.value = value;
			this.namespace = namespace;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opt4j.start.Constant#value()
		 */
		@Override
		public String value() {
			return value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.opt4j.start.Constant#namespace()
		 */
		@Override
		public Class<?> namespace() {
			return namespace;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return ((127 * "value".hashCode()) ^ value.hashCode()) + ((127 * "namespace".hashCode()) ^ namespace.hashCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object o) {
			if (o == null || !(o instanceof Constant)) {
				return false;
			}

			Constant other = (Constant) o;
			return (value.equals(other.value()) && namespace.equals(other.namespace()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.annotation.Annotation#annotationType()
		 */
		@Override
		public Class<? extends Annotation> annotationType() {
			return Constant.class;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "@" + Constant.class.getName() + "(value=" + value + ", namespace=" + namespace.getName() + ")";
		}

		private static final long serialVersionUID = 0;
	}

	/**
	 * Bind a {@link Constant}.
	 * 
	 * @param value
	 *            the value from the {@link Constant} annotation with an empty
	 *            namespace
	 * @return the constant binding builder that allows a binding
	 */
	protected ConstantBindingBuilder bindConstant(String value) {
		return bindConstant(value, Object.class);
	}

	/**
	 * Bind a constant.
	 * 
	 * @param value
	 *            the value from the {@link Constant}
	 * @param namespace
	 *            the namespace from the {@link Constant}
	 * @return the constant binding builder that allows a binding
	 */
	public ConstantBindingBuilder bindConstant(String value, Class<?> namespace) {
		assert (namespace != null);
		Constant c = new ConstantImpl(value, namespace);
		return bindConstant(c);
	}

	/**
	 * Adds an {@link OptimizerStateListener}.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addOptimizerStateListener(Class<? extends OptimizerStateListener> listener) {
		Multibinder<OptimizerStateListener> multibinder = Multibinder.newSetBinder(binder(), OptimizerStateListener.class);
		multibinder.addBinding().to(listener);
	}

	/**
	 * Adds an {@link OptimizerIterationListener}.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addOptimizerIterationListener(Class<? extends OptimizerIterationListener> listener) {
		assert binder() != null;
		Multibinder<OptimizerIterationListener> multibinder = Multibinder.newSetBinder(binder(), OptimizerIterationListener.class);
		multibinder.addBinding().to(listener);
	}

	/**
	 * Adds an {@link IndividualStateListener}.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addIndividualStateListener(Class<? extends IndividualStateListener> listener) {
		Multibinder<IndividualStateListener> multibinder = Multibinder.newSetBinder(binder(), IndividualStateListener.class);
		multibinder.addBinding().to(listener);
	}

	/**
	 * Adds an {@link ControlListener}.
	 * 
	 * @param listener
	 *            the listener to be added
	 */
	public void addControlListener(Class<? extends ControlListener> listener) {
		Multibinder<ControlListener> multibinder = Multibinder.newSetBinder(binder(), ControlListener.class);
		multibinder.addBinding().to(listener);
	}

}
