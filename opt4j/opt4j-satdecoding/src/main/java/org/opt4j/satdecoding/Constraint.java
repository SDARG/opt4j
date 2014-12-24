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


package org.opt4j.satdecoding;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A linear pseudo-Boolean {@link Constraint} as used in an Integer Linear
 * Program (ILP). A set of {@link Constraint}s specify a search space.
 * <p>
 * 
 * This {@link Constraint} consists of a list of {@link Term}s, an
 * {@link Operator} and a right hand side. E.g., {@code 4a - 3b + not(c) <= 7}
 * is a valid {@link Constraint} consisting of three {@link Literal}s
 * {@code a, b, not(c)} with coefficients {@code 4, 3, 1} , the less or equal
 * operator from {@link Operator}, and the right hand side {@code 7}.
 * <p>
 * 
 * This implementation is a plain data structure, i.e., {@code 4a - 3a + c <= 1}
 * is not simplified to {@code a + c <= 1}. Moreover, two {@link Constraint}s
 * are equal if they have an equal {@link Operator}, right hand side, and equal
 * {@link Term}s in the same order.
 * 
 * @author lukasiewycz
 * 
 */
public class Constraint extends ArrayList<Term> {

	protected Operator operator = Operator.GE;
	protected int rhs = 1;

	/**
	 * The {@link Operator}s correspond to the Boolean operators <=,=,>=.
	 */
	public enum Operator {
		/**
		 * Boolean operation <=.
		 */
		LE,
		/**
		 * Boolean operation =.
		 */
		EQ,
		/**
		 * Boolean operation >=.
		 */
		GE;

		/**
		 * Returns a {@link String} representation of this {@link Operator}.
		 * 
		 * @return a string representation of this operator
		 */
		@Override
		public String toString() {
			switch (this) {
			case LE:
				return "<=";
			case EQ:
				return "=";
			default: // GE
				return ">=";
			}
		}

		/**
		 * Returns {@code true} if {@code lhs} {@link Operator} {@code rhs} is
		 * true.
		 * 
		 * @param lhs
		 *            the left hand side value
		 * @param rhs
		 *            the right hand side value
		 * @return {@code true} if the operation is true for the two values
		 */
		public boolean isTrue(int lhs, int rhs) {
			switch (this) {
			case LE:
				return (lhs <= rhs);
			case EQ:
				return (lhs == rhs);
			default: // GE
				return (lhs >= rhs);
			}
		}

		/**
		 * Converts a {@link String} to an operator.
		 * 
		 * @param string
		 *            the string (<=,=,>=).
		 * @return the operator
		 */
		public static Operator getOperator(String string) {
			if ("<=".equals(string)) {
				return LE;
			} else if ("=".equals(string)) {
				return EQ;
			} else if (">=".equals(string)) {
				return GE;
			} else {
				throw new IllegalArgumentException("Unknown operator " + string + ". Allowed operators are: <=,=,>=");
			}
		}
	}

	/**
	 * Constructs a linear {@link Constraint} with {@code >=1}.
	 */
	public Constraint() {
		this(Operator.GE, 1);
	}

	/**
	 * Constructs a linear {@link Constraint}.
	 * 
	 * @param operator
	 *            the operator (<=,=,>=) as a String
	 * @param rhs
	 *            the right-hand-side value
	 */
	public Constraint(String operator, int rhs) {
		this(Operator.getOperator(operator), rhs);
	}

	/**
	 * Constructs a linear {@link Constraint}.
	 * 
	 * @param operator
	 *            the operator
	 * @param rhs
	 *            the right-hand-side value
	 */
	public Constraint(Operator operator, int rhs) {
		this.operator = operator;
		this.rhs = rhs;
	}

	/**
	 * Returns an iterable of the coefficients.
	 * 
	 * @return the coefficients
	 */
	public Iterable<Integer> getCoefficients() {
		return new Iterable<Integer>() {
			@Override
			public Iterator<Integer> iterator() {
				return new Iterator<Integer>() {

					int i = 0;

					@Override
					public boolean hasNext() {
						return (i < size());
					}

					@Override
					public Integer next() {
						return get(i++).getCoefficient();
					}

					@Override
					public void remove() {
						throw new RuntimeException("Operation not supported");
					}
				};
			}
		};
	}

	/**
	 * Returns an iterable of the {@link Literal}s.
	 * 
	 * @return the literals.
	 */
	public Iterable<Literal> getLiterals() {
		return new Iterable<Literal>() {
			@Override
			public Iterator<Literal> iterator() {
				return new Iterator<Literal>() {

					int i = 0;

					@Override
					public boolean hasNext() {
						return (i < size());
					}

					@Override
					public Literal next() {
						return get(i++).getLiteral();
					}

					@Override
					public void remove() {
						throw new RuntimeException("Operation not supported");
					}
				};
			}
		};
	}

	/**
	 * Returns the {@link Operator}.
	 * 
	 * @see #setOperator
	 * @return the operator
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * Sets the {@link Operator}.
	 * 
	 * @see #getOperator
	 * @param operator
	 *            the operator to be set
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * Return the right hand side value.
	 * 
	 * @see #setRhs
	 * @return the right hand side value
	 */
	public int getRhs() {
		return rhs;
	}

	/**
	 * Sets the right hand side value.
	 * 
	 * @see #getRhs
	 * @param rhs
	 *            the right hand side value to set
	 */
	public void setRhs(int rhs) {
		this.rhs = rhs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractCollection#toString()
	 */
	@Override
	public String toString() {
		String s = "";

		for (int i = 0; i < size(); i++) {
			s += get(i);
			if (i < size() - 1) {
				s += " + ";
			}
		}

		s += " " + operator + " ";
		s += rhs;

		return s;
	}

	/**
	 * Checks, if this constraint is satisfied for a given model.
	 * 
	 * @param model
	 *            the model
	 * @return true, iff this constraint is satisfied
	 */
	public boolean isSatisfied(Model model) {
		int count = getCount(model);
		return operator.isTrue(count, rhs);

	}

	/**
	 * Specifies if the {@link Constraint} is satisfied for a given
	 * {@link Model}.
	 * 
	 * @param model
	 *            the model
	 * @return {@code true} if this constraint is satisfied for the model
	 */
	public int getViolationCount(Model model) {
		int count = getCount(model);

		switch (operator) {
		case LE:
			return (count <= rhs) ? 0 : (count - rhs);
		case EQ:
			return Math.abs(count - rhs);
		default: // GE
			return (count >= rhs) ? 0 : (rhs - count);
		}
	}

	/**
	 * Add a {@link Literal} with the coefficient {@code 1}.
	 * 
	 * @param lit
	 *            the literal
	 */
	public void add(Literal lit) {
		add(1, lit);
	}

	/**
	 * Add a {@link Literal} with specified coefficient.
	 * 
	 * @param coeff
	 *            the coefficient
	 * @param lit
	 *            the literal
	 */
	public void add(int coeff, Literal lit) {
		Term term = new Term(coeff, lit);
		add(term);
	}

	/**
	 * Returns the sum of the left hand side of the constraint for a given
	 * model.
	 * 
	 * @param model
	 *            the model
	 * @return the sum
	 */
	private int getCount(Model model) {
		int count = 0;
		for (Term term : this) {
			Literal literal = term.getLiteral();
			int coefficient = term.getCoefficient();

			Object var = literal.variable();
			Boolean phase = literal.phase();

			if (phase.equals(model.get(var))) {
				count += coefficient;
			}
		}
		return count;
	}

	/**
	 * Copies the {@link Constraint}.
	 * 
	 * @return a copy of the constraint
	 */
	public Constraint copy() {
		Constraint pb = new Constraint(operator, rhs);
		for (Term term : this) {
			pb.add(term.copy());
		}
		return pb;
	}

	/**
	 * Returns {@code true} if the {@link Constraint} contains the
	 * {@link Literal}.
	 * 
	 * @param literal
	 *            the literal
	 * 
	 * @return {@code true} if the constrain} contains the literal
	 */
	public boolean contains(Literal literal) {
		for (Term term : this) {
			if (literal.equals(term.getLiteral())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractList#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + rhs;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractList#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Constraint other = (Constraint) obj;
		return (super.equals(other) && operator == other.operator && rhs == other.rhs);
	}

	private static final long serialVersionUID = 1L;

}
