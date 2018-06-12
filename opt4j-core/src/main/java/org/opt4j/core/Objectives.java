/*******************************************************************************
 * Copyright (c) 2014 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/

package org.opt4j.core;

import static org.opt4j.core.Objective.Sign.MAX;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.opt4j.core.Objective.Sign;

/**
 * The {@link Objectives} contains the {@link Objective}-{@link Value}s pairs of an {@link Individual}.
 * 
 * @see Value
 * @see Objective
 * @author lukasiewycz
 * 
 */
public class Objectives implements Iterable<Entry<Objective, Value<?>>> {

	protected final SortedMap<Objective, Value<?>> map = new TreeMap<Objective, Value<?>>();

	protected double[] array = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Entry<Objective, Value<?>>> iterator() {
		return map.entrySet().iterator();
	}

	/**
	 * Returns an array of all values which all have to be minimized. Do not call this method before all objectives were
	 * added!
	 * 
	 * @see Value#getDouble()
	 * @return an array containing values which have to be minimized
	 */
	public double[] array() {
		if (array == null) {
			submit();
		}

		return array;
	}

	/**
	 * Calculates the array.
	 */
	protected synchronized void submit() {
		if (array == null) {
			array = new double[size()];
			int i = 0;
			for (Entry<Objective, Value<?>> entry : this) {
				Objective objective = entry.getKey();
				Value<?> value = entry.getValue();

				Double v = value.getDouble();

				if (v == null) {
					array[i] = Double.MAX_VALUE;
				} else if (objective.getSign() == MAX) {
					array[i] = -v;
				} else {
					array[i] = v;
				}

				i++;
			}
		}
	}

	/**
	 * Returns the number of set {@link Objective}s.
	 * 
	 * @return the number of set objectives
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Returns all objectives.
	 * 
	 * @return all objectives
	 */
	public Collection<Objective> getKeys() {
		return map.keySet();
	}

	/**
	 * Returns all values.
	 * 
	 * @return all values
	 */
	public Collection<Value<?>> getValues() {
		return map.values();
	}

	/**
	 * Returns the value that is assigned to the given objective. Returns {@code null} if the objective does not exist.
	 * 
	 * @param objective
	 *            the given objective
	 * @return the value
	 */
	public Value<?> get(Objective objective) {
		return map.get(objective);
	}

	/**
	 * Returns the objective that is assigned to the given value. Returns {@code null} if the value does not exist.
	 * 
	 * @param value
	 *            the given value
	 * @return the objective
	 */
	public Objective get(Value<?> value) {
		Objective objective = null;
		for (Entry<Objective, Value<?>> entry : this) {
			Value<?> v = entry.getValue();
			if (value.equals(v)) {
				Objective o = entry.getKey();
				objective = o;
				break;
			}
		}
		return objective;
	}

	/**
	 * Adds the objective with the specified value.
	 * 
	 * @param objective
	 *            the objective
	 * @param value
	 *            the value
	 */
	public void add(Objective objective, Value<?> value) {
		if (value == null) {
			map.put(objective, new DoubleValue(null));
		} else {
			map.put(objective, value);
		}
		array = null;
	}

	/**
	 * Adds the objective with the specified value.
	 * 
	 * @param name
	 *            the name of the objective
	 * @param sign
	 *            the optimization direction
	 * @param value
	 *            the value
	 */
	public void add(String name, Sign sign, Value<?> value) {
		add(new Objective(name, sign), value);
	}

	/**
	 * Adds the objective with the specified double value.
	 * 
	 * @param objective
	 *            the objective
	 * @param value
	 *            the value
	 */
	public void add(Objective objective, double value) {
		add(objective, new DoubleValue(value));
	}

	/**
	 * Adds the objective with the specified value.
	 * 
	 * @param name
	 *            the name of the objective
	 * @param sign
	 *            the optimization direction
	 * @param value
	 *            the value
	 */
	public void add(String name, Sign sign, double value) {
		add(new Objective(name, sign), value);
	}

	/**
	 * Adds the objective with the specified integer value.
	 * 
	 * @param objective
	 *            the objective
	 * @param value
	 *            the value
	 */
	public void add(Objective objective, int value) {
		add(objective, new IntegerValue(value));
	}

	/**
	 * Adds the objective with the specified value.
	 * 
	 * @param name
	 *            the name of the objective
	 * @param sign
	 *            the optimization direction
	 * @param value
	 *            the value
	 */
	public void add(String name, Sign sign, int value) {
		add(new Objective(name, sign), value);
	}

	/**
	 * Adds all objective with the specified value specified in {@link Objectives}.
	 * 
	 * @param objectives
	 *            the objectives
	 */
	public void addAll(Objectives objectives) {
		map.putAll(objectives.map);
		array = null;
	}

	/**
	 * Returns {@code true} if this objectives weakly dominates the specified objectives. This comparison is based on
	 * the {@link #array()} values.
	 * 
	 * @param opponent
	 *            other objectives
	 * @return {@code true} if this objectives weakly dominate the {@code opponent}
	 */
	public boolean weaklyDominates(Objectives opponent) {
		double[] va = this.array();
		double[] vb = opponent.array();
		for (int i = 0; i < va.length; i++) {
			if (vb[i] < va[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns {@code true} if this objectives dominate the specified objectives. This comparison is based on the
	 * {@link #array()} values.
	 * 
	 * @param opponent
	 *            other objectives
	 * @return {@code true} if these objectives dominate the {@code opponent}
	 */
	public boolean dominates(Objectives opponent) {
		double[] va = this.array();
		double[] vb = opponent.array();

		boolean equal = true;
		for (int i = 0; i < va.length; i++) {
			if (va[i] > vb[i]) {
				return false;
			} else if (va[i] < vb[i]) {
				equal = false;
			}
		}
		return !equal;
	}

	/**
	 * Returns {@code true} if this objectives are equal to the specified objectives. This comparison is based on the
	 * {@link #array()} values.
	 * 
	 * @param opponent
	 *            other objectives
	 * @return {@code true} if these objectives dominate the {@code opponent}
	 */
	public boolean isEqual(Objectives opponent) {
		double[] va = this.array();
		double[] vb = opponent.array();

		for (int i = 0; i < va.length; i++) {
			if (va[i] != vb[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Calculates the euclidean distance of two {@link Objectives}. This calculation is based on the {@link #array()}
	 * values.
	 * 
	 * @param other
	 *            the second objectives
	 * @return the euclidean distance
	 */
	public double distance(Objectives other) {
		double[] va = this.array();
		double[] vb = other.array();

		double s = 0;
		for (int i = 0; i < va.length; i++) {
			s += (va[i] - vb[i]) * (va[i] - vb[i]);
		}

		return Math.sqrt(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		for (Entry<Objective, Value<?>> entry : map.entrySet()) {
			Objective o = entry.getKey();
			Value<?> v = entry.getValue();
			s.append(o).append("=").append(v).append(" ");
		}
		return s.toString();
	}

}
