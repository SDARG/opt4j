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

package org.opt4j.benchmarks.dtlz;

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.MAY;

import org.opt4j.benchmarks.BinaryCreator;
import org.opt4j.benchmarks.BinaryToDoubleDecoder;
import org.opt4j.benchmarks.Bits;
import org.opt4j.benchmarks.DoubleCopyDecoder;
import org.opt4j.benchmarks.DoubleCreator;
import org.opt4j.benchmarks.M;
import org.opt4j.benchmarks.N;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.config.annotations.Required;
import org.opt4j.core.problem.Creator;
import org.opt4j.core.problem.Decoder;
import org.opt4j.core.problem.Evaluator;
import org.opt4j.core.problem.ProblemModule;
import org.opt4j.core.start.Constant;

/**
 * Module for the DTLZ benchmarks as proposed in "Scalable multi-objective optimization test problems, Kalyanmoy Deb,
 * Lothar Thiele, Marco Laumanns, Eckart Zitzler, Proc. Congress Evolutionary Computation Volume 1 (May 2002)".
 * 
 * @author lukasiewycz
 * 
 */
@Info("DTLZ Problem Suite.  (n = m + k - 1)")
@Citation(title = "", authors = "Kalyanmoy Deb, Lothar Thiele, Marco Laumanns, and Eckart Zitzler", journal = "Proceedings of the 2002 Congress on Evolutionary Computation", volume = 1, month = MAY, year = 2002, doi = "10.1109/CEC.2002.1007032")
public class DTLZModule extends ProblemModule {

	@Order(0)
	@Info("The DTLZ function")
	protected Function function = Function.DTLZ1;

	@Required(property = "function", elements = { "DTLZ4" })
	@Order(1)
	@Constant(value = "alpha", namespace = DTLZ4.class)
	protected double alpha = 100.0;

	@Order(2)
	@Info("The number of objectives.")
	protected int m = 3;

	@Order(3)
	@Info("The size of the search space.")
	protected int n = 7;

	@Order(4)
	@Info("The k value.")
	protected int k = 5;

	@Order(10)
	@Info("The encoding of the optimization problem.")
	protected Encoding encoding = Encoding.DOUBLE;

	@Required(property = "encoding", elements = { "BINARY" })
	@Info("The number of bits per double value.")
	protected int bits = 30;

	/**
	 * The used DTZL1 function.
	 * 
	 * @author lukasiewycz
	 */
	public enum Function {
		/**
		 * Use the DTZL1 function.
		 */
		DTLZ1,
		/**
		 * Use the DTZL2 function.
		 */
		DTLZ2,
		/**
		 * Use the DTZL3 function.
		 */
		DTLZ3,
		/**
		 * Use the DTZL4 function.
		 */
		DTLZ4,
		/**
		 * Use the DTZL5 function.
		 */
		DTLZ5,
		/**
		 * Use the DTZL6 function.
		 */
		DTLZ6,
		/**
		 * Use the DTZL7 function.
		 */
		DTLZ7;
	}

	/**
	 * The used encoding type.
	 * 
	 * @author lukasiewycz
	 */
	public enum Encoding {
		/**
		 * Use double values.
		 */
		DOUBLE,
		/**
		 * Use binary values.
		 */
		BINARY;
	}

	/**
	 * Returns the number of bits per double value.
	 * 
	 * @return the bits
	 */
	public int getBits() {
		return bits;
	}

	/**
	 * Sets the number of bits per double value.
	 * 
	 * @param bits
	 *            the bits to set
	 */
	public void setBits(int bits) {
		this.bits = bits;
	}

	/**
	 * Returns the encoding (double or binary).
	 * 
	 * @return the encoding
	 */
	public Encoding getEncoding() {
		return encoding;
	}

	/**
	 * Set the encoding (double or binary).
	 * 
	 * @param encoding
	 *            the encoding
	 */
	public void setEncoding(Encoding encoding) {
		this.encoding = encoding;
	}

	/**
	 * Returns the DTZL function.
	 * 
	 * @return the DTZL function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * Sets the ZDT function.
	 * 
	 * @param function
	 *            the ZDT function
	 */
	public void setFunction(Function function) {
		this.function = function;

		switch (function) {
		default: // DTLZ1
			n = 7;
			m = 3;
			k = 5;
			break;
		case DTLZ2:
		case DTLZ3:
		case DTLZ4:
		case DTLZ5:
		case DTLZ6:
			n = 12;
			m = 3;
			k = 10;
			break;
		case DTLZ7:
			n = 22;
			m = 3;
			k = 20;
			break;
		}

	}

	/**
	 * Returns the n value.
	 * 
	 * @return the n value
	 */
	public int getN() {
		return n;
	}

	/**
	 * Sets the n value.
	 * 
	 * @param n
	 *            the n value
	 */
	public void setN(int n) {
		this.n = n;
		this.k = n - m + 1;
	}

	/**
	 * Returns the m value.
	 * 
	 * @return the m
	 */
	public int getM() {
		return m;
	}

	/**
	 * Sets the m value.
	 * 
	 * @param m
	 *            the m to set
	 */
	public void setM(int m) {
		this.m = m;
		this.k = n - m + 1;
	}

	/**
	 * Returns the k value.
	 * 
	 * @return the k
	 */
	public int getK() {
		return k;
	}

	/**
	 * Sets the k value.
	 * 
	 * @param k
	 *            the k to set
	 */
	public void setK(int k) {
		this.k = k;
		this.n = m + k - 1;
	}

	/**
	 * Returns the alpha.
	 * 
	 * @return the alpha
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets the alpha.
	 * 
	 * @param alpha
	 *            the alpha to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		n = m + k - 1;

		Class<? extends Creator<?>> creator = null;
		Class<? extends Decoder<?, ?>> decoder = null;
		Class<? extends Evaluator<?>> evaluator = null;

		switch (encoding) {
		case BINARY:
			creator = BinaryCreator.class;
			decoder = BinaryToDoubleDecoder.class;
			break;
		case DOUBLE:
			creator = DoubleCreator.class;
			decoder = DoubleCopyDecoder.class;
			break;
		}

		switch (function) {
		case DTLZ1:
			evaluator = DTLZ1.class;
			break;
		case DTLZ2:
			evaluator = DTLZ2.class;
			break;
		case DTLZ3:
			evaluator = DTLZ3.class;
			break;
		case DTLZ4:
			evaluator = DTLZ4.class;
			break;
		case DTLZ5:
			evaluator = DTLZ5.class;
			break;
		case DTLZ6:
			evaluator = DTLZ6.class;
			break;
		case DTLZ7:
			evaluator = DTLZ7.class;
			break;
		default:
			evaluator = DTLZ1.class;
		}

		bindConstant(N.class).to(n);
		bindConstant(M.class).to(m);
		bindConstant(Bits.class).to(bits);
		bindProblem(creator, decoder, evaluator);

	}

}
